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

import net.ygbstudio.jbrave.core.domain.SearchHeader;

/**
 * Enumeration of possible Brave Search API headers.
 *
 * <p>Used as a shorthand for setting custom headers in the API requests.
 *
 * @author Yoham Gabriel B. (YGBStudio)
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

  USER_AGENT("User-Agent");

  private final String value;

  BraveHeaders(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }
}
