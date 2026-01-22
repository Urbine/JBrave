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

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import net.ygbstudio.jbrave.api.options.BravePlan;
import net.ygbstudio.jbrave.api.rate.XRateLimitReset;
import net.ygbstudio.jbrave.core.exceptions.BraveClientException;
import net.ygbstudio.jbrave.core.exceptions.EmptyTaskListException;
import net.ygbstudio.jbrave.core.exceptions.RequestRetryExhaustedException;
import net.ygbstudio.jbrave.core.utils.GzipBodyHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract class for building the request and executing it.
 *
 * <p>This class is designed to be extended by concrete implementations of request executors. It
 * provides a base implementation for executing requests.
 *
 * @param <T> The type of the concrete implementation of this abstract class.
 */
public abstract class AbstractRequestExecutor<T extends AbstractRequestExecutor<T>> {

  protected final HttpClient.Builder client =
      HttpClient.newBuilder().version(HttpClient.Version.HTTP_2);
  protected GzipBodyHandler gzipBodyHandler = new GzipBodyHandler();
  protected List<HttpRequest> requestList;
  protected RequestPacer requestPacer;

  /**
   * Returns the current instance of the builder.
   *
   * <p>The cast is safe because the method is declared to return the type parameter T, which is
   * defined as {@code T extends AbstractRequestExecutor<T>}. This means that T will always be a
   * subclass of AbstractRequestExecutor<T>, so it is safe to cast "this" to T.
   *
   * @return Current instance of the builder.
   */
  @SuppressWarnings("unchecked")
  protected T self() {
    return (T) this;
  }

  /**
   * Adds a request to the list of requests.
   *
   * @param requestSupplier the request to be added
   * @return the current instance of the builder
   */
  protected final T submit(Supplier<HttpRequest> requestSupplier) {
    if (requestList == null) requestList = new ArrayList<>();
    requestList.add(requestSupplier.get());
    return self();
  }

  protected final T addRatePacer(RequestPacer pacer) {
    this.requestPacer = pacer;
    return self();
  }

  /**
   * Executes the request and returns an optional response.
   *
   * @return an optional response to the request
   * @throws InterruptedException if the execution is interrupted
   */
  public final HttpResponse<String> execute(HttpRequest request) throws InterruptedException {
    try (HttpClient httpClient = client.build()) {
      return httpClient.send(request, new GzipBodyHandler());
    } catch (IOException ioEx) {
      Supplier<String> clientEx =
          () ->
              "Unable to process request for "
                  + request.uri().toString()
                  + (Objects.nonNull(ioEx.getCause()) ? " Caused by: " + ioEx.getCause() : "");
      throw new BraveClientException(clientEx);
    }
  }

  /**
   * Executes a closed set of HTTP requests sequentially, enforcing rate-limited admission, and
   * returns a {@link List} of transformed results.
   *
   * <p>Requests are submitted one at a time according to the configured request pacer. Although the
   * HTTP client uses asynchronous I/O internally via {@code sendAsync()}, this method enforces
   * synchronous orchestration by awaiting completion before proceeding to the next request.
   *
   * <p>{@code sendAsync()} is used to model request execution and response transformation as a
   * composable completion stage, allowing response decoration to be expressed as part of the
   * execution pipeline rather than as inline post-processing. A single-thread executor is supplied
   * to the HTTP client to reflect the strictly sequential nature of execution and to isolate client
   * completion work.
   *
   * @param decorateRequest a function that transforms an {@link HttpResponse} into a result
   * @return a {@link List} containing the results, in request order
   * @throws EmptyTaskListException if the request list is empty
   */
  protected final @NotNull <E> List<E> executeAllPaced(
      Function<? super HttpResponse<String>, E> decorateRequest) {
    RequestPacer pacer =
        Objects.nonNull(requestPacer) ? requestPacer : RequestPacer.of(BravePlan.FREE);
    try (HttpClient httpClient = client.executor(Executors.newSingleThreadExecutor()).build()) {
      if (requestList == null) {
        Supplier<String> taskListErr =
            () -> "Unable to execute tasks asynchronously. Task list is empty.";
        throw new EmptyTaskListException(taskListErr);
      }

      List<E> resultList = new ArrayList<>();
      Iterator<HttpRequest> requestIterator = requestList.iterator();
      while (requestIterator.hasNext()) {
        resultList.add(
            httpClient
                .sendAsync(requestIterator.next(), gzipBodyHandler)
                .thenApply(decorateRequest)
                .join());
        requestIterator.remove();
        try {
          pacer.timeUnit().sleep(pacer.delay());
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
      return resultList;
    }
  }

  /**
   * Executes the requests in the request list, retrying requests that receive a 429 status code up
   * to the specified maximum number of retries.
   *
   * @param maxRetries the maximum number of retries for requests that receive a 429 status code
   * @return a list of {@link HttpResponse}s, in request order
   * @throws RequestRetryExhaustedException if the maximum number of retries is reached for a
   *     request
   * @throws RequestRetryExhaustedException if an I/O error occurs while executing the HTTP request
   */
  public final @NotNull List<HttpResponse<String>> executeWithRetries(int maxRetries) {
    try (HttpClient httpClient = client.executor(Executors.newSingleThreadExecutor()).build()) {
      if (requestList == null) {
        Supplier<String> taskListErr =
            () -> "Unable to execute tasks asynchronously. Task list is empty.";
        throw new EmptyTaskListException(taskListErr);
      }

      List<HttpResponse<String>> resultList = new ArrayList<>();
      Iterator<HttpRequest> requestIterator = requestList.iterator();
      while (requestIterator.hasNext()) {
        int attempt = 0;
        HttpRequest currentRequest = requestIterator.next();
        HttpResponse<String> httpResponse = null;
        try {
          do {
            httpResponse = httpClient.send(currentRequest, gzipBodyHandler);
            attempt++;
            if (httpResponse.statusCode() != 429) {
              resultList.add(httpResponse);
              requestIterator.remove();
              break;
            }
            XRateLimitReset limitReset = XRateLimitReset.from(httpResponse);
            double waitTime = Math.pow(2, attempt);
            TimeUnit.SECONDS.sleep(Long.max(limitReset.secondsUntilNextRequest(), (int) waitTime));
          } while (httpResponse.statusCode() == 429 && attempt <= maxRetries);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RequestRetryExhaustedException(
              "Request was interrupted during retry attempt " + attempt + " of " + maxRetries,
              e,
              maxRetries,
              httpResponse != null ? httpResponse.statusCode() : -1,
              currentRequest.method(),
              currentRequest.uri().toString());
        }
      }
      return resultList;
    } catch (IOException e) {
      throw new RequestRetryExhaustedException(
          "I/O error while executing HTTP request", e, maxRetries, -1, "UNKNOWN", "UNKNOWN");
    }
  }
}
