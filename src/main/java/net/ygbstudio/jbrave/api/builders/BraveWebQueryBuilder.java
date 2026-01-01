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

package net.ygbstudio.jbrave.api.builders;

import net.ygbstudio.jbrave.api.base.CountryIdentifier;
import net.ygbstudio.jbrave.api.base.LanguageIdentifier;
import net.ygbstudio.jbrave.api.base.RegionLocaleIdentifier;
import net.ygbstudio.jbrave.api.base.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.api.options.SearchOptions;
import net.ygbstudio.jbrave.api.options.Units;
import net.ygbstudio.jbrave.api.verticals.BraveResource;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A builder class for constructing Brave Web search queries.
 *
 * <p>This class provides a fluent API for constructing Brave Web search queries. The builder allows
 * clients to specify various options such as the search term, the number of results to return, the
 * offset, the units in which to display the results, and more.
 *
 * <p>The builder is not thread-safe.
 *
 * @see BraveResource
 * @see SearchOptions
 */
public class BraveWebQueryBuilder extends AbstractQueryUrlBuilder<BraveWebQueryBuilder> {

  private BraveWebQueryBuilder() {}

  /**
   * Creates a new instance of {@link BraveWebQueryBuilder} with an empty URL query.
   *
   * @return A new instance of {@link BraveWebQueryBuilder}.
   */
  @Contract(value = " -> new", pure = true)
  public static @NotNull BraveWebQueryBuilder builder() {
    return new BraveWebQueryBuilder().addInstanceVertical(BraveResource.WEB).clear();
  }

  /**
   * Adds a search term to the URL query.
   *
   * @param queryTerm The search term to add.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder term(String queryTerm) {
    return addQueryTerm(queryTerm);
  }

  /**
   * Adds the count option to the URL query.
   *
   * @param count The number of results to return.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder count(int count) {
    return addOptionCarrier(SearchOptions.count(count));
  }

  /**
   * Adds the offset option to the URL query.
   *
   * @param offset The number of results to skip.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder offset(int offset) {
    return addOptionCarrier(SearchOptions.offset(offset));
  }

  /**
   * Adds the text_decorations option to the URL query.
   *
   * @param textDecorations Whether to include text decorations in the results.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder textDecorations(boolean textDecorations) {
    return addOptionCarrier(SearchOptions.textDecorations(textDecorations));
  }

  /**
   * Adds the spellcheck option to the URL query.
   *
   * @param spellCheck Whether to enable spellcheck.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder spellCheck(boolean spellCheck) {
    return addOptionCarrier(SearchOptions.spellCheck(spellCheck));
  }

  /**
   * Adds the extra_snippets option to the URL query.
   *
   * @param extraSnippets Whether to include extra snippets in the results.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder extraSnippets(boolean extraSnippets) {
    return addOptionCarrier(SearchOptions.extraSnippets(extraSnippets));
  }

  /**
   * Adds the summary option to the URL query.
   *
   * @param summary Whether to include a summary in the results.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder summary(boolean summary) {
    return addOptionCarrier(SearchOptions.summary(summary));
  }

  /**
   * Adds the operators option to the URL query.
   *
   * @param operators Whether to include operators in the results.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder operators(boolean operators) {
    return addOptionCarrier(SearchOptions.operators(operators));
  }

  /**
   * Adds the country option to the URL query.
   *
   * @param countryIdentifier The country identifier to set.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder country(@NotNull CountryIdentifier countryIdentifier) {
    return addOptionCarrier(countryIdentifier.toSearchOption());
  }

  /**
   * Adds the market option to the URL query.
   *
   * @param regionLocaleIdentifier The region locale identifier to set.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder market(@NotNull RegionLocaleIdentifier regionLocaleIdentifier) {
    return addOptionCarrier(regionLocaleIdentifier.toSearchOption());
  }

  /**
   * Adds the language option to the URL query.
   *
   * @param languageIdentifier The language identifier to set.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder language(@NotNull LanguageIdentifier languageIdentifier) {
    return addOptionCarrier(languageIdentifier.toSearchOption());
  }

  /**
   * Adds the enable_rich_callback option to the URL query.
   *
   * @param enableRichCallback Whether to enable rich callback.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder enableRichCallback(boolean enableRichCallback) {
    return addOptionCarrier(SearchOptions.enableRichCallback(enableRichCallback));
  }

  /**
   * Adds the units option to the URL query.
   *
   * @param units The units to set.
   * @return The current instance of the builder.
   */
  public BraveWebQueryBuilder units(@NotNull Units units) {
    return addOptionCarrier(units.toSearchOption());
  }

  @Override
  public BraveWebQueryBuilder clear() {
    return super.clear();
  }

  @Override
  public String build() {
    return super.build();
  }
}
