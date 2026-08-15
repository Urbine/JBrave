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
import net.ygbstudio.jbrave.core.model.BraveHeaders;

/**
 * Builds a Brave vertical search request.
 *
 * <p>It configures the request URI and the standard request headers (user agent, cache control, API
 * version, and subscription token) and builds the underlying {@link HttpRequest}.
 *
 * <p>Clients typically configure vertical search requests through the query builders' {@code
 * withHeaders} consumers, which receive this builder as a {@link VerticalHeaderBuilder} typed with
 * {@link BraveVerticalRequest}.
 *
 * <p>Instances are created with {@link #builder()} and can be reused after calling {@link
 * #clear()}.
 */
public final class BraveVerticalSearchRequest
    extends AbstractBraveRequestBuilder<BraveVerticalSearchRequest>
    implements BraveVerticalRequest {

  private BraveVerticalSearchRequest() {}

  /**
   * Creates a new instance of {@link BraveVerticalSearchRequest}.
   *
   * @return a new instance of {@link BraveVerticalSearchRequest}
   */
  public static BraveVerticalSearchRequest builder() {
    return new BraveVerticalSearchRequest().clear();
  }

  /**
   * Sets the URI for the request.
   *
   * @param uri the request URI
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest queryAddress(URI uri) {
    return queryURI(uri);
  }

  /**
   * Adds a user agent header to the request.
   *
   * @param userAgent the user agent value to set
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest withUserAgent(String userAgent) {
    return addHeader(BraveHeaders.USER_AGENT, userAgent);
  }

  /**
   * Adds a cache control header to the request.
   *
   * @param cacheControl the cache control value to set
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest withCacheControl(String cacheControl) {
    return addHeader(BraveHeaders.CACHE_CONTROL, cacheControl);
  }

  /**
   * Adds an API version header to the request.
   *
   * @param apiVersion the API version value to set
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest withApiVersion(String apiVersion) {
    return addHeader(BraveHeaders.API_VERSION, apiVersion);
  }

  /**
   * Adds a brave subscription token header to the request.
   *
   * @param subscriptionToken the subscription token to set
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest withToken(String subscriptionToken) {
    return addHeader(BraveHeaders.SUBSCRIPTION_TOKEN, subscriptionToken);
  }
}
