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

/**
 * Exception thrown when a search query is absent in a URL query builder method.
 *
 * <p>Queries cannot be empty and a request without it is not acceptable by the API.
 *
 * @see net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder
 */
public class AbsentSearchQueryException extends RuntimeException {
  public AbsentSearchQueryException(String message) {
    super(message);
  }
}
