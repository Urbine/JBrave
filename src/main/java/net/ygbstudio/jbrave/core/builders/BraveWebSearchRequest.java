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
import java.time.ZoneId;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import org.jetbrains.annotations.NotNull;

/**
 * Builds a Brave web search request.
 *
 * <p>It configures the request URI and the request headers (user agent, cache control, API version,
 * subscription token, and the geolocation headers) and builds the underlying {@link HttpRequest}.
 *
 * <p>Clients typically configure web search requests through {@code BraveWebQuery}'s {@code
 * withHeaders} consumer, which receives this builder as a {@link WebHeaderBuilder} typed with
 * {@link BraveWebRequest}.
 *
 * <p>Instances are created with {@link #builder()} and can be reused after calling {@link
 * #clear()}.
 */
public final class BraveWebSearchRequest extends AbstractBraveRequestBuilder<BraveWebSearchRequest>
    implements BraveWebRequest {

  private BraveWebSearchRequest() {}

  /**
   * Creates a new instance of {@link BraveWebSearchRequest}.
   *
   * @return a new instance of {@link BraveWebSearchRequest}
   */
  public static BraveWebSearchRequest builder() {
    return new BraveWebSearchRequest().clear();
  }

  /**
   * Sets the URI for the request.
   *
   * @param uri the request URI
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest queryAddress(URI uri) {
    return queryURI(uri);
  }

  /**
   * Adds a latitude header to the request.
   *
   * @param latitude the latitude value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withLatitude(double latitude) {
    return addHeader(BraveHeaders.LATITUDE, String.valueOf(latitude));
  }

  /**
   * Adds a longitude header to the request.
   *
   * @param longitude the longitude value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withLongitude(double longitude) {
    return addHeader(BraveHeaders.LONGITUDE, String.valueOf(longitude));
  }

  /**
   * Adds a timezone header to the request.
   *
   * @param timezone the timezone value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withTimezone(@NotNull ZoneId timezone) {
    return addHeader(BraveHeaders.TIMEZONE, timezone.toString());
  }

  /**
   * Adds a country header to the request.
   *
   * @param country the country value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withCountry(String country) {
    return addHeader(BraveHeaders.COUNTRY, country);
  }

  /**
   * Adds a city header to the request.
   *
   * @param city the city value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withCity(String city) {
    return addHeader(BraveHeaders.CITY, city);
  }

  /**
   * Adds a state header to the request.
   *
   * @param state the state value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withState(String state) {
    return addHeader(BraveHeaders.STATE, state);
  }

  /**
   * Adds a state name header to the request.
   *
   * @param stateName the state name value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withStateName(String stateName) {
    return addHeader(BraveHeaders.STATE_NAME, stateName);
  }

  /**
   * Adds a postal code header to the request.
   *
   * @param postalCode the postal code value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withPostalCode(String postalCode) {
    return addHeader(BraveHeaders.POSTAL_CODE, postalCode);
  }

  /**
   * Adds a user agent header to the request.
   *
   * @param userAgent the user agent value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withUserAgent(String userAgent) {
    return addHeader(BraveHeaders.USER_AGENT, userAgent);
  }

  /**
   * Adds a cache control header to the request.
   *
   * @param cacheControl the cache control value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withCacheControl(String cacheControl) {
    return addHeader(BraveHeaders.CACHE_CONTROL, cacheControl);
  }

  /**
   * Adds an API version header to the request.
   *
   * @param apiVersion the API version value to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withApiVersion(String apiVersion) {
    return addHeader(BraveHeaders.API_VERSION, apiVersion);
  }

  /**
   * Adds a brave subscription token header to the request.
   *
   * @param subscriptionToken the subscription token to set
   * @return the current instance of {@link BraveWebSearchRequest}
   */
  public BraveWebSearchRequest withToken(String subscriptionToken) {
    return addHeader(BraveHeaders.SUBSCRIPTION_TOKEN, subscriptionToken);
  }
}
