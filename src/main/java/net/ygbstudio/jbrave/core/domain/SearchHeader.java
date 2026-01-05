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

/**
 * An interface representing a header for a search request.
 *
 * <p>This interface is implemented by enums that represent the headers that can be used in a search
 * request. The {@link SearchHeader} interface provides a method to retrieve the value of a header.
 *
 * @author Yoham Gabriel B. (YGBStudio)
 */
public interface SearchHeader {
  /**
   * Returns the value of the header.
   *
   * @return the value of the header
   */
  String value();
}
