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
import net.ygbstudio.jbrave.core.builders.AbstractBraveRequestBuilder;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.core.domain.SearchHeader;
import net.ygbstudio.jbrave.api.response.SpellCheckSearchApiResponse;
import net.ygbstudio.jbrave.core.domain.provided.CountryIdentifier;
import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.domain.verticals.BraveResource;
import net.ygbstudio.jbrave.core.executors.BraveRequestExecutor;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A builder class for building Brave Spell Check API queries.
 *
 * <p>This class is not intended to be instantiated directly, instead use the {@link #builder()}
 * method to create a new instance of the builder.
 *
 * <p>The builder is not thread-safe and not intended to be instantiated directly, instead use the
 * {@link #builder()} method to create a new instance of the builder. Also note that this builder is
 * a stateful, reusable builder intended for single-threaded use.
 *
 * <p>Method {@link #reset()} will clear the internal state of the builder and must be called
 * before reusing.
 */
public final class BraveSpellcheckQuery extends AbstractQueryUrlBuilder<BraveSpellcheckQuery>
    implements BraveQueryBuilder<BraveSpellcheckQuery, SpellCheckSearchApiResponse> {

  public static final class BraveRequestBuilder
      extends AbstractBraveRequestBuilder<BraveSpellcheckQuery.BraveRequestBuilder> {

    private BraveRequestBuilder() {}

    /**
     * Creates a new instance of {@link BraveSpellcheckQuery.BraveRequestBuilder}.
     *
     * @return a new instance of {@link BraveSpellcheckQuery.BraveRequestBuilder}
     */
    private static BraveSpellcheckQuery.BraveRequestBuilder builder() {
      return new BraveSpellcheckQuery.BraveRequestBuilder().clear();
    }

    /**
     * Sets the URI for the request.
     *
     * @param query the URI for the request
     */
    private BraveSpellcheckQuery.BraveRequestBuilder queryAddress(URI query) {
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
     * @return the current instance of {@link BraveSpellcheckQuery.BraveRequestBuilder}
     */
    public BraveSpellcheckQuery.BraveRequestBuilder withUserAgent(String userAgent) {
      addCustomHeader(BraveHeaders.USER_AGENT, userAgent);
      return this;
    }

    /**
     * Adds a cache control header to the request.
     *
     * @param cacheControl the cache control value to set
     * @return the current instance of {@link BraveSpellcheckQuery.BraveRequestBuilder}
     */
    public BraveSpellcheckQuery.BraveRequestBuilder withCacheControl(String cacheControl) {
      addCustomHeader(BraveHeaders.CACHE_CONTROL, cacheControl);
      return this;
    }

    /**
     * Adds an API version header to the request.
     *
     * @param apiVersion the API version value to set
     * @return the current instance of {@link BraveSpellcheckQuery.BraveRequestBuilder}
     */
    public BraveSpellcheckQuery.BraveRequestBuilder withApiVersion(String apiVersion) {
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

  private final BraveSpellcheckQuery.BraveRequestBuilder requestBuilder =
      BraveSpellcheckQuery.BraveRequestBuilder.builder();
  private final BraveRequestExecutor executor = BraveRequestExecutor.getInstance();
  private HttpResponse<String> currentResponse;
  private int maxRetries = 0;

  private BraveSpellcheckQuery() {}

  public static BraveSpellcheckQuery builder() {
    return new BraveSpellcheckQuery().addInstanceVertical(BraveResource.SPELLCHECK).clear();
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
  public BraveSpellcheckQuery query(String queryTerm) {
    return addQueryTerm(queryTerm, false);
  }

  /**
   * Adds the language option to the URL query.
   *
   * @see SearchLanguage
   * @param language The language identifier to set.
   * @return The current instance of the builder.
   */
  public <T extends LanguageIdentifier> BraveSpellcheckQuery language(@NotNull T language) {
    return addOptionCarrier(language.toSearchOption());
  }

  /**
   * Adds the country option to the URL query.
   *
   * @see Country
   * @param country The country identifier to set.
   * @return The current instance of the builder.
   */
  public <T extends CountryIdentifier> BraveSpellcheckQuery country(@NotNull T country) {
    return addOptionCarrier(country.toSearchOption());
  }

  /**
   * Sets the subscription token header using the provided {@link ClientInfo} instance.
   *
   * @param clientInfo the {@link ClientInfo} instance containing the subscription token
   * @return the current instance of {@link BraveSpellcheckQuery}
   */
  @Contract("_ -> this")
  public BraveSpellcheckQuery withToken(@NotNull ClientInfo clientInfo) {
    requestBuilder.addCustomHeader(BraveHeaders.SUBSCRIPTION_TOKEN, clientInfo.subscriptionToken());
    return this;
  }

  /**
   * Sets the request headers using the provided consumer.
   *
   * @param headers a consumer that accepts an inner request builder instance and applies
   *     preconfigured headers to it via helper methods.
   * @return the current instance of {@link BraveSpellcheckQuery}
   */
  @Contract("_ -> this")
  public BraveSpellcheckQuery withHeaders(
      @NotNull Consumer<BraveSpellcheckQuery.BraveRequestBuilder> headers) {
    headers.accept(requestBuilder);
    return this;
  }

  /**
   * Sets the maximum number of retries for this request.
   *
   * @param maxRetries the maximum number of retries
   * @return the current instance of {@link BraveSpellcheckQuery}
   */
  public BraveSpellcheckQuery withRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  /**
   * Executes the request and returns response of string.
   *
   * @return an optional response to the request
   */
  public BraveSpellcheckQuery execute() {
    Supplier<HttpRequest> suppliedTask = () -> requestBuilder.queryAddress(toURI()).buildRequest();
    currentResponse =
        maxRetries > 0
            ? executor.submitTask(suppliedTask).executeWithRetries(maxRetries).getFirst()
            : executor.executeStringResponseOnce(suppliedTask);
    return this;
  }

  @Override
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
  public Class<SpellCheckSearchApiResponse> getResponseType() {
    return SpellCheckSearchApiResponse.class;
  }

  /**
   * Clears the current instance of {@link BraveSpellcheckQuery} by resetting its state to its
   * initial values.
   *
   * @return the current instance of {@link BraveSpellcheckQuery}
   */
  public BraveSpellcheckQuery reset() {
    currentResponse = null;
    maxRetries = 0;
    requestBuilder.clearBuilder();
    return super.clear();
  }
}
