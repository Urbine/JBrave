/*
 * JBrave
 *
 * Copyright 2025-2026 Yoham Gabriel Barboza B. (YGBStudio)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package net.ygbstudio.jbrave.core.executors;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import net.ygbstudio.jbrave.api.rate.XRateLimitReset;
import net.ygbstudio.jbrave.api.response.ErrorResponse;
import net.ygbstudio.jbrave.api.response.RateLimitResponse;
import net.ygbstudio.jbrave.core.exceptions.BraveApiException;
import net.ygbstudio.jbrave.core.exceptions.BraveClientException;
import net.ygbstudio.jbrave.core.exceptions.RequestProcessingInterrupted;
import net.ygbstudio.jbrave.core.exceptions.RequestRetryInterruptedException;
import net.ygbstudio.jbrave.core.model.BraveErrorCode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serializes execution of HTTP requests against a shared Brave API execution context.
 *
 * <p>{@code BraveExecutionGate} acts as a single-admission execution gate: at most one request may
 * be executed at a time. Callers attempting to submit a request while another request is in
 * progress will block until execution becomes available or until the calling thread is interrupted.
 *
 * <p>This class is designed to coordinate access to a rate-limited API token. It ensures:
 *
 * <ul>
 *   <li>Strict FIFO fairness between competing callers
 *   <li>Global serialization of request execution
 *   <li>Consistent handling of HTTP 429 (rate limit) responses
 *   <li>Retry and backoff behavior based on server-provided rate limit metadata
 * </ul>
 *
 * <p><strong>Concurrency model:</strong> This gate uses a fair {@link
 * java.util.concurrent.locks.ReentrantLock} to provide mutual exclusion. Lock acquisition is
 * interruptible, allowing callers to cancel admission while waiting. Rate-limit backoff is
 * performed while holding the lock, ensuring that no subsequent request executes until the backoff
 * window has elapsed.
 *
 * <p><strong>Blocking semantics:</strong> Calls to {@link #submit(HttpRequest)} and {@link
 * #submit(HttpRequest, int)} are synchronous and may block for extended periods of time due to rate
 * limiting or retry backoff. This behavior is intentional and reflects the semantics of a shared,
 * rate-limited execution resource.
 *
 * <p><strong>Default retry policy:</strong> Rate-limited (HTTP 429) responses are retried up to
 * {@link #DEFAULT_MAX_RETRIES DEFAULT_MAX_RETRIES} times by default, sleeping for the
 * server-provided rate limit reset window before each attempt. The retry is what activates the
 * rate-aware backoff; without it, a rate-limited response would be returned without honoring the
 * server's backoff guidance.
 *
 * <p><strong>Failure handling:</strong>
 *
 * <ul>
 *   <li>Thread interruption during admission results in {@link RequestProcessingInterrupted}
 *   <li>Thread interruption during retry/backoff results in {@link
 *       RequestRetryInterruptedException}
 *   <li>Non-rate-limit HTTP 429 responses are treated as unrecoverable errors
 * </ul>
 *
 * <p>This class does not perform authentication, request construction, or asynchronous scheduling.
 * It is a coordination primitive, not a general-purpose executor.
 */
public final class BraveExecutionGate {
  private static final Logger admissionLogger = LoggerFactory.getLogger(BraveExecutionGate.class);

  /** The default maximum number of retry attempts for rate-limited (HTTP 429) responses. */
  private static final int DEFAULT_MAX_RETRIES = 1;

  private final ReentrantLock startLock;
  private final BraveRequestExecutor executor;

  /**
   * Creates a new execution gate backed by a single shared {@link BraveRequestExecutor}.
   *
   * <p>The gate uses a fair reentrant lock so concurrent callers are admitted in FIFO order.
   */
  public BraveExecutionGate() {
    startLock = new ReentrantLock(true);
    executor = BraveRequestExecutor.getInstance();
  }

  /**
   * Submits a request for serialized execution through the execution gate.
   *
   * <p>If another request is currently executing, the calling thread will block until execution
   * becomes available or until the thread is interrupted.
   *
   * <p>The request will be executed synchronously and may be retried if the Brave API responds with
   * a rate-limit (HTTP 429) error at least once. Retry behavior is governed by the {@code
   * maxRetries} parameter and server-provided rate limit metadata.
   *
   * @param httpRequest the {@link HttpRequest} to execute
   * @param maxRetries the maximum number of retry attempts for rate-limited responses
   * @return the completed HTTP response
   * @throws RequestProcessingInterrupted if the thread is interrupted while waiting to acquire
   *     execution admission
   * @throws RequestRetryInterruptedException if the thread is interrupted during retry backoff
   * @throws BraveApiException if an unrecoverable API error is returned by the server (e.g. Monthly
   *     limit exhaustion)
   * @throws BraveClientException if a rate-limited response does not carry the rate limit window
   *     headers required to activate rate-aware backoff
   */
  public HttpResponse<String> submit(@NotNull HttpRequest httpRequest, int maxRetries) {
    admissionLogger.debug("Received URI: {} for processing", httpRequest.uri());
    return start(httpRequest, maxRetries);
  }

