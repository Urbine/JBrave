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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.MarketLocale;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.api.response.NewsSearchApiResponse;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.core.builders.BraveVerticalRequest;
import net.ygbstudio.jbrave.core.builders.BraveVerticalSearchRequest;
import net.ygbstudio.jbrave.core.builders.SearchOperatorBuilder;
import net.ygbstudio.jbrave.core.builders.VerticalHeaderBuilder;
import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.domain.provided.RegionLocaleIdentifier;
import net.ygbstudio.jbrave.core.domain.verticals.BraveResource;
import net.ygbstudio.jbrave.core.executors.BraveExecutionGate;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import net.ygbstudio.jbrave.core.model.SearchOptions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A builder for constructing Brave News API queries.
 *
 * <p>The builder is not thread-safe and is intended for single-threaded use. It is stateful and
 * reusable: call {@link #reset()} to clear its internal state before reusing it.
 *
 * <p>Builders are not intended to be instantiated directly; use the {@link #builder()} factory
 * method to create a new instance.
 */
public final class BraveNewsQuery extends AbstractQueryUrlBuilder<BraveNewsQuery>
    implements BraveQueryBuilder<BraveNewsQuery, NewsSearchApiResponse> {

  private final BraveVerticalSearchRequest requestBuilder = BraveVerticalSearchRequest.builder();
  private BraveExecutionGate controller;
  private HttpResponse<String> currentResponse;
  private int maxRetries = 0;

  private BraveNewsQuery() {}

  /**
   * Creates a new instance of {@link BraveNewsQuery}.
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
    return addQueryTerm(queryTerm, false);
  }

  /**
   * Adds the search language option to the URL query.
   *
   * @see SearchLanguage
   * @param searchLanguage the search language to set
   * @return the current instance of the builder
   */
  public <T extends LanguageIdentifier> BraveNewsQuery language(@NotNull T searchLanguage) {
    return addOptionCarrier(searchLanguage.toSearchOption());
  }

  /**
   * Adds the market option to the URL query.
   *
   * @see MarketLocale
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

  /**
   * Adds the include_fetch_metadata option to the URL query.
   *
   * @param includeFetchMetadata whether to include fetch metadata in the results
   * @return the current instance of the builder
   */
  public BraveNewsQuery includeFetchMetadata(boolean includeFetchMetadata) {
    return addOptionCarrier(SearchOptions.includeFetchMetadata(includeFetchMetadata));
  }

  /**
   * Adds the operators option to the URL query.
   *
   * <p>This option tells the API to read search operators from the query term.
   *
   * @param enableOperators whether to enable the operators option
   * @return the current instance of the builder
   */
  public BraveNewsQuery enableOperators(boolean enableOperators) {
    return addOptionCarrier(SearchOptions.operators(enableOperators));
  }

  /**
   * Adds search operations to the query term for advanced result filtering.
   *
   * <p>Make sure to include the {@link #enableOperators(boolean)} method to tell the API that you
   * will be including operators in your query.
   *
   * @param operators a consumer that accepts a {@link SearchOperatorBuilder} instance and populates
   *     it with operators. The built {@code SearchOperatorBuilder} instance will be used to
   *     construct the operators string.
   * @return the current instance of the builder
   */
  @Contract("_ -> this")
  public BraveNewsQuery withSearchOperators(@NotNull Consumer<SearchOperatorBuilder> operators) {
    SearchOperatorBuilder operatorBuilder = SearchOperatorBuilder.builder();
    operators.accept(operatorBuilder);
    addQueryTerm(operatorBuilder.build(), true);
    return this;
  }

  /**
   * Sets the subscription token header using the provided {@link ClientInfo} instance.
   *
   * @param clientInfo the {@link ClientInfo} instance containing the subscription token
   * @return the current instance of {@link BraveNewsQuery}
   */
  @Contract("_ -> this")
  public BraveNewsQuery withToken(@NotNull ClientInfo clientInfo) {
    if (controller == null) controller = clientInfo.requestGate();
    requestBuilder.withToken(clientInfo.subscriptionToken());
    return this;
  }

  /**
   * Sets the request headers using the provided consumer.
   *
   * @param headers a consumer that receives a {@link VerticalHeaderBuilder} and applies the
   *     request's headers to it
   * @return the current instance of {@link BraveNewsQuery}
   */
  @Contract("_ -> this")
  public BraveNewsQuery withHeaders(
      @NotNull Consumer<VerticalHeaderBuilder<BraveVerticalRequest>> headers) {
    headers.accept(requestBuilder);
    return this;
  }

  /**
   * Sets the maximum number of retry attempts for rate-limited (HTTP 429) responses.
   *
   * <p>Values less than or equal to zero fall back to the default retry policy (one retry). Retries
   * sleep for the server-provided rate limit reset window before each attempt.
   *
   * @param maxRetries the maximum number of retry attempts for rate-limited responses
   * @return the current instance of {@link BraveNewsQuery}
   */
  public BraveNewsQuery withRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  public BraveNewsQuery execute() {
    HttpRequest suppliedTask = requestBuilder.queryAddress(toURI()).build();
    currentResponse =
        maxRetries > 0
            ? controller.submit(suppliedTask, maxRetries)
            : controller.submit(suppliedTask);
    return this;
  }

  public boolean hasExecuted() {
    return Objects.nonNull(currentResponse);
  }

  /**
   * Converts the current query to an {@link HttpRequest} instance.
   *
   * @return the {@link HttpRequest} instance representing the current query
   */
  public HttpRequest toHttpRequest() {
    return requestBuilder.queryAddress(toURI()).build();
  }

  /**
   * Returns the current HTTP response as an {@link Optional}.
   *
   * <p>If the request has not been executed, this method will execute the request and return the
   * {@link HttpResponse}.
   *
   * @return an {@link Optional} containing the current HTTP response.
   */
  public @NotNull Optional<HttpResponse<String>> getHttpResponse() {
    return Objects.nonNull(currentResponse)
        ? Optional.of(currentResponse)
        : execute().getHttpResponse();
  }

  @Override
  public Class<NewsSearchApiResponse> getResponseType() {
    return NewsSearchApiResponse.class;
  }

  /**
   * Clears the current instance of {@link BraveNewsQuery} by resetting its state to its initial
   * values.
   *
   * @return the current instance of {@link BraveNewsQuery}
   */
  public BraveNewsQuery reset() {
    currentResponse = null;
    maxRetries = 0;
    requestBuilder.clear();
    return super.clear();
  }
}
