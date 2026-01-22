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

package net.ygbstudio.jbrave.api.response;

import java.net.http.HttpResponse;
import net.ygbstudio.jbrave.api.rate.XRateLimit;
import net.ygbstudio.jbrave.api.rate.XRateLimitPolicy;
import net.ygbstudio.jbrave.api.rate.XRateLimitRemaining;
import net.ygbstudio.jbrave.api.rate.XRateLimitReset;
import net.ygbstudio.jbrave.core.utils.JsonSupport;
import org.jetbrains.annotations.NotNull;

/**
 * Constructs a {@code RateLimitResponse} with the given rate limit information.
 *
 * <p>This is a carrier object that extracts and encapsulates all headers related to rate limits in
 * the Brave Search API. If you only need one of these, each header can be extracted individually by
 * using its corresponding factory method {@code from()}.
 *
 * @see XRateLimit
 * @see XRateLimitPolicy
 * @see XRateLimitRemaining
 * @see XRateLimitReset
 * @param xRateLimit the rate limit for the current time window
 * @param xRateLimitPolicy the complete policy specification including time window sizes
 * @param xRateRemaining the number of remaining requests in the current time window
 * @param xRateLimitReset the time until the current quota window resets
 */
public record RateLimitResponse(
    XRateLimit xRateLimit,
    XRateLimitPolicy xRateLimitPolicy,
    XRateLimitRemaining xRateRemaining,
    XRateLimitReset xRateLimitReset) {

  /**
   * Creates a RateLimitResponse object from the headers of an {@link HttpResponse}.
   *
   * @param httpResponse the HttpResponse object to extract rate limit information from
   * @param <T> the type of the response body
   * @return a RateLimitResponse object with the rate limit information extracted from the headers
   */
  public static <T> @NotNull RateLimitResponse from(@NotNull HttpResponse<T> httpResponse) {
    return new RateLimitResponse(
        XRateLimit.from(httpResponse),
        XRateLimitPolicy.from(httpResponse),
        XRateLimitRemaining.from(httpResponse),
        XRateLimitReset.from(httpResponse));
  }

  public String toJson() {
    return JsonSupport.toJsonString(this);
  }
}
