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

package net.ygbstudio.jbrave.core.model;

import net.ygbstudio.jbrave.core.domain.ClientProvidedOption;
import net.ygbstudio.jbrave.core.domain.ProvidedOption;
import net.ygbstudio.jbrave.core.domain.SearchOption;

/**
 * An interface that represents a carrier for a {@link SearchOption} and its corresponding value.
 *
 * <p>It acts as a building bridge by uniting {@link ProvidedOption} and {@link
 * ClientProvidedOption} implementations.
 *
 * <p>Options provided by the API offer automatic conversion to a {@link SearchOptionCarrier},
 * whereas {@link SearchOption} implementations that depend on client-provided values need to make
 * sure this conversion happens correctly via typed factories. In other words, your user-provided
 * value must be of type {@code V} according to the primitive type required by the API.
 *
 * <p>This allows for type validation before construction since all parameters and their values are
 * built and send as a single URL string after joining.
 *
 * @param <V> the type of the option provided
 * @author Yoham Gabriel B. (YGBStudio)
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
