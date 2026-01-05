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

package net.ygbstudio.jbrave.core.builders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import net.ygbstudio.jbrave.core.domain.SearchHeader;
import net.ygbstudio.jbrave.core.exceptions.BraveClientException;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import net.ygbstudio.jbrave.core.model.BraveSearchHeader;
import net.ygbstudio.jbrave.core.utils.GzipBodyHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract base class for building Brave API client builders.
 *
 * <p>Concrete implementations of this class must call the {@link #addHeader(SearchHeader, String)}
 * method to add headers before building. Furthermore, the builder must be cleared with the {@link
 * #clear()} method before adding headers.
 *
 * <p>The builder will throw {@link BraveClientException} if the subscription token header is
 * missing.
 *
 * @param <T> The concrete builder class.
 */
public abstract class AbstractBraveClientBuilder<T extends AbstractBraveClientBuilder<T>> {
  protected Set<BraveSearchHeader> headers;
  protected HttpRequest.Builder internalRequestBuilder;
  protected URI requestURI;

  /**
   * Returns the current instance of the builder.
   *
   * <p>The cast is safe because the method is declared to return the type parameter T, which is
   * defined as {@code T extends AbstractBraveClientBuilder<T>}. This means that T will always be a
   * subclass of AbstractBraveClientBuilder<T>, so it is safe to cast "this" to T.
   *
   * @return Current instance of the builder.
   */
  @SuppressWarnings("unchecked")
  protected T self() {
    return (T) this;
  }

  /**
   * Checks if the subscription token header is missing.
   *
   * @param <K> the type of the header, which must extend {@link SearchHeader}
   * @return true if the subscription token header is missing, false otherwise.
   */
  protected <K extends SearchHeader> boolean subscriptionMissing() {
    return headers.stream()
        .noneMatch(header -> header.header().equals(BraveHeaders.SUBSCRIPTION_TOKEN.value()));
  }

  /**
   * Adds a header to the list of headers.
   *
   * @param header the header to be added
   * @param value the value of the header
   * @param <K> the type of the header, which must extend {@link SearchHeader}
   * @return the current builder instance
   */
  protected <K extends SearchHeader> T addHeader(@NotNull K header, String value) {
    headers.add(new BraveSearchHeader(header.value(), value));
    return self();
  }

  /**
   * Sets the URI for the request.
   *
   * @param query the URI for the request
   * @return the current builder instance
   */
  protected T queryURI(URI query) {
    internalRequestBuilder.uri(query);
    requestURI = query;
    return self();
  }

  /**
   * Clears the request builder, resetting it to its initial state.
   *
   * @return the current builder instance
   */
  public T clear() {
    internalRequestBuilder = HttpRequest.newBuilder();
    headers = new HashSet<>();
    return self();
  }

  /**
   * Attaches the headers to the request builder.
   *
   * @return the current builder instance
   */
  protected T attachHeaders() {
    internalRequestBuilder.header("Accept", "application/json");
    internalRequestBuilder.header("Accept-Encoding", "gzip");
    headers.stream()
        .map(BraveSearchHeader::toEntry)
        .forEach(header -> internalRequestBuilder.header(header.getKey(), header.getValue()));
    return self();
  }

  /**
   * Executes the request and returns an optional response.
   *
   * @return an optional response to the request
   * @throws InterruptedException if the execution is interrupted
   */
  public Optional<HttpResponse<String>> execute() throws InterruptedException {
    try (HttpClient client = HttpClient.newHttpClient()) {
      if (subscriptionMissing()) {
        throw new BraveClientException("Subscription token is required to execute the request");
      }

      return Optional.of(
          client.send(attachHeaders().internalRequestBuilder.GET().build(), new GzipBodyHandler()));
    } catch (IOException IOEx) {
      Supplier<String> clientEx =
          () ->
              "Unable to process request for "
                  + requestURI.toString()
                  + (Objects.nonNull(IOEx.getCause()) ? " Caused by: " + IOEx.getCause() : "");
      throw new BraveClientException(clientEx);
    }
  }
}
