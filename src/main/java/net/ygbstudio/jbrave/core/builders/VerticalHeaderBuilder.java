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

/**
 * Configures the standard headers of a Brave vertical search request.
 *
 * <p>The headers declared here are common to every vertical search request.
 *
 * <p>The subscription token is not configured here; use the query builder's {@code withToken}
 * method instead.
 *
 * @param <T> the type returned by the configuration methods
 */
public interface VerticalHeaderBuilder<T extends VerticalHeaderBuilder<T>> {

  /**
   * Adds a user agent header to the request.
   *
   * @param userAgent the user agent value to set
   * @return the current instance of {@link T}
   */
  T withUserAgent(String userAgent);

  /**
   * Adds a cache control header to the request.
   *
   * @param cacheControl the cache control value to set
   * @return the current instance of {@link T}
   */
  T withCacheControl(String cacheControl);

  /**
   * Adds an API version header to the request.
   *
   * @param apiVersion the API version value to set
   * @return the current instance of {@link T}
   */
  T withApiVersion(String apiVersion);
}
