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

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashSet;
import java.util.Set;
import net.ygbstudio.jbrave.core.domain.SearchHeader;
import net.ygbstudio.jbrave.core.exceptions.MissingSubscriptionTokenException;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import net.ygbstudio.jbrave.core.model.BraveSearchHeader;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract base class for building Brave API request builders.
 *
 * <p>This class is the base class for all Brave API request builders. It provides the basic
 * functionality to build requests, such as adding headers to the request and building the request
 * itself.
 *
 * <p><strong>Note:</strong> This builder enforces strict request invariants. Subclasses must not
 * override core lifecycle methods. Extension is intended via additional fluent APIs only.
 *
 * @param <T> The concrete builder class.
 */
public abstract class AbstractBraveRequestBuilder<T extends AbstractBraveRequestBuilder<T>> {
  protected Set<BraveSearchHeader> headers;
  protected HttpRequest.Builder internalRequestBuilder;
  protected URI requestURI;
  protected boolean headersAttached;

  /**
   * Returns the current instance of the builder.
   *
   * <p>The cast is safe because subclasses follow the self-referential generic pattern used by this
   * base class.
   *
   * @return Current instance of the builder.
   */
  @SuppressWarnings("unchecked")
  protected final T self() {
    return (T) this;
  }

  /**
   * Sets the URI for the request.
   *
   * @param query the URI for the request
   * @return the current builder instance
   */
  protected final T queryURI(URI query) {
    internalRequestBuilder.uri(query);
    requestURI = query;
    return self();
  }

  /**
   * Adds a header to the list of headers.
   *
   * @param header the header to be added
   * @param value the value of the header
   * @param <K> the type of the header, which must extend {@link SearchHeader}
   * @return the current builder instance
   */
  protected final <K extends SearchHeader> T addHeader(@NotNull K header, String value) {
    headers.add(new BraveSearchHeader(header.value(), value));
    return self();
  }

  /** Attaches the headers to the request builder. */
  protected final void attachHeaders() {
    if (!headersAttached) {
      internalRequestBuilder.header("Accept", "application/json");
      internalRequestBuilder.header("Accept-Encoding", "gzip");
      headers.stream()
          .map(BraveSearchHeader::toEntry)
          .forEach(header -> internalRequestBuilder.header(header.getKey(), header.getValue()));
      headersAttached = true;
    }
  }

  /**
   * Checks if the subscription token header is missing.
   *
   * @return true if the subscription token header is missing, false otherwise.
   */
  protected final boolean subscriptionMissing() {
    return headers.stream()
        .noneMatch(header -> header.header().equals(BraveHeaders.SUBSCRIPTION_TOKEN.value()));
  }

  /**
   * Clears the request builder, resetting it to its initial state.
   *
   * @return the current builder instance
   */
  protected final T clear() {
    headers = new HashSet<>();
    internalRequestBuilder = HttpRequest.newBuilder();
    requestURI = null;
    headersAttached = false;
    return self();
  }

  /**
   * Clears the request builder, resetting it to its initial state, retaining the provided URI.
   *
   * @return the current builder instance
   */
  protected final T clearRetainURI() {
    headers = new HashSet<>();
    internalRequestBuilder = HttpRequest.newBuilder();
    headersAttached = false;
    return self();
  }

  /**
   * Builds the request using the current state of the builder.
   *
   * <p>This method attaches the headers to the request builder and throws a {@link
   * MissingSubscriptionTokenException} if the subscription token header is missing.
   *
   * @return the built {@link HttpRequest}
   * @throws MissingSubscriptionTokenException if the subscription token header is missing
   */
  protected final HttpRequest build() {
    attachHeaders();
    if (subscriptionMissing()) {
      throw new MissingSubscriptionTokenException(
          "Subscription token is required to build the request");
    }
    return internalRequestBuilder.GET().build();
  }
}
