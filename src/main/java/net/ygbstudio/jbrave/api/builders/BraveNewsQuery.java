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
import java.util.function.Supplier;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.MarketLocale;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.core.builders.AbstractBraveRequestBuilder;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.core.builders.SearchOperatorBuilder;
import net.ygbstudio.jbrave.core.domain.SearchHeader;
import net.ygbstudio.jbrave.api.response.NewsSearchApiResponse;
import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.domain.provided.RegionLocaleIdentifier;
import net.ygbstudio.jbrave.core.domain.verticals.BraveResource;
import net.ygbstudio.jbrave.core.executors.BraveRequestExecutor;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import net.ygbstudio.jbrave.core.model.SearchOptions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link BraveNewsQuery} class provides a builder for building Brave News API queries.
 *
 * <p>This class is not intended to be instantiated directly, instead use the {@link #builder()}
 * method to create a new instance of the builder.
 *
 * <p>The builder is not thread-safe and not intended to be instantiated directly, instead use the
 * {@link #builder()} method to create a new instance of the builder. Also note that this builder is
 * stateful, reusable builder intended for single-threaded use.
 *
 * <p>Method {@link #reset()} will clear the internal state of the builder and must be called
 * before reusing.
 */
public final class BraveNewsQuery extends AbstractQueryUrlBuilder<BraveNewsQuery>
    implements BraveQueryBuilder<BraveNewsQuery, NewsSearchApiResponse> {

  public static final class BraveRequestBuilder
      extends AbstractBraveRequestBuilder<BraveNewsQuery.BraveRequestBuilder> {

    private BraveRequestBuilder() {}

    /**
     * Creates a new instance of {@link BraveNewsQuery.BraveRequestBuilder}.
     *
     * @return a new instance of {@link BraveNewsQuery.BraveRequestBuilder}
     */
    private static BraveNewsQuery.BraveRequestBuilder builder() {
      return new BraveNewsQuery.BraveRequestBuilder().clear();
    }

    /**
     * Sets the URI for the request.
     *
     * @param query the URI for the request
     */
    private BraveNewsQuery.BraveRequestBuilder queryAddress(URI query) {
      queryURI(query);
      return this;
    }

    /**
     * Adds a custom header to the client.
     *
     * @param searchHeader the header to be added
     * @param headerValue the value of the header
     */
    private <K extends SearchHeader> void addCustomHeader(K searchHeader, String headerValue) {
      addHeader(searchHeader, headerValue);
    }

    /**
     * Adds a user agent header to the request.
     *
     * @param userAgent the user agent value to set
     * @return the current instance of {@link BraveWebQuery}
     */
    public BraveNewsQuery.BraveRequestBuilder withUserAgent(String userAgent) {
      addCustomHeader(BraveHeaders.USER_AGENT, userAgent);
      return this;
    }

    /**
     * Adds a cache control header to the request.
     *
     * @param cacheControl the cache control value to set
     * @return the current instance of {@link BraveWebQuery}
     */
    public BraveNewsQuery.BraveRequestBuilder withCacheControl(String cacheControl) {
      addCustomHeader(BraveHeaders.CACHE_CONTROL, cacheControl);
      return this;
    }

    /**
     * Adds an API version header to the request.
     *
     * @param apiVersion the API version value to set
     * @return the current instance of {@link BraveWebQuery}
     */
    public BraveNewsQuery.BraveRequestBuilder withApiVersion(String apiVersion) {
      addCustomHeader(BraveHeaders.API_VERSION, apiVersion);
      return this;
    }

    /**
     * Builds the request using the current state of the builder.
     *
     * @return the built {@link HttpRequest}
     */
    private HttpRequest buildRequest() {
      return super.build();
    }

    /** Clears the request builder, resetting it to its initial state. */
    private void clearBuilder() {
      super.clear();
    }
  }

  private final BraveNewsQuery.BraveRequestBuilder requestBuilder =
      BraveNewsQuery.BraveRequestBuilder.builder();
  private final BraveRequestExecutor executor = BraveRequestExecutor.getInstance();
  private HttpResponse<String> currentResponse;
  private int maxRetries = 0;

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
   * @param includeFetchMetadata Whether to include fetch metadata in the results.
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
   * @param enableOperators Whether to include operators in the results.
   * @return The current instance of the builder.
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
   * @param operators A consumer that accepts a {@link SearchOperatorBuilder} instance and populates
   *     it with operators. The built {@code SearchOperatorBuilder} instance will be used to
   *     construct the operators string.
   * @return The current instance of the builder.
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
    requestBuilder.addCustomHeader(BraveHeaders.SUBSCRIPTION_TOKEN, clientInfo.subscriptionToken());
    return this;
  }

  /**
   * Sets the request headers using the provided consumer.
   *
   * @param headers a consumer that accepts an inner request builder instance and applies
   *     preconfigured headers to it via helper methods.
   * @return the current instance of {@link BraveNewsQuery}
   */
  @Contract("_ -> this")
  public BraveNewsQuery withHeaders(@NotNull Consumer<BraveNewsQuery.BraveRequestBuilder> headers) {
    headers.accept(requestBuilder);
    return this;
  }

  /**
   * Sets the maximum number of retries for this request.
   *
   * @param maxRetries the maximum number of retries
   * @return the current instance of {@link BraveNewsQuery}
   */
  public BraveNewsQuery withRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  /**
   * Executes the request and returns response of string.
   *
   * @return an optional response to the request
   */
  public BraveNewsQuery execute() {
    Supplier<HttpRequest> suppliedTask = () -> requestBuilder.queryAddress(toURI()).buildRequest();
    currentResponse =
        maxRetries > 0
            ? executor.submitTask(suppliedTask).executeWithRetries(maxRetries).getFirst()
            : executor.executeStringResponseOnce(suppliedTask);
    return this;
  }

  /**
   * Converts the current query to an {@link HttpRequest} instance.
   *
   * @return the {@link HttpRequest} instance representing the current query
   */
  public HttpRequest toHttpRequest() {
    return requestBuilder.queryAddress(toURI()).buildRequest();
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
    requestBuilder.clearBuilder();
    return super.clear();
  }
}
