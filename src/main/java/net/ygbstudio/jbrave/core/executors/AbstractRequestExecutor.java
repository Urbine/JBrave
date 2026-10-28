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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.SSLSession;
import net.ygbstudio.jbrave.core.exceptions.BraveClientException;
import net.ygbstudio.jbrave.core.exceptions.ResponseDecompressionException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for executing HTTP requests using a long-lived {@link HttpClient}.
 *
 * <p>This executor is designed to be extended by concrete request executor implementations. It
 * provides a consistent execution model that:
 *
 * <ul>
 *   <li>Uses one reusable {@link HttpClient} instance at a time
 *   <li>Fully consumes HTTP responses using a {@code byte[]} body handler
 *   <li>Performs response decompression and decoding <em>after</em> transport completion
 *   <li>Adapts the result into an {@link HttpResponse} with a transformed body
 * </ul>
 *
 * <p><strong>Lifecycle and resource management:</strong> By default, a single HTTP/2 client is
 * created lazily on first execution and reused for the lifetime of this executor. Callers that
 * require explicit control over client configuration can supply their own instance through {@link
 * #addCustomClient(HttpClient)}. In either case, the executor does not close the client, and
 * connection pooling, multiplexing, and cleanup are managed by the JDK HTTP client implementation.
 * A caller-supplied client remains owned by the caller.
 *
 * <p>Builders in the {@code api} package are designed to be independent of any specific HTTP client
 * implementation.
 */
abstract class AbstractRequestExecutor {

  private static final Logger executionLogger =
      LoggerFactory.getLogger(AbstractRequestExecutor.class);

  /**
   * The long-lived {@link HttpClient} used to execute requests through this executor.
   *
   * <p>This field is {@code null} until the default client is lazily initialized or a custom client
   * is supplied through {@link #addCustomClient(HttpClient)}.
   */
  private HttpClient client;

  /**
   * Creates a new executor without an HTTP client.
   *
   * <p>The default HTTP/2 client is created lazily on first execution unless an alternative client
   * is supplied through {@link #addCustomClient(HttpClient)}.
   */
  AbstractRequestExecutor() {}

  /** Initializes the default HTTP/2 client on first use if no client has been supplied. */
  private void clientInit() {
    if (client == null) {
      client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
      executionLogger.debug("Using default HTTP/2 client.");
    }
  }

  /**
   * Replaces the HTTP client used to execute subsequent requests.
   *
   * <p>This method is intended for callers that require explicit control over client configuration
   * or lifecycle. The supplied client remains caller-owned; the executor reuses it without taking
   * over its lifecycle. Normal JDK {@link HttpClient} lifecycle rules apply.
   *
   * @param client the HTTP client to use; must not be {@code null}
   */
  protected void addCustomClient(@NotNull HttpClient client) {
    executionLogger.debug("Using custom HTTP Client");
    this.client = client;
  }

  /**
   * Determines whether the given HTTP response body is compressed using GZIP.
   *
   * <p>This method inspects the {@code Content-Encoding} response header and checks for the
   * presence of {@code "gzip"}.
   *
   * <p>Note that this implementation assumes a simple encoding model and does not attempt to parse
   * or apply multiple stacked encodings.
   *
   * @param response the HTTP response whose headers should be inspected
   * @param <R> the response body type
   * @return {@code true} if the response indicates GZIP compression, {@code false} otherwise
   */
  private <R> boolean isCompressed(@NotNull HttpResponse<R> response) {
    return response.headers().firstValue("Content-Encoding").orElse("").contains("gzip");
  }

  /**
   * Decompresses a GZIP-compressed byte array.
   *
   * <p>This method performs in-memory decompression of the provided byte array. It should be used
   * only after the full response body has been received.
   *
   * @param compressedBytes the GZIP-compressed data
   * @return the decompressed bytes
   * @throws IOException if the compressed data is malformed or decompression fails
   */
  private byte @NotNull [] decompressGunzip(byte[] compressedBytes) throws IOException {
    try (ByteArrayInputStream compressed = new ByteArrayInputStream(compressedBytes);
        GZIPInputStream gzip = new GZIPInputStream(compressed);
        ByteArrayOutputStream decompressed = new ByteArrayOutputStream()) {
      gzip.transferTo(decompressed);
      return decompressed.toByteArray();
    }
  }

  /**
   * Decodes a byte array into a {@link String} using the given character set.
   *
   * <p>The character set must be provided explicitly to avoid reliance on platform default
   * encodings.
   *
   * @param bytes the raw byte data
   * @param charset the character set to use for decoding
   * @return the decoded string
   */
  @Contract(value = "_, _ -> new", pure = true)
  private @NotNull String decodeByteArray(byte[] bytes, Charset charset) {
    return new String(bytes, charset);
  }

  /**
   * Decodes an {@link HttpResponse} with a {@code byte[]} body into a {@link String}.
   *
   * <p>If the response indicates GZIP compression, the body is decompressed before decoding.
   * Decoding assumes UTF-8 encoding, as response bodies are expected to represent textual content
   * such as JSON or other UTF-8 encoded payloads intended for deserialization into domain objects.
   *
   * <p>This method performs all transformations eagerly and should be invoked only after the HTTP
   * response has been fully received.
   *
   * @param byteResponse the HTTP response containing a byte array body
   * @return the decoded response body as a string
   * @throws ResponseDecompressionException if decompression fails
   */
  private @NotNull String decodeByteHttpResponse(HttpResponse<byte[]> byteResponse) {
    try {
      return decodeByteArray(
          isCompressed(byteResponse) ? decompressGunzip(byteResponse.body()) : byteResponse.body(),
          StandardCharsets.UTF_8);
    } catch (IOException ioEx) {
      Supplier<String> decompressErr =
          () ->
              "Failed to decompress GZIP response body."
                  + (Objects.nonNull(ioEx.getCause())
                      ? " Caused by " + ioEx.getCause() + " Reason: " + ioEx.getCause().getMessage()
                      : "");
      executionLogger.debug(decompressErr.get(), ioEx);
      throw new ResponseDecompressionException(decompressErr);
    }
  }

  /**
   * Adapts an existing {@link HttpResponse} to a new response body type.
   *
   * <p>The returned response delegates all metadata (status code, headers, request, URI, protocol
   * version, etc.) to the original response while exposing the provided body value.
   *
   * <p>This adapter does not preserve redirect history; {@link HttpResponse#previousResponse()}
   * always returns {@link Optional#empty()}, regardless of whether redirects occurred during
   * request execution.
   *
   * <p>This is a structural adaptation only; no additional HTTP processing occurs.
   *
   * @param httpResponse the original HTTP response
   * @param newResponseBody the transformed response body
   * @param <R> the original response body type
   * @param <U> the adapted response body type
   * @return an {@link HttpResponse} exposing the transformed body
   */
  @Contract(value = "_, _ -> new", pure = true)
  private <R, U> @NotNull HttpResponse<U> adaptHttpResponse(
      HttpResponse<R> httpResponse, U newResponseBody) {
    return new HttpResponse<>() {
      @Override
      public int statusCode() {
        return httpResponse.statusCode();
      }

      @Override
      public HttpRequest request() {
        return httpResponse.request();
      }

      @Override
      public Optional<HttpResponse<U>> previousResponse() {
        return Optional.empty();
      }

      @Override
      public HttpHeaders headers() {
        return httpResponse.headers();
      }

      @Override
      public U body() {
        return newResponseBody;
      }

      @Override
      public Optional<SSLSession> sslSession() {
        return httpResponse.sslSession();
      }

      @Override
      public URI uri() {
        return httpResponse.uri();
      }

      @Override
      public HttpClient.Version version() {
        return httpResponse.version();
      }
    };
  }

  /**
   * Executes the given HTTP request and returns a decoded response.
   *
   * <p>The request is sent using the executor's configured {@link HttpClient}. The response body is
   * fully materialized as a {@code byte[]} before any transformation occurs.
   *
   * <p>If the response is GZIP-compressed, it is decompressed and then decoded into a {@link
   * String}. The resulting body is adapted into a new {@link HttpResponse} instance.
   *
   * <p>This method performs no streaming and does not expose partially received data.
   *
   * @param request the HTTP request to execute
   * @return an {@link HttpResponse} containing the decoded response body
   * @throws InterruptedException if the executing thread is interrupted
   * @throws BraveClientException if request execution or response processing fails
   */
  protected final @NotNull HttpResponse<String> execute(HttpRequest request)
      throws InterruptedException {
    clientInit();
    try {
      HttpResponse<byte[]> responseBytes =
          client.send(request, HttpResponse.BodyHandlers.ofByteArray());

      String finalResponseDecode = decodeByteHttpResponse(responseBytes);
      return adaptHttpResponse(responseBytes, finalResponseDecode);
    } catch (IOException ioEx) {
      Supplier<String> clientEx =
          () ->
              "Unable to process request for "
                  + request.uri().toString()
                  + (Objects.nonNull(ioEx.getCause()) ? " Caused by: " + ioEx.getCause() : "");
      throw new BraveClientException(clientEx);
    }
  }
}
