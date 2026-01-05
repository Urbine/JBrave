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

import net.ygbstudio.jbrave.core.domain.BraveAPIConstant;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import net.ygbstudio.jbrave.core.domain.ProvidedOption;
import org.jetbrains.annotations.NotNull;

/**
 * An interface representing a region-locale identifier.
 *
 * <p>This interface is implemented by enums that represent region-locale identifiers.
 *
 * <p>Each region-locale identifier has a {@link #value()} method that is used to get the parameter
 * value for the API requests.
 *
 * <p>Each region-locale identifier has a {@link #URL_PARAM} that is used to get the URL parameter
 * for the API requests.
 *
 * @see BraveAPIConstant
 * @author Yoham Gabriel B. (YGBStudio)
 */
public interface RegionLocaleIdentifier extends ProvidedOption {
  String URL_PARAM = "ui_lang";

  /**
   * Converts this region locale identifier to a {@link SearchOptionCarrier} that can be used to
   * build a URL parameter.
   *
   * @return a {@link SearchOptionCarrier} representing this identifier
   */
  SearchOptionCarrier<String> toSearchOption();

  /**
   * Generates the URL parameter for this language identifier.
   *
   * <p>The URL parameter has the format {@code "ui_lang=<value>"}, where {@code "<value>"} is the
   * value of this identifier.
   *
   * @param regionLocaleIdentifier the identifier to generate the URL parameter for
   * @return the URL parameter for the given identifier
   */
  static @NotNull String urlParam(@NotNull RegionLocaleIdentifier regionLocaleIdentifier) {
    return URL_PARAM + "=" + regionLocaleIdentifier.value();
  }
}
