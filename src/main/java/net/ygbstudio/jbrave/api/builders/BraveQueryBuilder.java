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
import java.util.Optional;
import net.ygbstudio.jbrave.core.domain.dto.rate.XRateLimit;
import net.ygbstudio.jbrave.core.domain.dto.rate.XRateLimitPolicy;
import net.ygbstudio.jbrave.core.domain.dto.rate.XRateLimitRemaining;
import net.ygbstudio.jbrave.api.response.ApiResponse;
import net.ygbstudio.jbrave.api.response.ErrorResponse;
import net.ygbstudio.jbrave.api.response.WebSearchApiResponse;
import net.ygbstudio.jbrave.core.utils.JsonSupport;

/**
 * An interface for Brave API query builders. The BraveQueryBuilder interface is implemented by all
 * builders that build Brave API queries. It is parameterized by the type of the builder.
 *
 * @param <T> the type of the builder
 * @param <E> the type of the API response
 */
public sealed interface BraveQueryBuilder<T, E extends ApiResponse>
    permits BraveNewsQuery,
        BraveWebQuery,
        BraveImageQuery,
        BraveVideoQuery,
        BraveSuggestQuery,
        BraveSpellcheckQuery {

  /**
   * Returns the rate limit from the HTTP response.
   *
   * @return An optional containing the rate limit extracted from the HTTP response.
   */
  default Optional<XRateLimit> getRateLimits() {
    return getHttpResponse().map(XRateLimit::from);
  }

  /**
   * Returns the rate limit policy from the HTTP response.
   *
   * @return An optional containing the rate limit policy extracted from the HTTP response.
   */
  default Optional<XRateLimitPolicy> getRateLimitPolicy() {
    return getHttpResponse().map(XRateLimitPolicy::from);
  }

  /**
   * Returns the rate limit remaining from the HTTP response.
   *
   * @return An optional containing the rate limit remaining extracted from the HTTP response.
   */
  default Optional<XRateLimitRemaining> getRateLimitRemaining() {
    return getHttpResponse().map(XRateLimitRemaining::from);
  }

  /**
   * Executes the request and returns response of string.
   *
   * <p>This method does not return the response, and it is typically used to decouple the execution
   * and extraction steps in your application.
   *
   * <p>You can extract the responses with the following methods:
   * <li>{@link #getHttpResponse()}
   * <li>{@link #getPOJO()}
   * <li>{@link #getErrorPOJO()} <br>
   *
   *     <p><strong>Note:</strong> This builder stores the response object internally and repeated
   *     calls to <strong>extraction</strong> methods will not trigger multiple requests; however,
   *     {@code execute()} will send a new request per method call.
   *
   * @implNote If the client added retries to the building chain, {@code execute()} will activate
   *     retry logic under the hood.
   * @return instance of a builder type {@code T}
   */
  T execute();

  /**
   * Converts the current query to an {@link HttpRequest} instance.
   *
   * @return the {@link HttpRequest} instance representing the current query
   */
  HttpRequest toHttpRequest();

  /**
   * Returns the type of the API response. The class returned by this method is the class of the
   * concrete implementation of {@link ApiResponse} that this builder is expected to produce as a
   * result of its query.
   *
   * @return the class of the API response
   */
  Class<E> getResponseType();

  /**
   * Converts the current response to a POJO instance of {@link E} if the status code of the
   * response is 200.
   *
   * <p>If you get an empty optional with this method, it is possible that the request failed with
   * an error code. Use the {@link #getErrorPOJO()} method to get the error response object and use
   * it in any error handling strategy. If you want to inspect the raw {@link HttpResponse} object,
   * you can always use {@link #getHttpResponse()}.
   *
   * <p><strong>Note:</strong> Calling this method will execute the request and deserialize it. Once
   * a request has been executed, this builder stores it in an internal field, so you can call this
   * method and the ones mentioned in the last paragraph without having to send another request to
   * the API.
   *
   * @return An {@link Optional} containing the current response as a POJO instance of <br>
   *     {@link E}, or an empty {@link Optional} if the response status code is not 200.
   */
  default Optional<E> getPOJO() {
    return getHttpResponse()
        .map(
            res ->
                res.statusCode() == 200
                    ? JsonSupport.objectFromJson(res.body(), getResponseType())
                    : null);
  }

  /**
   * Converts the current response to a POJO instance of {@link ErrorResponse} if the status code of
   * the response is not 200.
   *
   * <p>If you get an empty optional with this method, it is possible that the request was
   * successful. Use the {@link #getPOJO()} method to get the response object and use it in any
   * success handling strategy. If you want to inspect the raw {@link HttpResponse} object, you can
   * always use <br>
   * {@link #getHttpResponse()}.
   *
   * @implNote Calling this method will execute the request and deserialize it. Once a request has
   *     been executed, this builder stores it in an internal field, so you can call this method and
   *     the ones mentioned in the last paragraph without having to send another request to the API.
   * @return An {@link Optional} containing the current response as a POJO instance of <br>
   *     {@link ErrorResponse}, or an empty {@link Optional} if the response status code is 200.
   */
  default Optional<ErrorResponse> getErrorPOJO() {
    return getHttpResponse()
        .map(
            res ->
                res.statusCode() != 200
                    ? JsonSupport.objectFromJson(res.body(), ErrorResponse.class)
                    : null);
  }

  /**
   * Converts the current response to a POJO instance of {@link WebSearchApiResponse} if the status
   * code of the response is 200. If the status code is not 200, it attempts to deserialize the
   * response body into an {@link ErrorResponse} object. This is particularly useful if you want to
   * check the success of your request with an {@code instanceof} check and then handle it as you
   * like depending on what {@link ApiResponse} implementation you get.
   *
   * @implNote Calling this method will execute the request and deserialize it. Once a request has
   *     been executed, this builder stores it in an internal field, so you can call this method and
   *     the ones mentioned in the last paragraph without having to send another request to the API.
   * @return An {@link Optional} containing the current response as a POJO instance of either <br>
   *     {@link WebSearchApiResponse} or {@link ErrorResponse}, or an empty {@link Optional} if the
   *     response status code is neither 200 nor any other expected error code.
   */
  default Optional<ApiResponse> getEitherPOJO() {
    return getHttpResponse()
        .map(
            res ->
                res.statusCode() == 200
                    ? JsonSupport.objectFromJson(res.body(), getResponseType())
                    : JsonSupport.objectFromJson(res.body(), ErrorResponse.class));
  }

  /**
   * Returns the current HTTP response as an {@link Optional}.
   *
   * <p>If the request has not been executed, this method will execute the request and return the
   * {@link HttpResponse}.
   *
   * @return an {@link Optional} containing the current HTTP response.
   */
  Optional<HttpResponse<String>> getHttpResponse();

  /**
   * Converts the URL query to a URI.
   *
   * @return The URI representation of the URL query.
   */
  URI toURI();

  /**
   * Clears the builder by resetting it to its initial state.
   *
   * <p>This method is used to reset the builder to its initial state before adding any options.
   *
   * @return The current instance of the builder.
   */
  T reset();
}
