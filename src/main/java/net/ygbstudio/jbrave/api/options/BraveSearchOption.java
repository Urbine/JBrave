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

package net.ygbstudio.jbrave.api.options;

import net.ygbstudio.jbrave.core.domain.SearchOption;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

/**
 * A search option that can be used in the search methods.
 *
 * <p>It is typically used as a factory for the {@link SearchOptions} implementation.
 *
 * @param <V> type of the parameter value for a search option.
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public record BraveSearchOption<V>(SearchOption option, V value) implements SearchOptionCarrier<V> {
  /**
   * Creates a new {@link BraveSearchOption} with the given key and value.
   *
   * @param key the search option key
   * @param value the value of the search option
   * @param <V> the type of the value
   * @return a new {@link BraveSearchOption}
   */
  @Contract("_, _ -> new")
  public static <V> @NotNull BraveSearchOption<V> of(SearchOption key, V value) {
    return new BraveSearchOption<>(key, value);
  }

  /**
   * Builds the URL parameter for this search option.
   *
   * @return the URL parameter for this search option
   */
  @NonNull
  public String buildParam() {
    String optionParam = option.urlParam();
    return optionParam.endsWith("=") ? optionParam + value : optionParam;
  }
}
