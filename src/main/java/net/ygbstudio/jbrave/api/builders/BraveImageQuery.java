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
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.core.builders.AbstractBraveRequestBuilder;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.core.domain.SearchHeader;
import net.ygbstudio.jbrave.core.domain.dto.response.ImageSearchApiResponse;
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
 * A builder for constructing Brave Image Search API queries.
 *
 * <p>This class is specifically designed for constructing Brave Image Search API queries, and is
 * not intended to be instantiated directly. Instead, use the {@link #builder()} method to create a
 * new instance of the builder.
 *
 * <p>The builder is immutable, reusable and type-safe.
 */
public final class BraveImageQuery extends AbstractQueryUrlBuilder<BraveImageQuery>
    implements BraveQueryBuilder<BraveImageQuery, ImageSearchApiResponse> {

  public static final class BraveRequestBuilder
      extends AbstractBraveRequestBuilder<BraveImageQuery.BraveRequestBuilder> {

    private BraveRequestBuilder() {}

    /**
     * Creates a new instance of {@link BraveImageQuery.BraveRequestBuilder}.
     *
     * @return a new instance of {@link BraveImageQuery.BraveRequestBuilder}
     */
    private static BraveImageQuery.BraveRequestBuilder builder() {
      return new BraveImageQuery.BraveRequestBuilder().clear();
    }

    /**
     * Sets the URI for the request.
     *
     * @param query the URI for the request
     */
    private BraveImageQuery.BraveRequestBuilder queryAddress(URI query) {
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
    public BraveImageQuery.BraveRequestBuilder withUserAgent(String userAgent) {
      addCustomHeader(BraveHeaders.USER_AGENT, userAgent);
      return this;
    }

    /**
     * Adds a cache control header to the request.
     *
     * @param cacheControl the cache control value to set
     * @return the current instance of {@link BraveWebQuery}
     */
    public BraveImageQuery.BraveRequestBuilder withCacheControl(String cacheControl) {
      addCustomHeader(BraveHeaders.CACHE_CONTROL, cacheControl);
      return this;
    }

    /**
     * Adds an API version header to the request.
     *
     * @param apiVersion the API version value to set
     * @return the current instance of {@link BraveWebQuery}
     */
    public BraveImageQuery.BraveRequestBuilder withApiVersion(String apiVersion) {
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

  private final BraveImageQuery.BraveRequestBuilder requestBuilder =
      BraveImageQuery.BraveRequestBuilder.builder();
  private final BraveRequestExecutor executor = BraveRequestExecutor.getInstance();
  private HttpResponse<String> currentResponse;
  private int maxRetries = 0;

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
    return addQueryTerm(queryTerm, false);
  }

  /**
   * Adds the search language option to the URL query.
   *
   * @see SearchLanguage
   * @param searchLanguage the search language to set
   * @return the current instance of the builder
   */
  public <T extends LanguageIdentifier> BraveImageQuery language(@NotNull T searchLanguage) {
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
   * @see Country
   * @param country the country identifier to set
   * @return the current instance of the builder
   */
  public <T extends CountryIdentifier> BraveImageQuery country(@NotNull T country) {
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

  /**
   * Sets the subscription token header using the provided {@link ClientInfo} instance.
   *
   * @param clientInfo the {@link ClientInfo} instance containing the subscription token
   * @return the current instance of {@link BraveImageQuery}
   */
  @Contract("_ -> this")
  public BraveImageQuery withToken(@NotNull ClientInfo clientInfo) {
    requestBuilder.addCustomHeader(BraveHeaders.SUBSCRIPTION_TOKEN, clientInfo.subscriptionToken());
    return this;
  }

  /**
   * Sets the request headers using the provided consumer.
   *
   * @param headers a consumer that accepts an inner request builder instance and applies
   *     preconfigured headers to it via helper methods.
   * @return the current instance of {@link BraveImageQuery}
   */
  @Contract("_ -> this")
  public BraveImageQuery withHeaders(
      @NotNull Consumer<BraveImageQuery.BraveRequestBuilder> headers) {
    headers.accept(requestBuilder);
    return this;
  }

  /**
   * Sets the maximum number of retries for this request.
   *
   * @param maxRetries the maximum number of retries
   * @return the current instance of {@link BraveImageQuery}
   */
  public BraveImageQuery withRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  /**
   * Executes the request and returns response of string.
   *
   * @return an optional response to the request
   */
  public BraveImageQuery execute() {
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

  @Override
  public Class<ImageSearchApiResponse> getReponseType() {
    return ImageSearchApiResponse.class;
  }

  /**
   * Returns the current HTTP response as an {@link Optional}.
   *
   * <p>If the request has not been executed, this method will execute the request and return the
   * {@link HttpResponse}.
   *
   * @return an {@link Optional} containing the current HTTP response.
   */
  @Contract(pure = true)
  public @NotNull Optional<HttpResponse<String>> getHttpResponse() {
    return Objects.nonNull(currentResponse)
        ? Optional.of(currentResponse)
        : execute().getHttpResponse();
  }

  /**
   * Clears the current instance of {@link BraveImageQuery} by resetting its state to its initial
   * values.
   *
   * @return the current instance of {@link BraveImageQuery}
   */
  public BraveImageQuery reset() {
    currentResponse = null;
    maxRetries = 0;
    requestBuilder.clearBuilder();
    return super.clear();
  }
}
