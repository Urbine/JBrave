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
import java.util.function.Function;
import java.util.regex.Pattern;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import org.jetbrains.annotations.NotNull;

/**
 * Provides the complete policy specification including time window sizes.
 *
 * <p><em>Tip: {@literal According to the official docs, it is recommended that instead of bursting
 * all requests at once, distribute them evenly throughout your time window to maximize throughput
 * and avoid hitting the per-second limit.}
 *
 * <p><em>Regarding time windows: {@literal Your plan may have multiple limit windows (per-second,
 * quota). The docs recommend tracking all to ensure you don’t exceed either limit.}
 *
 * <p>Windows are always expressed in seconds.
 *
 * @param limit Limit of 1 request over a 1-second window
 * @param windowSecond Window size in seconds (typically one second).
 * @param limitPerMonth Limit of 1 request over a one-month window
 * @param windowMonth Window size equivalent to one month in seconds
 */
public record XRateLimitPolicy(int limit, int windowSecond, int limitPerMonth, int windowMonth) {

  /**
   * Extracts the rate limit policy from the HTTP Response.
   *
   * @param httpResponse the HTTP Response object containing the rate limit policy information
   * @param <T> the type of the response body
   * @return the rate limit policy extracted from the httpResponse
   */
  public static <T> XRateLimitPolicy from(@NotNull HttpResponse<T> httpResponse) {
    Function<String[], XRateLimitPolicy> toXRateLimitPolicy =
        arr ->
            new XRateLimitPolicy(
                Integer.parseInt(arr[0]),
                Integer.parseInt(arr[1]),
                Integer.parseInt(arr[2]),
                Integer.parseInt(arr[3]));
    return BraveHeaders.X_RATE_LIMIT_POLICY.extract(
        httpResponse.headers(), s -> toXRateLimitPolicy.apply(Pattern.compile("\\D+").split(s)));
  }
}
