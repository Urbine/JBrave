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
 * Records that decode the rate-limit HTTP headers returned by the Brave Search API.
 *
 * <p>Each type extracts one of the {@code X-RateLimit-*} response headers ({@code Limit}, {@code
 * Policy}, {@code Remaining}, and {@code Reset}) from a {@link java.net.http.HttpResponse} and
 * exposes it as a structured value. They are used by the execution layer to honor the server's
 * backoff guidance on HTTP 429 responses.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.api.rate;
