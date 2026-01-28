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

package net.ygbstudio.jbrave.core.builders;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;
import net.ygbstudio.jbrave.core.domain.BraveAPIConstant;
import net.ygbstudio.jbrave.core.domain.SearchOption;
import net.ygbstudio.jbrave.core.domain.SearchVertical;
import net.ygbstudio.jbrave.core.exceptions.AbsentSearchQueryException;
import net.ygbstudio.jbrave.core.exceptions.AbsentSearchVerticalException;
import net.ygbstudio.jbrave.core.exceptions.InvalidQueryTermException;
import net.ygbstudio.jbrave.core.exceptions.UninitializedBuilderException;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract base class for building Brave API queries.
 *
 * <p>Implementations of this class should call {@link #addInstanceVertical(SearchVertical)} before
 * building, and initialize the builder with {@link #clear()} before adding options.
 *
 * <p>The builder will throw {@link AbsentSearchVerticalException} if the vertical is not added
 * before building. The builder will also throw {@link UninitializedBuilderException} if the builder
 * is not cleared before adding options.
 *
 * <p>Suggested implementation of factory method:
 *
 * {@snippet :
 *     public static BraveWebQuery builder() {
 *     return new BraveWebQuery().addInstanceVertical(BraveResource.WEB).clear();
 *   }
 * }
 *
 * <p>The builder will throw {@link AbsentSearchQueryException} if the query string is not present
 * in the URL query.
 *
 * <p><strong>Note:</strong> This builder enforces strict URI construction invariants. Subclasses
 * must not override core lifecycle methods. Extension is intended via additional fluent APIs only.
 *
 * @param <T> The concrete builder class.
 */
public abstract class AbstractQueryUrlBuilder<T extends AbstractQueryUrlBuilder<T>> {

  protected static final String QUERY_PROMPT = "q=";
  protected StringBuilder urlEnd;
  protected StringBuilder delayedOperators;
  protected Set<SearchOption> optionTracker;
  protected StringBuilder urlStart =
      new StringBuilder().append(BraveAPIConstant.SEARCH_API_BASE).append("/");

  /**
   * Returns the current instance of the builder.
   *
   * <p>The cast is safe because the method is declared to return the type parameter T, which is
   * defined as {@code T extends AbstractQueryUrlBuilder<T>}. This means that T will always be a
   * subclass of AbstractQueryUrlBuilder<T>, so it is safe to cast "this" to T.
   *
   * @return Current instance of the builder.
   */
  @SuppressWarnings("unchecked")
  protected final T self() {
    return (T) this;
  }

  /**
   * Checks if the given search option is already present in the URL query.
   *
   * @param option The search option to check.
   * @return {@code true} if the option is not already present in the URL query, {@code false}
   *     otherwise.
   */
  protected final boolean optionMissing(@NotNull SearchOption option) {
    if (optionTracker == null)
      throw new UninitializedBuilderException(
          "Option tracker set is null. Call clear() before adding options");
    return !optionTracker.contains(option);
  }

  /**
   * Checks if the query prompt is already present in the URL query.
   *
   * <p>The query prompt is the string {@code "q="}.
   *
   * @return {@code true} if the query prompt is not already present in the URL query, {@code false}
   *     otherwise.
   */
  protected final boolean queryMissing() {
    return !urlEnd.toString().contains(QUERY_PROMPT);
  }

  /**
   * Checks if the given query term is valid.
   *
   * <p>A query term is valid if it contains fewer than or exactly 400 characters and less than 50
   * words. Both limits are enforced conjunctively, in accordance with the Brave Search API
   * documentation.
   *
   * @param queryTerm The query term to check.
   * @return {@code true} if the query term is valid, {@code false} otherwise.
   */
  protected final boolean isValidQuery(@NotNull String queryTerm) {
    int chars = queryTerm.codePointCount(0, queryTerm.length());
    int words = queryTerm.trim().isEmpty() ? 0 : queryTerm.trim().split("\\s+").length;
    return chars <= 400 && words <= 50;
  }

  /**
   * Adds a vertical to the current builder instance so that it can be reused.
   *
   * <p>This method is typically called when the builder is instantiated <br>
   * since methods like {@link #clear()} depend on an initialized vertical field.
   *
   * @param vertical The vertical of builder instance.
   * @return The current instance of the builder.
   */
  protected final T addInstanceVertical(@NotNull SearchVertical vertical) {
    urlStart.append(vertical.urlParam()).append("?");
    return self();
  }

  /**
   * Adds a query term to the URL query and encodes it using UTF-8. If {@code appendOperator} is
   * true, the new queryTerm will append to the existing query term in the builder as a search
   * operator, if any.
   *
   * @param queryTerm The query term to add.
   * @param appendOperator Whether or not to interpret {@code queryTerm} as search operator and
   *     append it to the existing term or save it for later concatenation.
   * @return The current instance of the builder.
   */
  protected final T addQueryTerm(String queryTerm, boolean appendOperator) {
    boolean isValidQuery = isValidQuery(queryTerm);
    UnaryOperator<String> encodeQuery = query -> URLEncoder.encode(query, StandardCharsets.UTF_8);

    if (queryMissing() && isValidQuery && !appendOperator) {
      urlEnd.insert(
          0,
          QUERY_PROMPT
              + encodeQuery.apply(queryTerm)
              + (delayedOperators.isEmpty() ? "" : delayedOperators)
              + "&");

    } else if (appendOperator && isValidQuery) {
      if (!queryMissing()) {
        int currentQueryEndIndex = urlEnd.indexOf("&", 0);
        String currentQuery = urlEnd.substring(0, currentQueryEndIndex);
        urlEnd.replace(0, currentQueryEndIndex, currentQuery + "+" + encodeQuery.apply(queryTerm));
      } else delayedOperators.append("+").append(encodeQuery.apply(queryTerm));

    } else if (!isValidQuery)
      throw new InvalidQueryTermException(
          "More than 400 characters and 50 words in the query is not allowed");

    return self();
  }

  /**
   * Adds an option to the URL query.
   *
   * @param option The option to add.
   * @param value The value of the option.
   * @param <U> The type of the option value.
   * @return The current instance of the builder.
   */
  protected final <U> T addOption(@NotNull SearchOption option, U value) {
    if (optionMissing(option)) {
      urlEnd.append(option.urlParam()).append(value).append("&");
      optionTracker.add(option);
    }
    return self();
  }

  /**
   * Adds a search option carrier to the URL query.
   *
   * @param optionCarrier The option carrier to add.
   * @param <V> The type of the option value.
   * @return The current instance of the builder.
   */
  protected final <V> T addOptionCarrier(@NotNull SearchOptionCarrier<V> optionCarrier) {
    if (optionMissing(optionCarrier.option())) {
      urlEnd.append(optionCarrier.buildParam()).append("&");
      optionTracker.add(optionCarrier.option());
    }
    return self();
  }

  /**
   * Clears the URL query by resetting the internal builder to an empty state.
   *
   * @return The current instance of the builder.
   */
  public final T clear() {
    urlEnd = new StringBuilder();
    optionTracker = new HashSet<>();
    delayedOperators = new StringBuilder();
    return self();
  }

  /**
   * Builds the final URL query.
   *
   * @return The final URL query.
   */
  protected final String build() {
    // Sanity checks for implementors of builders based on this abstract class
    if (urlStart.toString().equals(BraveAPIConstant.SEARCH_API_BASE + "/")) {
      throw new AbsentSearchVerticalException(
          "Concrete implementations of this abstract class must call the addInstanceVertical() method before building");
    }

    if (urlEnd == null) {
      throw new UninitializedBuilderException(
          "Concrete implementations of this abstract class must initialize builders with the clear() method");
    }

    if (!urlEnd.isEmpty() && !urlEnd.toString().contains(urlStart)) {
      urlEnd.insert(0, urlStart);
    }
    String builtUrl = urlEnd.toString();

    // A query term is compulsory and without it the request is not acceptable
    if (queryMissing()) {
      throw new AbsentSearchQueryException("Query cannot be empty and a term is required");
    }

    return builtUrl.endsWith("&") ? builtUrl.substring(0, builtUrl.lastIndexOf("&")) : builtUrl;
  }

  /**
   * Converts the URL query to a URI.
   *
   * @return The URI representation of the URL query.
   */
  @Contract(" -> new")
  public final @NotNull URI toURI() {
    return URI.create(build());
  }
}
