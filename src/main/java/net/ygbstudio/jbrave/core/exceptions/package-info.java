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

/**
 * Runtime exceptions raised by JBrave for API, client, and builder lifecycle errors.
 *
 * <p>Exceptions in this package fall into two families: {@link
 * net.ygbstudio.jbrave.core.exceptions.BraveApiException} for errors the Brave Search API reports
 * and expects the caller to handle, and {@link
 * net.ygbstudio.jbrave.core.exceptions.BraveClientException} for errors originating in JBrave
 * itself (missing tokens, malformed queries, interruption during rate-limit backoff, and so on).
 * All are unchecked.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.core.exceptions;
