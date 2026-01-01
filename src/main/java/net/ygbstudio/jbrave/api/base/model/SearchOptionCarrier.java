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

package net.ygbstudio.jbrave.api.base.model;

import net.ygbstudio.jbrave.api.base.SearchOption;

/**
 * An interface that represents a carrier for a {@link SearchOption} and its corresponding value.
 *
 * @param <V> the type of the option provided
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public interface SearchOptionCarrier<V> {
  /**
   * Returns the {@link SearchOption} associated with this carrier.
   *
   * @return the {@link SearchOption}
   */
  SearchOption option();

  /**
   * Returns the value associated with this carrier.
   *
   * @return the value
   */
  V value();

  /**
   * Builds the URL-ready parameter for this search option to be joined.
   *
   * <p>For example: {@code option=value}
   *
   * @return the URL parameter for this search option
   */
  String buildParam();
}
