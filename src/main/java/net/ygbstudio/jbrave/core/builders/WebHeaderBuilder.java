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

import java.time.ZoneId;
import org.jetbrains.annotations.NotNull;

/**
 * Configures the web-specific headers of a Brave web search request.
 *
 * <p>Extends {@link VerticalHeaderBuilder} with the geolocation and regional headers supported by
 * web searches: latitude, longitude, timezone, country, city, state, state name, and postal code.
 *
 * <p>The subscription token is not configured here; use the query builder's {@code withToken}
 * method instead.
 *
 * @param <T> the type returned by the configuration methods
 */
public interface WebHeaderBuilder<T extends WebHeaderBuilder<T>> extends VerticalHeaderBuilder<T> {
  /**
   * Adds a latitude header to the request.
   *
   * @param latitude the latitude value to set
   * @return the current instance of {@link T}
   */
  T withLatitude(double latitude);

  /**
   * Adds a longitude header to the request.
   *
   * @param longitude the longitude value to set
   * @return the current instance of {@link T}
   */
  T withLongitude(double longitude);

  /**
   * Adds a timezone header to the request.
   *
   * @param timezone the timezone value to set
   * @return the current instance of {@link T}
   */
  T withTimezone(@NotNull ZoneId timezone);

  /**
   * Adds a country header to the request.
   *
   * @param country the country value to set
   * @return the current instance of {@link T}
   */
  T withCountry(String country);

  /**
   * Adds a city header to the request.
   *
   * @param city the city value to set
   * @return the current instance of {@link T}
   */
  T withCity(String city);

  /**
   * Adds a state header to the request.
   *
   * @param state the state value to set
   * @return the current instance of {@link T}
   */
  T withState(String state);

  /**
   * Adds a state name header to the request.
   *
   * @param stateName the state name value to set
   * @return the current instance of {@link T}
   */
  T withStateName(String stateName);

  /**
   * Adds a postal code header to the request.
   *
   * @param postalCode the postal code value to set
   * @return the current instance of {@link T}
   */
  T withPostalCode(String postalCode);
}
