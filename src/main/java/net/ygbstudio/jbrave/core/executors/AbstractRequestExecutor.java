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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for executing HTTP requests using a shared, long-lived {@link HttpClient}.
 *
 * <p>This executor is designed to be extended by concrete request builders or clients. It provides
 * a consistent execution model that:
 *
 * <ul>
 *   <li>Uses a single, reusable {@link HttpClient} instance
 *   <li>Fully consumes HTTP responses using a {@code byte[]} body handler
 *   <li>Performs response decompression and decoding <em>after</em> transport completion
 *   <li>Adapts the result into an {@link HttpResponse} with a transformed body
 * </ul>
 *
 * <p><strong>Lifecycle and resource management:</strong> This class intentionally reuses a single
 * {@link HttpClient} instance for its entire lifetime. The client is not closed per request.
 * Connection pooling, HTTP/2 multiplexing, and cleanup are managed internally by the JDK HTTP
 * client implementation.
 *
 * <p>If callers require explicit control over the {@link HttpClient} lifecycle or configuration,
 * they should manage their own client instance externally and use the request builders provided by
 * JBrave to construct {@link HttpRequest} and {@link URI} instances.
 *
 * <p>Builders in the {@code api} package are designed to be independent of any specific HTTP client
 * implementation.
 *
 * @param <T> the concrete subclass type, used to support fluent builder-style APIs
 */
public abstract class AbstractRequestExecutor<T extends AbstractRequestExecutor<T>> {

  private static final Logger executionLogger =
      LoggerFactory.getLogger(AbstractRequestExecutor.class);
  protected final HttpClient client =
      HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

  /**
   * Returns the current instance cast to the concrete subclass type.
   *
   * <p>This method supports fluent APIs in subclasses by avoiding repeated casts. The cast is safe
   * by construction because the type parameter {@code T} is constrained to extend {@code
   * AbstractRequestExecutor<T>}.
   *
   * @return this instance, cast to {@code T}
   */
  @SuppressWarnings("unchecked")
  protected T self() {
    return (T) this;
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
   * <p>This method performs a bounded, in-memory decompression of the provided byte array. It
   * should be used only after the full response body has been received.
   *
   * @param compressedBytes the GZIP-compressed data
   * @return the decompressed bytes
   * @throws IOException if the compressed data is malformed or decompression fails
   */
  public byte[] decompressGunzip(byte[] compressedBytes) throws IOException {
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
  public String decodeByteArray(byte[] bytes, Charset charset) {
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
  public String decodeByteHttpResponse(HttpResponse<byte[]> byteResponse) {
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
  protected <R, U> HttpResponse<U> adaptHttpResponse(
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
   * <p>The request is sent using the shared {@link HttpClient}. The response body is fully
   * materialized as a {@code byte[]} before any transformation occurs.
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
  public final @NotNull HttpResponse<String> execute(HttpRequest request)
      throws InterruptedException {
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
