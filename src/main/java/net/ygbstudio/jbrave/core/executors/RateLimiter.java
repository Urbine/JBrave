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

package net.ygbstudio.jbrave.core.executors;

import java.util.concurrent.TimeUnit;
import net.ygbstudio.jbrave.api.options.BravePlan;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a rate limiter that controls the rate at which tasks can be executed.
 *
 * @param delay the delay between consecutive tasks in the specified time unit
 * @param timeUnit the time unit of the delay parameter
 */
public record RateLimiter(long delay, TimeUnit timeUnit) {

  /**
   * Creates a {@link RateLimiter} based on the given {@link BravePlan}.
   *
   * @param plan the subscription plan that determines the rate limit for the tasks
   * @return a {@link RateLimiter} with the corresponding delay and time unit
   */
  public static @NotNull RateLimiter of(@NotNull BravePlan plan) {
    return switch (plan) {
      case FREE -> new RateLimiter(1, TimeUnit.SECONDS);
      case BASE -> new RateLimiter(50, TimeUnit.MILLISECONDS);
      case PRO -> new RateLimiter(20, TimeUnit.MILLISECONDS);
    };
  }
}
