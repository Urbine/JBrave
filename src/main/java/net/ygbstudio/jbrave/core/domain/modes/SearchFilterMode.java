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

package net.ygbstudio.jbrave.core.domain.modes;

import net.ygbstudio.jbrave.core.domain.ClientProvidedOption;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.ResultFilter;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Enumeration class representing filter modes for the Brave Search API.
 *
 * <p>This enumeration includes all possible filter modes that can be used in the Brave Search API,
 * and it unifies filter classes with their base constants under the subtype. These constants can be
 * used with methods that take a supertype of {@link ClientProvidedOption} as well as their value
 * implementations in this package.
 *
 * <p>{@link SearchFilterMode} constants were intentionally separated from their value
 * implementations: {@link Freshness}, {@link ResultFilter}, and {@link SafeSearch} to enhance
 * discoverability and avoid ambiguity about the correct use of value enums since these constants
 * may not be relevant for most clients using this library.
 *
 * @author Yoham Gabriel B. (YGBStudio)
 */
@Internal
public enum SearchFilterMode implements ClientProvidedOption {
  FRESHNESS("freshness"),
  SAFE_SEARCH("safesearch"),
  RESULT_FILTER("result_filter");

  private final String value;

  SearchFilterMode(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Contract(pure = true)
  @Override
  public @NotNull String urlParam() {
    return this.value + "=";
  }
}
