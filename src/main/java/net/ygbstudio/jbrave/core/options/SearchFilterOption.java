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

package net.ygbstudio.jbrave.core.options;

import net.ygbstudio.jbrave.core.BraveAPIConstant;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;

/**
 * An interface representing a search filter.
 *
 * <p>This interface is extended by enums that represent search filters. {@link SearchFilterOption} also
 * offers fields that are used to generate URL parameter combinations for the API requests.
 *
 * @see BraveAPIConstant
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public non-sealed interface SearchFilterOption extends BraveAPIConstant, ProvidedOption {

  /**
   * Converts this search filter to a {@link SearchOptionCarrier} that can be used to build a URL
   * parameter.
   *
   * @return a {@link SearchOptionCarrier} representing this search filter
   */
  SearchOptionCarrier<String> toSearchOption();
}
