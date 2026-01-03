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
import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.api.codes.MarketLocale;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.SearchOptions;
import net.ygbstudio.jbrave.api.verticals.BraveResource;
import net.ygbstudio.jbrave.core.domain.provided.RegionLocaleIdentifier;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link BraveNewsQuery} class provides a builder for building Brave News API queries.
 *
 * <p>This class is not intended to be instantiated directly, instead use the {@link #builder()}
 * method to create a new instance of the builder.
 *
 * <p>The builder is immutable, reusable and type-safe.
 *
 * @author Yoham Gabriel B. (YGBStudio)
 */
public final class BraveNewsQuery extends AbstractQueryUrlBuilder<BraveNewsQuery> {

  private BraveNewsQuery() {}

  /**
   * Creates a new instance of {@link BraveNewsQuery}
   *
   * @return a new instance of {@link BraveNewsQuery}
   */
  public static BraveNewsQuery builder() {
    return new BraveNewsQuery().addInstanceVertical(BraveResource.NEWS).clear();
  }

  /**
   * Adds a search term to the URL query. Maximum of 400 characters and 50 words.
   *
   * <p>If a query is already present, subsequent calls are ignored. Only one query term is
   * supported per search request.
   *
   * @param queryTerm the search term to add
   * @return the current instance of the builder
   */
  public BraveNewsQuery query(String queryTerm) {
    return addQueryTerm(queryTerm);
  }

  /**
   * Adds the search language option to the URL query.
   *
   * @see net.ygbstudio.jbrave.api.codes.SearchLanguage
   * @param searchLanguage the search language to set
   * @return the current instance of the builder
   */
  public <T extends LanguageIdentifier> BraveNewsQuery language(@NotNull T searchLanguage) {
    return addOptionCarrier(searchLanguage.toSearchOption());
  }

  /**
   * Adds the market option to the URL query.
   *
   * @see net.ygbstudio.jbrave.api.codes.MarketLocale
   * @param uiLanguage the market locale to set
   * @return the current instance of the builder
   */
  public <T extends RegionLocaleIdentifier> BraveNewsQuery market(@NotNull T uiLanguage) {
    return addOptionCarrier(uiLanguage.toSearchOption());
  }

  /**
   * Adds a safe search option to the URL query.
   *
   * @param safeSearch a {@link SafeSearch} enum value specifying the safe search level
   * @return the current instance of the builder
   */
  public BraveNewsQuery safeSearch(@NotNull SafeSearch safeSearch) {
    return addOptionCarrier(safeSearch.toSearchOption());
  }

  /**
   * Adds the count option to the URL query.
   *
   * @param count the number of results to return
   * @return the current instance of the builder
   */
  public BraveNewsQuery count(int count) {
    return addOptionCarrier(SearchOptions.count(count));
  }

  /**
   * Adds the offset option to the URL query.
   *
   * @param offset the number of results to skip
   * @return the current instance of the builder
   */
  public BraveNewsQuery offset(int offset) {
    return addOptionCarrier(SearchOptions.offset(offset));
  }

  /**
   * Adds the spell check option to the URL query.
   *
   * @param spellcheck whether to enable spellcheck
   * @return the current instance of the builder
   */
  public BraveNewsQuery spellcheck(boolean spellcheck) {
    return addOptionCarrier(SearchOptions.spellCheck(spellcheck));
  }

  /**
   * Adds a freshness option to the URL query using an existing {@link Freshness} instance.
   *
   * @see net.ygbstudio.jbrave.api.filters.Freshness
   * @param startDate the start date of the freshness constraint
   * @param endDate the end date of the freshness constraint
   * @return the current instance of the builder
   */
  public BraveNewsQuery freshness(LocalDate startDate, LocalDate endDate) {
    return addOptionCarrier(Freshness.between(startDate, endDate));
  }

  /**
   * Adds a freshness option to the URL query using an existing {@link Freshness} instance.
   *
   * @see net.ygbstudio.jbrave.api.filters.Freshness
   * @param freshness a non-null {@link Freshness} describing the freshness constraint
   * @return the current instance of {@link BraveNewsQuery}
   */
  public BraveNewsQuery freshness(@NotNull Freshness freshness) {
    return addOptionCarrier(freshness.toSearchOption());
  }

  /**
   * Adds the extra_snippets option to the URL query.
   *
   * @param extraSnippets whether to include extra snippets in the results
   * @return the current instance of the builder
   */
  public BraveNewsQuery extraSnippets(boolean extraSnippets) {
    return addOptionCarrier(SearchOptions.extraSnippets(extraSnippets));
  }

  /**
   * Adds a goggles URL option to the URL query.
   *
   * @param gogglesUri the {@link URI} pointing to a goggles resource to use for the query
   * @return the current instance of {@link BraveNewsQuery}
   */
  public BraveNewsQuery goggles(URI gogglesUri) {
    return addOptionCarrier(SearchOptions.goggles(gogglesUri));
  }
}
