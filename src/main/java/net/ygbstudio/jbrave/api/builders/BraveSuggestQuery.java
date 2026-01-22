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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.api.response.SuggestSearchApiResponse;
import net.ygbstudio.jbrave.core.builders.AbstractBraveRequestBuilder;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.core.domain.SearchHeader;
import net.ygbstudio.jbrave.core.domain.provided.CountryIdentifier;
import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.domain.verticals.BraveResource;
import net.ygbstudio.jbrave.core.executors.BraveRequestExecutor;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import net.ygbstudio.jbrave.core.model.SearchOptions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@link BraveSuggestQuery} class provides a builder for building Brave Suggest API queries.
 *
 * <p>This class is not intended to be instantiated directly, instead use the {@link #builder()}
 * method to create a new instance of the builder.
 *
 * <p>The builder is not thread-safe and not intended to be instantiated directly, instead use the
 * {@link #builder()} method to create a new instance of the builder. Also note that this builder is
 * a stateful, reusable builder intended for single-threaded use.
 *
 * <p>Method {@link #reset()} will clear the internal state of the builder and must be called before
 * reusing.
 */
public final class BraveSuggestQuery extends AbstractQueryUrlBuilder<BraveSuggestQuery>
    implements BraveQueryBuilder<BraveSuggestQuery, SuggestSearchApiResponse> {

  public static final class BraveRequestBuilder
      extends AbstractBraveRequestBuilder<BraveSuggestQuery.BraveRequestBuilder> {

    private BraveRequestBuilder() {}

    /**
     * Creates a new instance of {@link BraveSuggestQuery.BraveRequestBuilder}.
     *
     * @return a new instance of {@link BraveSuggestQuery.BraveRequestBuilder}
     */
    private static BraveSuggestQuery.BraveRequestBuilder builder() {
      return new BraveSuggestQuery.BraveRequestBuilder().clear();
    }

    /**
     * Sets the URI for the request.
     *
     * @param query the URI for the request
     */
    private BraveSuggestQuery.BraveRequestBuilder queryAddress(URI query) {
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
     * @return the current instance of {@link BraveSuggestQuery.BraveRequestBuilder}
     */
    public BraveSuggestQuery.BraveRequestBuilder withUserAgent(String userAgent) {
      addCustomHeader(BraveHeaders.USER_AGENT, userAgent);
      return this;
    }

    /**
     * Adds a cache control header to the request.
     *
     * @param cacheControl the cache control value to set
     * @return the current instance of {@link BraveSuggestQuery.BraveRequestBuilder}
     */
    public BraveSuggestQuery.BraveRequestBuilder withCacheControl(String cacheControl) {
      addCustomHeader(BraveHeaders.CACHE_CONTROL, cacheControl);
      return this;
    }

    /**
     * Adds an API version header to the request.
     *
     * @param apiVersion the API version value to set
     * @return the current instance of {@link BraveSuggestQuery.BraveRequestBuilder}
     */
    public BraveSuggestQuery.BraveRequestBuilder withApiVersion(String apiVersion) {
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

  private final BraveSuggestQuery.BraveRequestBuilder requestBuilder =
      BraveSuggestQuery.BraveRequestBuilder.builder();
  private final BraveRequestExecutor executor = BraveRequestExecutor.getInstance();
  private HttpResponse<String> currentResponse;
  private int maxRetries = 0;

  private BraveSuggestQuery() {}

  /**
   * Creates a new instance of {@link BraveSuggestQuery}
   *
   * @return a new instance of {@link BraveSuggestQuery}
   */
  public static BraveSuggestQuery builder() {
    return new BraveSuggestQuery().addInstanceVertical(BraveResource.SUGGEST).clear();
  }

  /**
   * Adds a search term to the URL query. Maximum of 400 characters and 50 words.
   *
   * <p>If a query is already present, subsequent calls are ignored. Only one query term is
   * supported per search request.
   *
   * @param queryTerm The search term to add.
   * @return The current instance of the builder.
   */
  public BraveSuggestQuery query(String queryTerm) {
    return addQueryTerm(queryTerm, false);
  }

  /**
   * Adds the search language option to the URL query.
   *
   * @see SearchLanguage
   * @param searchLanguage the search language to set
   * @return the current instance of the builder
   */
  public <T extends LanguageIdentifier> BraveSuggestQuery language(@NotNull T searchLanguage) {
    return addOptionCarrier(searchLanguage.toSearchOption());
  }

  /**
   * Adds the country option to the URL query.
   *
   * @see Country
   * @param country The country identifier to set.
   * @return The current instance of the builder.
   */
  public <T extends CountryIdentifier> BraveSuggestQuery country(@NotNull T country) {
    return addOptionCarrier(country.toSearchOption());
  }

  /**
   * Adds the rich option to the URL query.
   *
   * @param rich whether to enable rich results
   * @return the current instance of the builder
   */
  public BraveSuggestQuery rich(boolean rich) {
    return addOptionCarrier(SearchOptions.rich(rich));
  }

  /**
   * Adds the count option to the URL query.
   *
   * @param count The number of results to return.
   * @return The current instance of the builder.
   */
  public BraveSuggestQuery count(int count) {
    return addOptionCarrier(SearchOptions.count(count));
  }

  /**
   * Sets the subscription token header using the provided {@link ClientInfo} instance.
   *
   * @param clientInfo the {@link ClientInfo} instance containing the subscription token
   * @return the current instance of {@link BraveSuggestQuery}
   */
  @Contract("_ -> this")
  public BraveSuggestQuery withToken(@NotNull ClientInfo clientInfo) {
    requestBuilder.addCustomHeader(BraveHeaders.SUBSCRIPTION_TOKEN, clientInfo.subscriptionToken());
    return this;
  }

  /**
   * Sets the request headers using the provided consumer.
   *
   * @param headers a consumer that accepts an inner request builder instance and applies
   *     preconfigured headers to it via helper methods.
   * @return the current instance of {@link BraveSuggestQuery}
   */
  @Contract("_ -> this")
  public BraveSuggestQuery withHeaders(
      @NotNull Consumer<BraveSuggestQuery.BraveRequestBuilder> headers) {
    headers.accept(requestBuilder);
    return this;
  }

  /**
   * Sets the maximum number of retries for this request.
   *
   * @param maxRetries the maximum number of retries
   * @return the current instance of {@link BraveSuggestQuery}
   */
  public BraveSuggestQuery withRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  /**
   * Executes the request and returns response of string.
   *
   * @return an optional response to the request
   */
  public BraveSuggestQuery execute() {
    Supplier<HttpRequest> suppliedTask = () -> requestBuilder.queryAddress(toURI()).buildRequest();
    currentResponse =
        maxRetries > 0
            ? executor.submitTask(suppliedTask).executeWithRetries(maxRetries).getFirst()
            : executor.executeStringResponseOnce(suppliedTask);
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
    return requestBuilder.queryAddress(toURI()).buildRequest();
  }

  /**
   * Returns the current HTTP response as an {@link Optional}.
   *
   * @return an {@link Optional} containing the current HTTP response, or an empty {@link Optional}
   *     if there is no current response.
   */
  public @NotNull Optional<HttpResponse<String>> getHttpResponse() {
    return Objects.nonNull(currentResponse)
        ? Optional.of(currentResponse)
        : execute().getHttpResponse();
  }

  @Override
  public Class<SuggestSearchApiResponse> getResponseType() {
    return SuggestSearchApiResponse.class;
  }

  /**
   * Clears the current instance of {@link BraveSuggestQuery} by resetting its state to its initial
   * values.
   *
   * @return the current instance of {@link BraveSuggestQuery}
   */
  public BraveSuggestQuery reset() {
    currentResponse = null;
    maxRetries = 0;
    requestBuilder.clearBuilder();
    return super.clear();
  }
}
