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
 * Sealed response model returned by the Brave Search API.
 *
 * <p>The root {@link net.ygbstudio.jbrave.api.response.ApiResponse} interface permits exactly one
 * implementation per search vertical (web, image, news, video, suggest, spellcheck) plus an {@link
 * net.ygbstudio.jbrave.api.response.ErrorResponse} for API errors. Implementations provide JSON
 * (de)serialization helpers so responses can be read from, and written to, strings, files, readers,
 * and HTTP response bodies.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.api.response;
