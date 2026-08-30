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
import java.util.Set;
import java.util.function.Consumer;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.ResultFilter;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.options.MarketLocale;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.api.options.Units;
import net.ygbstudio.jbrave.api.response.WebSearchApiResponse;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.core.builders.BraveWebRequest;
import net.ygbstudio.jbrave.core.builders.BraveWebSearchRequest;
import net.ygbstudio.jbrave.core.builders.SearchOperatorBuilder;
import net.ygbstudio.jbrave.core.builders.WebHeaderBuilder;
import net.ygbstudio.jbrave.core.domain.provided.CountryIdentifier;
import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.domain.provided.RegionLocaleIdentifier;
import net.ygbstudio.jbrave.core.domain.verticals.BraveResource;
import net.ygbstudio.jbrave.core.executors.BraveExecutionGate;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import net.ygbstudio.jbrave.core.model.SearchOptions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A builder for constructing Brave Web search queries.
 *
 * <p>This class provides a fluent API for constructing Brave Web search queries. The builder allows
 * clients to specify various options such as the search term, the number of results to return, the
 * offset, the units in which to display the results, and more.
 *
 * <p>The builder is not thread-safe and is intended for single-threaded use. It is stateful and
 * reusable: call {@link #reset()} to clear its internal state before reusing it.
 *
 * <p>Builders are not intended to be instantiated directly; use the {@link #builder()} factory
 * method to create a new instance.
 */
public final class BraveWebQuery extends AbstractQueryUrlBuilder<BraveWebQuery>
    implements BraveQueryBuilder<BraveWebQuery, WebSearchApiResponse> {

  private final BraveWebSearchRequest requestBuilder = BraveWebSearchRequest.builder();
  private BraveExecutionGate controller;
  private HttpResponse<String> currentResponse;
  private int maxRetries = 0;

  private BraveWebQuery() {}

  /**
   * Creates a new instance of {@link BraveWebQuery} with an empty URL query.
   *
   * @return a new instance of {@link BraveWebQuery} with an empty URL query
   */
  @Contract(value = " -> new")
  public static @NotNull BraveWebQuery builder() {
    return new BraveWebQuery().addInstanceVertical(BraveResource.WEB).reset();
  }

  /**
   * Adds a search term to the URL query.
   *
   * @param queryTerm the search term to add
   * @return the current instance of the builder
   */
  public BraveWebQuery query(String queryTerm) {
    return addQueryTerm(queryTerm, false);
  }

  /**
   * Adds the count option to the URL query.
   *
   * @param count the number of results to return
   * @return the current instance of the builder
   */
  public BraveWebQuery count(int count) {
    return addOptionCarrier(SearchOptions.count(count));
  }

  /**
   * Adds the offset option to the URL query.
   *
   * @param offset the number of results to skip
   * @return the current instance of the builder
   */
  public BraveWebQuery offset(int offset) {
    return addOptionCarrier(SearchOptions.offset(offset));
  }

  /**
   * Adds the text_decorations option to the URL query.
   *
   * @param textDecorations whether to include text decorations in the results
   * @return the current instance of the builder
   */
  public BraveWebQuery textDecorations(boolean textDecorations) {
    return addOptionCarrier(SearchOptions.textDecorations(textDecorations));
  }

  /**
   * Adds the spellcheck option to the URL query.
   *
   * @param spellCheck whether to enable spellcheck
   * @return the current instance of the builder
   */
  public BraveWebQuery spellCheck(boolean spellCheck) {
    return addOptionCarrier(SearchOptions.spellCheck(spellCheck));
  }

  /**
   * Adds the extra_snippets option to the URL query.
   *
   * @param extraSnippets whether to include extra snippets in the results
   * @return the current instance of the builder
   */
  public BraveWebQuery extraSnippets(boolean extraSnippets) {
    return addOptionCarrier(SearchOptions.extraSnippets(extraSnippets));
  }

  /**
   * Adds the summary option to the URL query.
   *
   * @param summary whether to include a summary in the results
   * @return the current instance of the builder
   */
  public BraveWebQuery summary(boolean summary) {
    return addOptionCarrier(SearchOptions.summary(summary));
  }

  /**
   * Adds the operators option to the URL query.
   *
   * <p>This option tells the API to read search operators from the query term.
   *
   * @return the current instance of the builder
   */
  public BraveWebQuery enableOperators() {
    return addOptionCarrier(SearchOptions.operators(true));
  }

  /**
   * Adds search operations to the query term for advanced result filtering.
   *
   * @param operators a consumer that accepts a {@link SearchOperatorBuilder} instance and populates
   *     it with operators. The built {@code SearchOperatorBuilder} instance will be used to
   *     construct the operators string.
   * @return the current instance of the builder
   */
  @Contract("_ -> this")
  public BraveWebQuery withOperators(@NotNull Consumer<SearchOperatorBuilder> operators) {
    SearchOperatorBuilder operatorBuilder = SearchOperatorBuilder.builder();
    operators.accept(operatorBuilder);
    addQueryTerm(operatorBuilder.build(), true);
    return enableOperators();
  }

  /**
   * Adds the country option to the URL query.
   *
   * @see Country
   * @param <T> the country type
   * @param country the country identifier to set
   * @return the current instance of the builder
   */
  public <T extends CountryIdentifier> BraveWebQuery country(@NotNull T country) {
    return addOptionCarrier(country.toSearchOption());
  }

  /**
   * Adds the market option to the URL query.
   *
   * @see MarketLocale
   * @param <T> the region locale type
   * @param regionLocale the region locale identifier to set
   * @return the current instance of the builder
   */
  public <T extends RegionLocaleIdentifier> BraveWebQuery market(@NotNull T regionLocale) {
    return addOptionCarrier(regionLocale.toSearchOption());
  }

  /**
   * Adds the language option to the URL query.
   *
   * @see SearchLanguage
   * @param <T> the language type
   * @param languageIdentifier the language identifier to set
   * @return the current instance of the builder
   */
  public <T extends LanguageIdentifier> BraveWebQuery language(@NotNull T languageIdentifier) {
    return addOptionCarrier(languageIdentifier.toSearchOption());
  }

  /**
   * Adds the enable_rich_callback option to the URL query.
   *
   * @param enableRichCallback whether to enable rich callback
   * @return the current instance of the builder
   */
  public BraveWebQuery enableRichCallback(boolean enableRichCallback) {
    return addOptionCarrier(SearchOptions.enableRichCallback(enableRichCallback));
  }

  /**
   * Adds the units option to the URL query.
   *
   * @see net.ygbstudio.jbrave.api.options.Units
   * @param units The units to set.
   * @return the current instance of the builder
   */
  public BraveWebQuery units(@NotNull Units units) {
    return addOptionCarrier(units.toSearchOption());
  }

  /**
   * Adds a freshness option to the URL query using an existing {@link Freshness} instance.
   *
   * @see net.ygbstudio.jbrave.api.filters.Freshness
   * @param freshness a {@link Freshness} describing the freshness constraint
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery freshness(@NotNull Freshness freshness) {
    return addOptionCarrier(freshness.toSearchOption());
  }

  /**
   * Adds a freshness option to the URL query representing a date range.
   *
   * @see net.ygbstudio.jbrave.api.filters.Freshness
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
   * @see net.ygbstudio.jbrave.api.filters.SafeSearch
   * @param safeSearch a {@link SafeSearch} enum value specifying the safe search level
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery safeSearch(@NotNull SafeSearch safeSearch) {
    return addOptionCarrier(safeSearch.toSearchOption());
  }

  /**
   * Adds result filters to the URL query.
   *
   * @see net.ygbstudio.jbrave.api.filters.ResultFilter
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
   * Adds the include_fetch_metadata option to the URL query.
   *
   * @param includeFetchMetadata whether to include fetch metadata in the results
   * @return the current instance of the builder
   */
  public BraveWebQuery includeFetchMetadata(boolean includeFetchMetadata) {
    return addOptionCarrier(SearchOptions.includeFetchMetadata(includeFetchMetadata));
  }

  /**
   * Sets the subscription token header using the provided {@link ClientInfo} instance.
   *
   * @param clientInfo the {@link ClientInfo} instance containing the subscription token
   * @return the current instance of {@link BraveWebQuery}
   */
  @Contract("_ -> this")
  public BraveWebQuery withToken(@NotNull ClientInfo clientInfo) {
    if (controller == null) controller = clientInfo.requestGate();
    requestBuilder.withToken(clientInfo.subscriptionToken());
    return this;
  }

  /**
   * Sets the request headers using the provided consumer.
   *
   * @param headers a consumer that receives a {@link WebHeaderBuilder} and applies the request's
   *     headers to it
   * @return the current instance of {@link BraveWebQuery}
   */
  @Contract("_ -> this")
  public BraveWebQuery withHeaders(@NotNull Consumer<WebHeaderBuilder<BraveWebRequest>> headers) {
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
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery withRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  public BraveWebQuery execute() {
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
  public Class<WebSearchApiResponse> getResponseType() {
    return WebSearchApiResponse.class;
  }

  /**
   * Clears the current instance of {@link BraveWebQuery} by resetting its state to its initial
   * values.
   *
   * @return the current instance of {@link BraveWebQuery}
   */
  public BraveWebQuery reset() {
    currentResponse = null;
    maxRetries = 0;
    requestBuilder.clear();
    return super.clear();
  }
}
