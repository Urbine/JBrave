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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An interface representing a search vertical.
 *
 * <p>This interface extends {@link BraveAPIConstant} and is implemented by enums that represent
 * search verticals.
 *
 * <p>Each search vertical has a {@link #value()} method that is used to get the parameter value for
 * the API requests. All verticals are followed by the {@link #SEARCH_PATH} string, and it is
 * included here for convenience and readability as it is used in the implementation of the <br>
 * {@link #urlParam()} method by default.
 *
 * @see BraveAPIConstant
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public non-sealed interface SearchVertical extends BraveAPIConstant {
  String SEARCH_PATH = "search";

  /**
   * Creates a URL query string for the given search vertical.
   *
   * <p>This method takes a search vertical and returns a URL query string that can be used to
   * construct a request to the Brave API. The URL query string is constructed by concatenating the
   * value of the search vertical with the {@link #SEARCH_PATH} string and appending {@code "?q="}.
   *
   * @param vertical the search vertical to use for constructing the URL query string
   * @return the URL query string for the given search vertical
   */
  @Contract(pure = true)
  static @NotNull String urlVertical(@NotNull SearchVertical vertical) {
    return vertical.value() + "/" + SEARCH_PATH;
  }
}
