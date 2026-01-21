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
 * Shows when each quota window will reset, expressed in seconds from now.
 *
 * <p>When you receive a {@code 429} status code, check the {@code X-RateLimit-Reset} header to
 * determine how long to wait before retrying.
 *
 * @param secondsUntilNextRequest Seconds until you can make another request (per-second limit
 *     resets)
 * @param secondsUntilMonthlyReset Seconds until your monthly quota fully resets
 */
public record XRateLimitReset(int secondsUntilNextRequest, int secondsUntilMonthlyReset) {

  /**
   * Extracts the rate limit reset from the HTTP Response.
   *
   * @param httpResponse the HTTP Response object containing the rate limit reset information
   * @param <T> the type of the response body
   * @return the rate limit reset extracted from the httpResponse
   */
  public static <T> XRateLimitReset from(@NotNull HttpResponse<T> httpResponse) {
    Function<String[], XRateLimitReset> toXRateLimitReset =
        strArr -> new XRateLimitReset(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1]));
    return BraveHeaders.X_RATE_LIMIT_RESET.extract(
        httpResponse.headers(), s -> toXRateLimitReset.apply(s.split(",")));
  }
}
