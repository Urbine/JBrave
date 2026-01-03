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

package net.ygbstudio.jbrave.core;

import net.ygbstudio.jbrave.core.options.ClientProvidedOption;
import net.ygbstudio.jbrave.core.options.ProvidedOption;
import net.ygbstudio.jbrave.core.options.SearchOption;

/**
 * Interface for constants that are used in Brave API.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public sealed interface BraveAPIConstant
    permits
        SearchOption,
        SearchVertical {

  /** The base URL for the Brave API. */
  String SEARCH_API_BASE = "https://api.search.brave.com/res/v1";

  /**
   * Returns the value of the constant. Must be implemented by the enum constants.
   *
   * @return the value of the constant
   */
  String value();

  /**
   * Returns the URL parameter associated with this constant. It can be implemented by default at
   * the interface level with the value of the parameter.
   *
   * <p>This method is used to generate the URL parameter for the API requests and represents a unit
   * of {@code option/parameterName/parameterConstant + "=" + parameterValue}.
   *
   * <p>A {@code parameterValue} may be provided by the API or require that the client provides it.
   * By default, all options/parameters names are provided by the API.
   *
   * @see ClientProvidedOption
   * @see ProvidedOption
   * @see <a href="https://api-dashboard.search.brave.com/app/documentation/web-search/query">Brave
   *     API Web Search - Query Parameters</a>
   * @return the URL parameter associated with this constant
   */
  String urlParam();
}
