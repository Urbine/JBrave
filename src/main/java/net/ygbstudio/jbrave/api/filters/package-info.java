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
 * Enums that model the filtering parameters accepted by the Brave Search API.
 *
 * <p>This package groups the result filters, safe-search levels, and freshness windows that narrow
 * or constrain search results. Each enum implements {@link
 * net.ygbstudio.jbrave.core.domain.SearchFilterOption} and knows how to produce its own URL
 * parameter via a {@link net.ygbstudio.jbrave.core.model.SearchOptionCarrier}.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.api.filters;
