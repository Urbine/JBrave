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

import java.time.LocalDate;
import net.ygbstudio.jbrave.core.LanguageIdentifier;
import net.ygbstudio.jbrave.api.base.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.api.codes.MarketLocale;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.SearchOptions;
import net.ygbstudio.jbrave.api.verticals.BraveResource;
import org.jetbrains.annotations.NotNull;

/**
 * A builder class for building Brave Video API queries.
 *
 * <p>This class is not intended to be instantiated directly, instead use the {@link #builder()}
 * method to create a new instance of the builder.
 *
 * <p>The builder is immutable, reusable and type-safe.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public final class BraveVideoQueryBuilder extends AbstractQueryUrlBuilder<BraveVideoQueryBuilder> {

  private BraveVideoQueryBuilder() {}

  /**
   * Creates a new instance of {@link BraveVideoQueryBuilder}
   *
   * @return a new instance of {@link BraveVideoQueryBuilder}
   */
  public static BraveVideoQueryBuilder builder() {
    return new BraveVideoQueryBuilder().addInstanceVertical(BraveResource.VIDEO).clear();
  }

  /**
   * Adds a search term to the URL query.
   *
   * @param queryTerm the search term to add
   * @return the current instance of the builder
   */
  public BraveVideoQueryBuilder query(String queryTerm) {
    return addQueryTerm(queryTerm);
  }

  /**
   * Adds the search language option to the URL query. Maximum of 400 characters and 50 words.
   *
   * <p>If a query is already present, subsequent calls are ignored. Only one query term is
   * supported per search request.
   *
   * @param searchLanguage the search language to set
   * @return the current instance of the builder
   */
  public BraveVideoQueryBuilder language(@NotNull LanguageIdentifier searchLanguage) {
    return addOptionCarrier(searchLanguage.toSearchOption());
  }

  /**
   * Adds the market option to the URL query.
   *
   * @param uiLanguage the market locale to set
   * @return the current instance of the builder
   */
  public BraveVideoQueryBuilder market(@NotNull MarketLocale uiLanguage) {
    return addOptionCarrier(uiLanguage.toSearchOption());
  }

  /**
   * Adds a safe search option to the URL query.
   *
   * @param safeSearch a {@link SafeSearch} enum value specifying the safe search level
   * @return the current instance of the builder
   */
  public BraveVideoQueryBuilder safeSearch(@NotNull SafeSearch safeSearch) {
    return addOptionCarrier(safeSearch.toSearchOption());
  }

  /**
   * Adds the count option to the URL query.
   *
   * @param count The number of results to return.
   * @return The current instance of the builder.
   */
  public BraveVideoQueryBuilder count(int count) {
    return addOptionCarrier(SearchOptions.count(count));
  }

  /**
   * Adds the offset option to the URL query.
   *
   * @param offset The number of results to skip.
   * @return The current instance of the builder.
   */
  public BraveVideoQueryBuilder offset(int offset) {
    return addOptionCarrier(SearchOptions.offset(offset));
  }

  /**
   * Adds the spell check option to the URL query.
   *
   * @param spellcheck whether to enable spellcheck
   * @return the current instance of the builder
   */
  public BraveVideoQueryBuilder spellcheck(boolean spellcheck) {
    return addOptionCarrier(SearchOptions.spellCheck(spellcheck));
  }

  /**
   * Adds a freshness option to the URL query using an existing {@link Freshness} instance.
   *
   * @param freshness a {@link Freshness} describing the freshness constraint
   * @return the current instance of {@link BraveWebQueryBuilder}
   */
  public BraveVideoQueryBuilder freshness(@NotNull Freshness freshness) {
    return addOptionCarrier(freshness.toSearchOption());
  }

  /**
   * Adds a freshness option to the URL query representing a date range.
   *
   * @param startDate the start date of the freshness range
   * @param endDate the end date of the freshness range
   * @return the current instance of {@link BraveWebQueryBuilder}
   */
  public BraveVideoQueryBuilder freshness(LocalDate startDate, LocalDate endDate) {
    return addOptionCarrier(Freshness.between(startDate, endDate));
  }
}
