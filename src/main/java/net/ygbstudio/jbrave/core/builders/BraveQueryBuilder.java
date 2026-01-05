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

package net.ygbstudio.jbrave.core.builders;

import java.net.URI;

/**
 * The BraveQueryBuilder interface is a marker interface that is implemented by all builders that
 * build Brave API queries.
 *
 * <p>It is parameterized by the type of the builder.
 *
 * @author Yoham Gabriel B. (YGBStudio)
 */
public sealed interface BraveQueryBuilder<T> permits AbstractQueryUrlBuilder {

  /**
   * Converts the URL query to a URI.
   *
   * @return The URI representation of the URL query.
   */
  URI toURI();

  /**
   * Clears the builder by resetting it to its initial state.
   *
   * <p>This method is used to reset the builder to its initial state before adding any options.
   *
   * @return The current instance of the builder.
   */
  T clear();
}
