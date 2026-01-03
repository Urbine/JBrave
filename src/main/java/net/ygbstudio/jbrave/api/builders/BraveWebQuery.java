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

import java.net.URI;
import java.time.LocalDate;
import java.util.Set;
import net.ygbstudio.jbrave.core.options.provided.CountryIdentifier;
import net.ygbstudio.jbrave.core.options.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.options.provided.RegionLocaleIdentifier;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.ResultFilter;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
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
 * <p>The builder is not thread-safe and not intended to be instantiated directly, instead use the
 * {@link #builder()} method to create a new instance of the builder.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public final class BraveWebQuery extends AbstractQueryUrlBuilder<BraveWebQuery> {

  private BraveWebQuery() {}

  /**
   * Creates a new instance of {@link BraveWebQuery} with an empty URL query.
   *
   * @return A new instance of {@link BraveWebQuery}.
   */
  @Contract(value = " -> new", pure = true)
  public static @NotNull BraveWebQuery builder() {
    return new BraveWebQuery().addInstanceVertical(BraveResource.WEB).clear();
  }

  /**
   * Adds a search term to the URL query.
   *
   * @param queryTerm The search term to add.
   * @return The current instance of the builder.
   */
  public BraveWebQuery query(String queryTerm) {
    return addQueryTerm(queryTerm);
  }

  /**
   * Adds the count option to the URL query.
   *
   * @param count The number of results to return.
   * @return The current instance of the builder.
   */
  public BraveWebQuery count(int count) {
    return addOptionCarrier(SearchOptions.count(count));
  }

  /**
   * Adds the offset option to the URL query.
   *
   * @param offset The number of results to skip.
   * @return The current instance of the builder.
   */
  public BraveWebQuery offset(int offset) {
    return addOptionCarrier(SearchOptions.offset(offset));
  }

  /**
   * Adds the text_decorations option to the URL query.
   *
   * @param textDecorations Whether to include text decorations in the results.
   * @return The current instance of the builder.
   */
  public BraveWebQuery textDecorations(boolean textDecorations) {
    return addOptionCarrier(SearchOptions.textDecorations(textDecorations));
  }

  /**
   * Adds the spellcheck option to the URL query.
   *
   * @param spellCheck Whether to enable spellcheck.
   * @return The current instance of the builder.
   */
  public BraveWebQuery spellCheck(boolean spellCheck) {
    return addOptionCarrier(SearchOptions.spellCheck(spellCheck));
  }

  /**
   * Adds the extra_snippets option to the URL query.
   *
   * @param extraSnippets Whether to include extra snippets in the results.
   * @return The current instance of the builder.
   */
  public BraveWebQuery extraSnippets(boolean extraSnippets) {
    return addOptionCarrier(SearchOptions.extraSnippets(extraSnippets));
  }

  /**
   * Adds the summary option to the URL query.
   *
   * @param summary Whether to include a summary in the results.
   * @return The current instance of the builder.
   */
  public BraveWebQuery summary(boolean summary) {
    return addOptionCarrier(SearchOptions.summary(summary));
  }

  /**
   * Adds the operators option to the URL query. Maximum of 400 characters and 50 words.
   *
   * <p>If a query is already present, subsequent calls are ignored. Only one query term is
   * supported per search request.
   *
   * @param operators Whether to include operators in the results.
   * @return The current instance of the builder.
   */
  public BraveWebQuery operators(boolean operators) {
    return addOptionCarrier(SearchOptions.operators(operators));
  }

  /**
   * Adds the country option to the URL query.
   *
   * @param countryIdentifier The country identifier to set.
   * @return The current instance of the builder.
   */
  public BraveWebQuery country(@NotNull CountryIdentifier countryIdentifier) {
    return addOptionCarrier(countryIdentifier.toSearchOption());
  }

  /**
   * Adds the market option to the URL query.
   *
   * @param regionLocaleIdentifier The region locale identifier to set.
   * @return The current instance of the builder.
   */
  public BraveWebQuery market(@NotNull RegionLocaleIdentifier regionLocaleIdentifier) {
    return addOptionCarrier(regionLocaleIdentifier.toSearchOption());
  }

  /**
   * Adds the language option to the URL query.
   *
   * @param languageIdentifier The language identifier to set.
   * @return The current instance of the builder.
   */
  public BraveWebQuery language(@NotNull LanguageIdentifier languageIdentifier) {
    return addOptionCarrier(languageIdentifier.toSearchOption());
  }

  /**
   * Adds the enable_rich_callback option to the URL query.
   *
   * @param enableRichCallback Whether to enable rich callback.
   * @return The current instance of the builder.
   */
  public BraveWebQuery enableRichCallback(boolean enableRichCallback) {
    return addOptionCarrier(SearchOptions.enableRichCallback(enableRichCallback));
  }

  /**
   * Adds the units option to the URL query.
   *
   * @param units The units to set.
   * @return The current instance of the builder.
   */
  public BraveWebQuery units(@NotNull Units units) {
    return addOptionCarrier(units.toSearchOption());
  }

  /**
   * Adds a freshness option to the URL query using an existing {@link Freshness} instance.
   *
   * @param freshness a {@link Freshness} describing the freshness constraint
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery freshness(@NotNull Freshness freshness) {
    return addOptionCarrier(freshness.toSearchOption());
  }

  /**
   * Adds a freshness option to the URL query representing a date range.
   *
   * @param startDate the start date of the freshness range
   * @param endDate the end date of the freshness range
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery freshness(LocalDate startDate, LocalDate endDate) {
    return addOptionCarrier(Freshness.between(startDate, endDate));
  }

  /**
   * Adds a safe search option to the URL query.
   *
   * @param safeSearch a {@link SafeSearch} enum value specifying the safe search level
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery safeSearch(@NotNull SafeSearch safeSearch) {
    return addOptionCarrier(safeSearch.toSearchOption());
  }

  /**
   * Adds result filters to the URL query.
   *
   * @param resultFilterList a set of {@link ResultFilter} elements to apply; may be empty but not
   *     null
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery resultFilters(Set<ResultFilter> resultFilterList) {
    return addOptionCarrier(ResultFilter.from(resultFilterList));
  }

  /**
   * Adds a goggles URL option to the URL query.
   *
   * @param gogglesUri the {@link URI} pointing to a goggles resource to use for the query
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery goggles(URI gogglesUri) {
    return addOptionCarrier(SearchOptions.goggles(gogglesUri));
  }

  /**
   * Clears the URL query.
   *
   * <p>Exposed here to keep the builder API self-contained and IDE-discoverable.
   *
   * @return The current instance of the builder.
   */
  @Override
  public BraveWebQuery clear() {
    return super.clear();
  }

  /**
   * Builds the URL query and returns it as a string.
   *
   * <p>Exposed here to keep the builder API self-contained and IDE-discoverable.
   *
   * @return The URL query as a string.
   */
  @Override
  public String build() {
    return super.build();
  }
}
