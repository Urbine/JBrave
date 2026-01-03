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

package net.ygbstudio.jbrave.core.domain;

import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;

/**
 * An interface representing a search option that provides a key and a value for a URL parameter.
 *
 * <p>If a class implements this interface, the correctness of the implementation implies that an
 * option enum includes a method to convert it to a {@link SearchOptionCarrier}. Subinterfaces of
 * this interface may include constants that align with the Brave Search API documentation to ensure
 * that all options are supported and their parameter construction is correct.
 *
 * @see SearchOption
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public non-sealed interface ProvidedOption extends SearchOption {
  String UNITS = "units";

  /**
   * Converts this search option to a {@link SearchOptionCarrier} that can be used to build a URL
   * parameter.
   *
   * @return a {@link SearchOptionCarrier} representing this search option
   */
  SearchOptionCarrier<?> toSearchOption();
}
