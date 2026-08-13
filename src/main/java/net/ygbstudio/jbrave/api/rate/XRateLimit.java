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

package net.ygbstudio.jbrave.api.rate;

import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.function.Function;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import org.jetbrains.annotations.NotNull;

/**
 * Shows the maximum number of requests allowed for each time window in your plan.
 *
 * @param requestsPerSecond Burst rate limit
 * @param requestsPerMonth Monthly quota (0 for unlimited)
 */
public record XRateLimit(int requestsPerSecond, int requestsPerMonth) {

  /**
   * Extracts the rate limit from the HTTP Response.
   *
   * @param httpResponse the HTTP httpResponse containing the rate limit information
   * @param <T> the type of the response body
   * @return an optional of the rate limit extracted from the httpResponse
   */
  public static <T> Optional<XRateLimit> from(@NotNull HttpResponse<T> httpResponse) {
    Function<String[], XRateLimit> toXRateLimit =
        strArr ->
            new XRateLimit(Integer.parseInt(strArr[0].trim()), Integer.parseInt(strArr[1].trim()));
    return BraveHeaders.X_RATE_LIMIT.extract(
        httpResponse.headers(), s -> toXRateLimit.apply(s.split(",")));
  }
}
