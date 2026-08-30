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
  /** Subscription token identifying the calling client. */
  SUBSCRIPTION_TOKEN("x-subscription-token"),

  /** Client latitude in degrees, in the range {@code [-90.0, +90.0]}. */
  LATITUDE("x-loc-lat"),

  /** Client longitude in degrees, in the range {@code [-180.0, +180.0]}. */
  LONGITUDE("x-loc-long"),

  /** IANA timezone of the client's device (for example {@code "America/Costa_Rica"}). */
  TIMEZONE("x-loc-timezone"),

  /** Generic name of the client's city. */
  CITY("x-loc-city"),

  /** First-level administrative subdivision (ISO 3166-2); up to three characters. */
  STATE("x-loc-state"),

  /** Human-readable name of the first-level administrative subdivision. */
  STATE_NAME("x-loc-state-name"),

  /** Two-letter country code (ISO 3166-1 alpha-2). */
  COUNTRY("x-loc-country"),

  /** Client's postal code. */
  POSTAL_CODE("x-loc-postal-code"),

  /** {@code User-Agent} request header. */
  USER_AGENT("User-Agent"),

  /** {@code Cache-Control} request header; send {@code no-cache} to prevent caching. */
  CACHE_CONTROL("Cache-Control"),

  /** {@code Api-Version} request header; check the API changelog for the current value. */
  API_VERSION("Api-Version"),

  /** {@code X-RateLimit-Limit} response header reporting the plan's request limit. */
  X_RATE_LIMIT("X-RateLimit-Limit"),

  /** {@code X-RateLimit-Policy} response header describing the rate-limit policy. */
  X_RATE_LIMIT_POLICY("X-RateLimit-Policy"),

  /** {@code X-RateLimit-Remaining} response header reporting the remaining request quota. */
  X_RATE_LIMIT_REMAINING("X-RateLimit-Remaining"),

  /** {@code X-RateLimit-Reset} response header reporting the time until the quota resets. */
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
