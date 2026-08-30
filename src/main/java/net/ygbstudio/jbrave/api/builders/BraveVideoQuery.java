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
import net.ygbstudio.jbrave.api.response.VideoSearchApiResponse;
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
 * A builder for constructing Brave Video API queries.
 *
 * <p>The builder is not thread-safe and is intended for single-threaded use. It is stateful and
 * reusable: call {@link #reset()} to clear its internal state before reusing it.
 *
 * <p>Builders are not intended to be instantiated directly; use the {@link #builder()} factory
 * method to create a new instance.
 */
public final class BraveVideoQuery extends AbstractQueryUrlBuilder<BraveVideoQuery>
    implements BraveQueryBuilder<BraveVideoQuery, VideoSearchApiResponse> {

  private final BraveVerticalSearchRequest requestBuilder = BraveVerticalSearchRequest.builder();
  private BraveExecutionGate controller;
  private HttpResponse<String> currentResponse;
  private int maxRetries = 0;

  private BraveVideoQuery() {}

  /**
   * Creates a new instance of {@link BraveVideoQuery}
   *
   * @return a new instance of {@link BraveVideoQuery}
   */
  public static BraveVideoQuery builder() {
    return new BraveVideoQuery().addInstanceVertical(BraveResource.VIDEO).clear();
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
  public BraveVideoQuery query(String queryTerm) {
    return addQueryTerm(queryTerm, false);
  }

  /**
   * Adds the search language option to the URL query.
   *
   * @see SearchLanguage
   * @param <T> the search language type
   * @param searchLanguage the search language to set
   * @return the current instance of the builder
   */
  public <T extends LanguageIdentifier> BraveVideoQuery language(@NotNull T searchLanguage) {
    return addOptionCarrier(searchLanguage.toSearchOption());
  }

  /**
   * Adds the market option to the URL query.
   *
   * @see MarketLocale
   * @param <T> the market locale type
   * @param uiLanguage the market locale to set
   * @return the current instance of the builder
   */
  public <T extends RegionLocaleIdentifier> BraveVideoQuery market(@NotNull T uiLanguage) {
    return addOptionCarrier(uiLanguage.toSearchOption());
  }

  /**
   * Adds a safe search option to the URL query.
   *
   * @see net.ygbstudio.jbrave.api.filters.SafeSearch
   * @param safeSearch a {@link SafeSearch} enum value specifying the safe search level
   * @return the current instance of the builder
   */
  public BraveVideoQuery safeSearch(@NotNull SafeSearch safeSearch) {
    return addOptionCarrier(safeSearch.toSearchOption());
  }

  /**
   * Adds the count option to the URL query.
   *
   * @param count the number of results to return
   * @return the current instance of the builder
   */
  public BraveVideoQuery count(int count) {
    return addOptionCarrier(SearchOptions.count(count));
  }

  /**
   * Adds the offset option to the URL query.
   *
   * @param offset the number of results to skip
   * @return the current instance of the builder
   */
  public BraveVideoQuery offset(int offset) {
    return addOptionCarrier(SearchOptions.offset(offset));
  }

  /**
   * Adds the spell check option to the URL query.
   *
   * @param spellcheck whether to enable spellcheck
   * @return the current instance of the builder
   */
  public BraveVideoQuery spellcheck(boolean spellcheck) {
    return addOptionCarrier(SearchOptions.spellCheck(spellcheck));
  }

  /**
   * Adds a freshness option to the URL query using an existing {@link Freshness} instance.
   *
   * @see net.ygbstudio.jbrave.api.filters.Freshness
   * @param freshness a {@link Freshness} describing the freshness constraint
   * @return the current instance of {@link BraveVideoQuery}
   */
  public BraveVideoQuery freshness(@NotNull Freshness freshness) {
    return addOptionCarrier(freshness.toSearchOption());
  }

  /**
   * Adds a freshness option to the URL query representing a date range.
   *
   * @see net.ygbstudio.jbrave.api.filters.Freshness
   * @param startDate the start date of the freshness range
   * @param endDate the end date of the freshness range
   * @return the current instance of {@link BraveVideoQuery}
   */
  public BraveVideoQuery freshness(LocalDate startDate, LocalDate endDate) {
    return addOptionCarrier(Freshness.between(startDate, endDate));
  }

  /**
   * Adds the include_fetch_metadata option to the URL query.
   *
   * @param includeFetchMetadata whether to include fetch metadata in the results
   * @return the current instance of the builder
   */
  public BraveVideoQuery includeFetchMetadata(boolean includeFetchMetadata) {
    return addOptionCarrier(SearchOptions.includeFetchMetadata(includeFetchMetadata));
  }

  /**
   * Adds the operators option to the URL query.
   *
   * <p>This option tells the API to read search operators from the query term.
   *
   * @return the current instance of the builder
   */
  public BraveVideoQuery enableOperators() {
    return addOptionCarrier(SearchOptions.operators(true));
  }

  /**
   * Sets the subscription token header using the provided {@link ClientInfo} instance.
   *
   * @param clientInfo the {@link ClientInfo} instance containing the subscription token
   * @return the current instance of {@link BraveVideoQuery}
   */
  @Contract("_ -> this")
  public BraveVideoQuery withToken(@NotNull ClientInfo clientInfo) {
    if (controller == null) controller = clientInfo.requestGate();
    requestBuilder.withToken(clientInfo.subscriptionToken());
    return this;
  }

  /**
   * Sets the request headers using the provided consumer.
   *
   * @param headers a consumer that receives a {@link VerticalHeaderBuilder} and applies the
   *     request's headers to it
   * @return the current instance of {@link BraveVideoQuery}
   */
  @Contract("_ -> this")
  public BraveVideoQuery withHeaders(
      @NotNull Consumer<VerticalHeaderBuilder<BraveVerticalRequest>> headers) {
    headers.accept(requestBuilder);
    return this;
  }

  /**
   * Adds search operations to the query term for advanced result filtering.
   *
   * @param operators A consumer that accepts a {@link SearchOperatorBuilder} instance and populates
   *     it with operators. The built {@code SearchOperatorBuilder} instance will be used to
   *     construct the operators string.
   * @return the current instance of the builder
   */
  @Contract("_ -> this")
  public BraveVideoQuery withOperators(@NotNull Consumer<SearchOperatorBuilder> operators) {
    SearchOperatorBuilder operatorBuilder = SearchOperatorBuilder.builder();
    operators.accept(operatorBuilder);
    addQueryTerm(operatorBuilder.build(), true);
    return enableOperators();
  }

  /**
   * Sets the maximum number of retry attempts for rate-limited (HTTP 429) responses.
   *
   * <p>Values less than or equal to zero fall back to the default retry policy (one retry). Retries
   * sleep for the server-provided rate limit reset window before each attempt.
   *
   * @param maxRetries the maximum number of retry attempts for rate-limited responses
   * @return the current instance of {@link BraveVideoQuery}
   */
  public BraveVideoQuery withRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  public BraveVideoQuery execute() {
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

  @Override
  public Class<VideoSearchApiResponse> getResponseType() {
    return VideoSearchApiResponse.class;
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

  /**
   * Clears the current instance of {@link BraveVideoQuery} by resetting its state to its initial
   * values.
   *
   * @return the current instance of {@link BraveVideoQuery}
   */
  public BraveVideoQuery reset() {
    currentResponse = null;
    maxRetries = 0;
    requestBuilder.clear();
    return super.clear();
  }
}
