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

import net.ygbstudio.jbrave.core.CountryIdentifier;
import net.ygbstudio.jbrave.core.LanguageIdentifier;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.api.verticals.BraveResource;
import org.jetbrains.annotations.NotNull;

/**
 * A builder class for building Brave Spell Check API queries.
 *
 * <p>This class is not intended to be instantiated directly, instead use the {@link #builder()}
 * method to create a new instance of the builder.
 *
 * <p>The builder is immutable, reusable and type-safe.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public class BraveSpellcheckQueryBuilder
    extends AbstractQueryUrlBuilder<BraveSpellcheckQueryBuilder> {
  private BraveSpellcheckQueryBuilder() {}

  public static BraveSpellcheckQueryBuilder builder() {
    return new BraveSpellcheckQueryBuilder().addInstanceVertical(BraveResource.SPELLCHECK).clear();
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
  public BraveSpellcheckQueryBuilder query(String queryTerm) {
    return addQueryTerm(queryTerm);
  }

  /**
   * Adds the language option to the URL query.
   *
   * @param languageIdentifier The language identifier to set.
   * @return The current instance of the builder.
   */
  public BraveSpellcheckQueryBuilder language(@NotNull LanguageIdentifier languageIdentifier) {
    return addOptionCarrier(languageIdentifier.toSearchOption());
  }

  /**
   * Adds the country option to the URL query.
   *
   * @param countryIdentifier The country identifier to set.
   * @return The current instance of the builder.
   */
  public BraveSpellcheckQueryBuilder country(@NotNull CountryIdentifier countryIdentifier) {
    return addOptionCarrier(countryIdentifier.toSearchOption());
  }
}
