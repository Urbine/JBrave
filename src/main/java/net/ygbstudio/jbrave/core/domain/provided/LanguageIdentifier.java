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

package net.ygbstudio.jbrave.core.domain.provided;

import net.ygbstudio.jbrave.core.domain.ProvidedOption;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import org.jetbrains.annotations.NotNull;

/**
 * An interface representing a language identifier.
 *
 * <p>Each language identifier has a {@link #value()} method that is used to get the parameter value
 * for the API requests.
 */
public interface LanguageIdentifier extends ProvidedOption {
  /** The URL parameter name for the search language option ({@code "search_lang"}). */
  String URL_PARAM = "search_lang";

  /**
   * Converts this language identifier to a {@link SearchOptionCarrier} that can be used to build a
   * URL parameter.
   *
   * @return a {@link SearchOptionCarrier} representing this identifier
   */
  /**
   * Converts this language identifier to a {@link SearchOptionCarrier} for use in a search request.
   *
   * @return the {@link SearchOptionCarrier} representing this language
   */
  SearchOptionCarrier<String> toSearchOption();

  /**
   * Generates the URL parameter for this language identifier.
   *
   * <p>The URL parameter has the format {@code "search_lang=<value>"}, where {@code "<value>"} is
   * the value of this identifier.
   *
   * @param languageIdentifier the identifier to generate the URL parameter for
   * @return the URL parameter for the given identifier
   */
  static @NotNull String urlParam(@NotNull LanguageIdentifier languageIdentifier) {
    return URL_PARAM + "=" + languageIdentifier.value();
  }
}
