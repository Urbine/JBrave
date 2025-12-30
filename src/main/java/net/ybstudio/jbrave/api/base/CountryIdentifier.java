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

package net.ybstudio.jbrave.api.base;

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
public non-sealed interface CountryIdentifier extends BraveAPIConstant, SearchOption {
  String URL_PARAM = "country";

  @NonNull
  static String urlParam(@NonNull CountryIdentifier countryIdentifier) {
    return URL_PARAM + "=" + countryIdentifier.value();
  }
}
