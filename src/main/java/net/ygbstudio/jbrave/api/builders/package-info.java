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
 * Public, fluent builders for constructing, configuring, and executing Brave Search API queries.
 *
 * <p>Each builder targets a single search vertical (web, image, news, video, suggest, or
 * spellcheck) and exposes a type-safe fluent API for assembling query parameters, filters, headers,
 * and the subscription token before {@code execute()}-ing the request and obtaining a typed {@link
 * net.ygbstudio.jbrave.api.response.ApiResponse}.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.api.builders;
