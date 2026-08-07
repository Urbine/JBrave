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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import net.ygbstudio.jbrave.core.domain.ClientProvidedOption;
import net.ygbstudio.jbrave.core.exceptions.BraveGogglesIdentifierException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Enumeration of the search options available in the Brave API.
 *
 * <p>These options can be used to modify the behavior of the API requests.
 */
public enum SearchOptions implements ClientProvidedOption {
  COUNT("count"),
  OFFSET("offset"),
  TEXT_DECORATIONS("text_decorations"),
  SPELLCHECK("spellcheck"),
  EXTRA_SNIPPETS("extra_snippets"),
  ENABLE_RICH_CALLBACK("enable_rich_callback"),
  SUMMARY("summary"),
  OPERATORS("operators"),
  GOGGLES("goggles"),
  RICH("rich"),
  INCLUDE_FETCH_METADATA("include_fetch_metadata");

  private final String value;

  SearchOptions(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Contract(pure = true)
  @Override
  public @NotNull String urlParam() {
    return value + "=";
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#COUNT} and the
   * given value.
   *
   * @param count the value for {@link SearchOptions#COUNT}
   * @return a new instance of {@link BraveSearchOption}
   * @throws IllegalArgumentException if the specified count is less than one.
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Integer> count(int count) {
    if (count < 1)
      throw new IllegalArgumentException(
          "Unable to create a search option for less than 1 element.");
    return BraveSearchOption.of(COUNT, count);
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#OFFSET} and the
   * given value.
   *
   * @param offset the value for {@link SearchOptions#OFFSET}
   * @return a new instance of {@link BraveSearchOption}
   * @throws IllegalArgumentException if specified offset is less than zero.
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Integer> offset(int offset) {
    if (offset < 0)
      throw new IllegalArgumentException(
          "Unable to create a search option with an offset that is less than 0.");
    return BraveSearchOption.of(OFFSET, offset);
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#TEXT_DECORATIONS}
   * and the given value.
   *
   * @param textDecorations the value for {@link SearchOptions#TEXT_DECORATIONS}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Boolean> textDecorations(boolean textDecorations) {
    return BraveSearchOption.of(TEXT_DECORATIONS, textDecorations);
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#SPELLCHECK} and
   * the given value.
   *
   * @param spellCheck the value for {@link SearchOptions#SPELLCHECK}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Boolean> spellCheck(boolean spellCheck) {
    return BraveSearchOption.of(SPELLCHECK, spellCheck);
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#EXTRA_SNIPPETS}
   * and the given value.
   *
   * @param extraSnippets the value for {@link SearchOptions#EXTRA_SNIPPETS}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Boolean> extraSnippets(boolean extraSnippets) {
    return BraveSearchOption.of(EXTRA_SNIPPETS, extraSnippets);
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#SUMMARY} and the
   * given value.
   *
   * @param summary the value for {@link SearchOptions#SUMMARY}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Boolean> summary(boolean summary) {
    return BraveSearchOption.of(SUMMARY, summary);
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#OPERATORS} and the
   * given value.
   *
   * @param operators the value for {@link SearchOptions#OPERATORS}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Boolean> operators(boolean operators) {
    return BraveSearchOption.of(OPERATORS, operators);
  }

  /**
   * Creates a new instance of {@link BraveSearchOption} with {@link
   * SearchOptions#ENABLE_RICH_CALLBACK} and the given value.
   *
   * @param enableRichCallback the value for {@link SearchOptions#ENABLE_RICH_CALLBACK}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Boolean> enableRichCallback(boolean enableRichCallback) {
    return BraveSearchOption.of(ENABLE_RICH_CALLBACK, enableRichCallback);
  }

  /**
   * Creates a new instance of {@link BraveSearchOption} with {@link SearchOptions#GOGGLES} and the
   * given value.
   *
   * @param gogglesUri the value for {@link SearchOptions#GOGGLES}, the URI must be a valid URL too
   * @return a new instance of {@link BraveSearchOption}
   * @throws BraveGogglesIdentifierException if the given gogglesUri is not a valid URL candidate
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<String> goggles(@NotNull URI gogglesUri) {
    try {
      return BraveSearchOption.of(
          GOGGLES, URLEncoder.encode(gogglesUri.toURL().toString(), StandardCharsets.UTF_8));
    } catch (MalformedURLException malformedURlEx) {
      throw new BraveGogglesIdentifierException(
          () -> gogglesUri + " is not a valid URL candidate for the goggles parameter value");
    }
  }

  /**
   * Creates a new instance of {@link BraveSearchOption} with {@link
   * SearchOptions#INCLUDE_FETCH_METADATA} and the given value.
   *
   * @param includeFetchMetadata the value for {@link SearchOptions#INCLUDE_FETCH_METADATA}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Boolean> includeFetchMetadata(
      boolean includeFetchMetadata) {
    return BraveSearchOption.of(INCLUDE_FETCH_METADATA, includeFetchMetadata);
  }

  /**
   * Creates a new instance of {@link BraveSearchOption} with {@link SearchOptions#RICH} and the
   * given value.
   *
   * @param rich the value for {@link SearchOptions#RICH}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Boolean> rich(boolean rich) {
    return BraveSearchOption.of(RICH, rich);
  }
}