  /**
   * Submits a request for serialized execution with a default retry policy.
   *
   * <p>This method is equivalent to calling {@link #submit(HttpRequest, int)} with the {@link
   * #DEFAULT_MAX_RETRIES default retry policy}.
   *
   * @param httpRequest the {@link HttpRequest} to execute
   * @return the completed HTTP response
   * @throws RequestProcessingInterrupted if the thread is interrupted while waiting to acquire
   *     execution admission
   * @throws RequestRetryInterruptedException if the thread is interrupted during retry backoff
   * @throws BraveApiException if an unrecoverable API error occurs
   * @throws BraveClientException if a rate-limited response does not carry the rate limit window
   *     headers required to activate rate-aware backoff
   */
  public HttpResponse<String> submit(@NotNull HttpRequest httpRequest) {
    return submit(httpRequest, DEFAULT_MAX_RETRIES);
  }

  /**
   * Acquires exclusive execution admission and invokes request processing.
   *
   * <p>This method is responsible for enforcing mutual exclusion and fairness. Lock acquisition is
   * interruptible to allow callers to abandon admission while waiting behind other requests.
   *
   * <p>The lock is always released exactly once if successfully acquired.
   *
   * @param request the request to execute
   * @param maxRetries the maximum number of retry attempts
   * @return the completed HTTP response
   * @throws RequestProcessingInterrupted if admission is interrupted
   */
  private HttpResponse<String> start(@NotNull HttpRequest request, int maxRetries) {
    boolean locked = false;
    try {
      startLock.lockInterruptibly();
      locked = true;

      admissionLogger.debug("Processing request URI: {}", request.uri());
      return processNext(request, maxRetries);
    } catch (InterruptedException interrupted) {
      Thread.currentThread().interrupt();
      admissionLogger.warn("Interrupted Exception while processing builder request", interrupted);
      throw new RequestProcessingInterrupted(
          "Request processing was interrupted while waiting for admission", interrupted);
    } finally {
      if (locked) {
        startLock.unlock();
      }
    }
  }

  /**
   * Executes a request and applies retry and backoff logic for rate-limited responses.
   *
   * <p>This method performs synchronous request execution and handles HTTP 429 responses that
   * indicate API rate limiting. When a rate limit is encountered, the method sleeps for the
   * duration specified by the server before retrying.
   *
   * <p>Retry backoff occurs while holding the execution lock, ensuring that no subsequent request
   * executes until the rate-limit window has elapsed.
   *
   * @param request the request to execute
   * @param maxRetries the maximum number of retry attempts
   * @return the completed HTTP response
   * @throws InterruptedException if the thread is interrupted during backoff sleep
   * @throws BraveApiException if an unrecoverable API error occurs
   * @throws BraveClientException if rate aware retry strategy is unable to find retry window
   *     headers in the server response
   */
  private HttpResponse<String> processNext(@NotNull HttpRequest request, int maxRetries)
      throws InterruptedException {
    int attempt = 0;
    HttpResponse<String> httpResponse;
    try {
      do {
        admissionLogger.debug("Attempt {}/{} for URI: {}", attempt, maxRetries, request.uri());

        httpResponse = executor.execute(request);

        admissionLogger.debug(
            "Response completed with {} HTTP Status Code", httpResponse.statusCode());

        if (httpResponse.statusCode() != 429) {
          return httpResponse;
        }

        attempt++;
        ErrorResponse errorResponse = ErrorResponse.from(httpResponse);

        if (errorResponse.error().code() != BraveErrorCode.RATE_LIMITED) {
          Supplier<String> errorMessage =
              () ->
                  "Unrecoverable error from Brave Search: "
                      + errorResponse.error().detail()
                      + " Error Code: "
                      + errorResponse.error().code().name();
          admissionLogger.debug(
              "Detected HTTP 429 not related to rate limit backoff {}", errorMessage.get());
          admissionLogger.debug("Error Response: {}", errorResponse);
          throw new BraveApiException(errorMessage);
        }

        Optional<XRateLimitReset> limitResponse =
            RateLimitResponse.from(httpResponse).xRateLimitReset();

        int secondsUntilNextRequest;
        if (limitResponse.isPresent()) {
          secondsUntilNextRequest = limitResponse.get().secondsUntilNextRequest();
        } else {
          throw new BraveClientException(
              "Unable to activate rate aware retry strategy: empty rate limit window headers");
        }

        admissionLogger.debug(
            "Sleeping for {} seconds based on limit reset", secondsUntilNextRequest);
        TimeUnit.SECONDS.sleep(secondsUntilNextRequest);
      } while (httpResponse.statusCode() == 429 && attempt <= maxRetries);
      return httpResponse;
    } catch (InterruptedException interrupted) {
      Thread.currentThread().interrupt();
      admissionLogger.debug("Interrupted Exception detected", interrupted);
      throw new RequestRetryInterruptedException(
          String.format("Request was interrupted during retry attempt %d/%d", attempt, maxRetries),
          interrupted);
    }
  }
}
