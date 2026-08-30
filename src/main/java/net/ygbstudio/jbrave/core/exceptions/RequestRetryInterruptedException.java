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

package net.ygbstudio.jbrave.core.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when all retry attempts for an HTTP request have been exhausted, indicating that the
 * executor was unable to complete a series of retries.
 *
 * @see net.ygbstudio.jbrave.core.executors.AbstractRequestExecutor
 */
public class RequestRetryInterruptedException extends BraveClientException {

  /**
   * Creates a new instance with the given detail message and cause.
   *
   * @param errorMessageSupplier the detail message
   * @param cause the cause of the exception
   */
  public RequestRetryInterruptedException(@NotNull String errorMessageSupplier, Throwable cause) {
    super(errorMessageSupplier, cause);
  }
}
