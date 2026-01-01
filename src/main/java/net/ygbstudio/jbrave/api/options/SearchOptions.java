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

package net.ygbstudio.jbrave.api.options;

import net.ygbstudio.jbrave.api.base.SearchOption;
import net.ygbstudio.jbrave.api.base.model.SearchOptionCarrier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NonNull;

public enum SearchOptions implements SearchOption {
  COUNT("count"),
  OFFSET("offset"),
  TEXT_DECORATIONS("text_decorations"),
  SPELLCHECK("spellcheck"),
  EXTRA_SNIPPETS("extra_snippets"),
  ENABLE_RICH_CALLBACK("enable_rich_callback"),
  SUMMARY("summary"),
  OPERATORS("operators");

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
  public @NonNull String urlParam() {
    return value + "=";
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#COUNT} and the
   * given value.
   *
   * @param count the value for {@link SearchOptions#COUNT}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Integer> count(int count) {
    return BraveSearchOption.of(COUNT, count);
  }

  /**
   * Create a new instance of {@link BraveSearchOption} with {@link SearchOptions#OFFSET} and the
   * given value.
   *
   * @param offset the value for {@link SearchOptions#OFFSET}
   * @return a new instance of {@link BraveSearchOption}
   */
  @Contract("_ -> new")
  public static @NotNull BraveSearchOption<Integer> offset(int offset) {
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
     * This method should not be used to get a valid {@link SearchOptionCarrier} since there are
     * other methods in this class that can provide the caller with type validation and the ability
     * to provide a value.
     *
     * @return a new instance of {@link BraveSearchOption}
     * @throws UnsupportedOperationException if this method is called
     */
    @Contract(" -> fail")
    @Override
    public @NotNull @Unmodifiable SearchOptionCarrier<String> toSearchOption() {
        throw new UnsupportedOperationException(
                "This method should not be used to obtain a valid SearchOptionCarrier. "
                + "Use the other methods in this class to get a SearchOptionCarrier with type validation and the ability to provide a value.");
    }
}
