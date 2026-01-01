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

package net.ygbstudio.jbrave.api.base;

import net.ygbstudio.jbrave.api.base.model.SearchOptionCarrier;
import org.jspecify.annotations.NonNull;

/**
 * An interface representing a country identifier.
 *
 * <p>This interface is implemented by enums that represent countries. The {@link CountryIdentifier}
 * interface extends {@link BraveAPIConstant} and {@link SearchOption}, providing methods for URL
 * parameter generation.
 *
 * @see BraveAPIConstant
 * @see SearchOption
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public non-sealed interface CountryIdentifier extends BraveAPIConstant, ProvidedOption {
  String URL_PARAM = "country";

  /**
   * Converts this country identifier to a {@link SearchOptionCarrier} that can be used to build a
   * URL parameter.
   *
   * @return a {@link SearchOptionCarrier} representing this country identifier
   */
  SearchOptionCarrier<String> toSearchOption();

  /**
   * Generates the URL parameter for this country identifier.
   *
   * <p>The URL parameter has the format {@code "country=<value>"}, where {@code "<value>"} is the
   * value of this identifier.
   *
   * @param countryIdentifier the identifier to generate the URL parameter for
   * @return the URL parameter for the given identifier
   */
  @NonNull
  static String urlParam(@NonNull CountryIdentifier countryIdentifier) {
    return URL_PARAM + "=" + countryIdentifier.value();
  }
}
