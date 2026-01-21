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

import java.util.Map;
import java.util.stream.Stream;
import net.ygbstudio.jbrave.core.domain.SearchHeader;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * A record class representing a Brave Search API header.
 *
 * <p>This class is an immutable representation of a Brave Search API header, which consists of a
 * header name and a corresponding value. It is used for internal handling of headers.
 */
public record BraveSearchHeader(String header, String value) {

  /**
   * Returns a stream of {@link BraveSearchHeader} objects created from the entries of the given
   * map.
   *
   * <p>This method takes a map of {@link SearchHeader} objects and their corresponding values, and
   * converts them into a stream of {@link BraveSearchHeader} objects.
   *
   * @param headerMap a map of {@link SearchHeader} objects and their corresponding values
   * @param <K> the type of the keys in the map, which must extend {@link SearchHeader}
   * @return a stream of {@link BraveSearchHeader} objects
   */
  public static <K extends SearchHeader> Stream<BraveSearchHeader> fromHeaderMap(
      @NotNull Map<K, String> headerMap) {
    return headerMap.entrySet().stream()
        .map(entry -> new BraveSearchHeader(entry.getKey().value(), entry.getValue()));
  }

  /**
   * Returns an immutable entry of this header.
   *
   * <p>This method is used to convert a {@link BraveSearchHeader} object into a {@link
   * java.util.Map.Entry} object, which is useful for adding headers to the request.
   *
   * @return an immutable entry of this header
   */
  @Contract(value = " -> new", pure = true)
  public Map.@NotNull @Unmodifiable Entry<String, String> toEntry() {
    return Map.entry(header, value);
  }
}
