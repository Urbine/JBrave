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

package net.ygbstudio.jbrave.core.domain.dto.rate;

import java.net.http.HttpResponse;
import java.util.function.Function;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import org.jetbrains.annotations.NotNull;

/**
 * Indicates how many requests you can still make within each time window before hitting the limit.
 *
 * @param requestAvailable Request available in the current second
 * @param monthlyRequestsRemaining Requests remaining in the current month
 */
public record XRateLimitRemaining(int requestAvailable, int monthlyRequestsRemaining) {

  /**
   * Extracts the rate limit remaining from the HTTP Response.
   *
   * <p>Check {@code X-RateLimit-Remaining} before making subsequent requests. This helps you avoid
   * hitting limits unexpectedly.
   *
   * @param httpResponse the HTTP Response object containing the rate limit remaining information
   * @param <T> the type of the response body
   * @return the rate limit remaining extracted from the httpResponse
   */
  public static <T> XRateLimitRemaining from(@NotNull HttpResponse<T> httpResponse) {
    Function<String[], XRateLimitRemaining> toXRateLimitRemaining =
        strArr -> new XRateLimitRemaining(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1]));
    return BraveHeaders.X_RATE_LIMIT_REMAINING.extract(
        httpResponse.headers(), s -> toXRateLimitRemaining.apply(s.split(",")));
  }
}
