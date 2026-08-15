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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.api.response.ImageSearchApiResponse;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.core.builders.BraveVerticalRequest;
import net.ygbstudio.jbrave.core.builders.BraveVerticalSearchRequest;
import net.ygbstudio.jbrave.core.builders.VerticalHeaderBuilder;
import net.ygbstudio.jbrave.core.domain.provided.CountryIdentifier;
import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.domain.verticals.BraveResource;
import net.ygbstudio.jbrave.core.executors.BraveExecutionGate;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import net.ygbstudio.jbrave.core.model.SearchOptions;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A builder for constructing Brave Image Search API queries.
 *
 * <p>The builder is not thread-safe and is intended for single-threaded use. It is stateful and
 * reusable: call {@link #reset()} to clear its internal state before reusing it.
 *
 * <p>Builders are not intended to be instantiated directly; use the {@link #builder()} factory
 * method to create a new instance.
 */
public final class BraveImageQuery extends AbstractQueryUrlBuilder<BraveImageQuery>
    implements BraveQueryBuilder<BraveImageQuery, ImageSearchApiResponse> {

  private final BraveVerticalSearchRequest requestBuilder = BraveVerticalSearchRequest.builder();
  private BraveExecutionGate controller;
  private HttpResponse<String> currentResponse;
  private int maxRetries = 0;

  private BraveImageQuery() {}

  /**
   * Creates a new instance of {@link BraveImageQuery}.
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
    if (controller == null) controller = clientInfo.requestGate();
    requestBuilder.withToken(clientInfo.subscriptionToken());
    return this;
  }

  /**
   * Sets the request headers using the provided consumer.
   *
   * @param headers a consumer that receives a {@link VerticalHeaderBuilder} and applies the
   *     request's headers to it
   * @return the current instance of {@link BraveImageQuery}
   */
  @Contract("_ -> this")
  public BraveImageQuery withHeaders(
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
   * @return the current instance of {@link BraveImageQuery}
   */
  public BraveImageQuery withRetries(int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  public BraveImageQuery execute() {
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

  @Override
  public Class<ImageSearchApiResponse> getResponseType() {
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
    requestBuilder.clear();
    return super.clear();
  }
}
