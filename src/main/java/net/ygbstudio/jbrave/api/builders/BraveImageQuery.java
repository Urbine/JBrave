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

import net.ygbstudio.jbrave.core.domain.provided.CountryIdentifier;
import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.SearchOptions;
import net.ygbstudio.jbrave.api.verticals.BraveResource;
import org.jetbrains.annotations.NotNull;

/**
 * A builder for constructing Brave Image Search API queries.
 *
 * <p>This class is specifically designed for constructing Brave Image Search API queries, and is
 * not intended to be instantiated directly. Instead, use the {@link #builder()} method to create a
 * new instance of the builder.
 *
 * <p>The builder is immutable, reusable and type-safe.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public final class BraveImageQuery extends AbstractQueryUrlBuilder<BraveImageQuery> {
  private BraveImageQuery() {}

  /**
   * Returns a new instance of {@link BraveImageQuery}
   *
   * @return a new instance of {@link BraveImageQuery}
   */
  public static BraveImageQuery builder() {
    return new BraveImageQuery().addInstanceVertical(BraveResource.IMAGE).clear();
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
  public BraveImageQuery query(String queryTerm) {
    return addQueryTerm(queryTerm);
  }

  /**
   * Adds the search language option to the URL query.
   *
   * @param searchLanguage the search language to set
   * @return the current instance of the builder
   */
  public BraveImageQuery language(@NotNull LanguageIdentifier searchLanguage) {
    return addOptionCarrier(searchLanguage.toSearchOption());
  }

  /**
   * Adds a safe search option to the URL query.
   *
   * @param safeSearch a {@link SafeSearch} enum value specifying the safe search level
   * @return the current instance of the builder
   */
  public BraveImageQuery safeSearch(@NotNull SafeSearch safeSearch) {
    return addOptionCarrier(safeSearch.toSearchOption());
  }

  /**
   * Adds the country option to the URL query.
   *
   * @param country the country identifier to set
   * @return the current instance of the builder
   */
  public BraveImageQuery country(@NotNull CountryIdentifier country) {
    return addOptionCarrier(country.toSearchOption());
  }

  /**
   * Adds the count option to the URL query.
   *
   * @param count the number of results to return
   * @return the current instance of the builder
   */
  public BraveImageQuery count(int count) {
    return addOptionCarrier(SearchOptions.count(count));
  }

  /**
   * Adds the spell check option to the URL query.
   *
   * @param spellCheck whether to enable spellcheck
   * @return the current instance of the builder
   */
  public BraveImageQuery spellcheck(boolean spellCheck) {
    return addOptionCarrier(SearchOptions.spellCheck(spellCheck));
  }
}
