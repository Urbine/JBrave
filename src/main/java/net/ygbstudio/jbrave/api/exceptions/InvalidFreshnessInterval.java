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

package net.ygbstudio.jbrave.api.exceptions;

import java.util.function.Supplier;
import org.jspecify.annotations.NonNull;

/**
 * Exception thrown when the interval of dates provided for a freshness filter is invalid. (e.g. end
 * date is before start date).
 *
 * @see net.ygbstudio.jbrave.api.filters.Freshness
 */
public class InvalidFreshnessInterval extends RuntimeException {
  public InvalidFreshnessInterval(String message) {
    super(message);
  }

  public InvalidFreshnessInterval(@NonNull Supplier<String> message) {
    super(message.get());
  }
}
