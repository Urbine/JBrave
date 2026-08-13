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

package net.ygbstudio.jbrave.core.model;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.ygbstudio.jbrave.core.domain.SearchHeader;
import org.jetbrains.annotations.NotNull;

/**
 * Enumeration of possible Brave Search API headers.
 *
 * <p>Used as a shorthand for setting custom headers in the API requests.
 */
public enum BraveHeaders implements SearchHeader {
  SUBSCRIPTION_TOKEN("x-subscription-token"),

  // -90.0 >= LATITUDE <= +90.0
  LATITUDE("x-loc-lat"),

  // -180.0 >= LONGITUDE <= +180.0 degrees
  LONGITUDE("x-loc-long"),

  // IANA Timezone for the client's device
  TIMEZONE("x-loc-timezone"),

  // Generic name of the client city
  CITY("x-loc-city"),

  // First-level subdivision (least-specific) of the ISO 3166-2 code
  // STATE is up to three characters
  STATE("x-loc-state"),
  STATE_NAME("x-loc-state-name"),

  // Two-letter country code ISO 3166-1 alpha-2
  COUNTRY("x-loc-country"),

  // Client's postal code
  POSTAL_CODE("x-loc-postal-code"),

  USER_AGENT("User-Agent"),

  // Add 'no-cache' to the request to prevent caching
  CACHE_CONTROL("Cache-Control"),

  // Check the API changelog for details on the current version
  API_VERSION("Api-Version"),

  X_RATE_LIMIT("X-RateLimit-Limit"),

  X_RATE_LIMIT_POLICY("X-RateLimit-Policy"),

  X_RATE_LIMIT_REMAINING("X-RateLimit-Remaining"),

  X_RATE_LIMIT_RESET("X-RateLimit-Reset");

  private final String value;

  BraveHeaders(String value) {
    this.value = value;
  }

  /**
   * Extracts a list of header values of the specified header from the HTTP headers.
   *
   * @param headers the HTTP headers to extract values from
   * @return a list of header values of the specified header
   */
  public List<String> extract(@NotNull HttpHeaders headers) {
    return headers.allValues(value);
  }

  /**
   * Extracts the first value of the specified header from the HTTP headers and transforms it using
   * the provided function.
   *
   * @param headers the HTTP headers to extract values from
   * @param transformer the function used to transform the available header
   * @param <R> optional type of the return value
   * @return the transformed first value of the specified header if present, otherwise an empty
   *     optional
   */
  public <R> Optional<R> extract(@NotNull HttpHeaders headers, Function<String, R> transformer) {
    return headers.firstValue(value).map(transformer);
  }

  @Override
  public String value() {
    return value;
  }
}
