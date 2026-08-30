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
 * Enums representing Brave Search API options whose values are selected from fixed, API-defined
 * vocabularies.
 *
 * <p>This package exposes the enumerations a caller uses to pick a country, search language, market
 * locale, or measurement system. Each enum implements one of the identifier interfaces in {@link
 * net.ygbstudio.jbrave.core.domain.provided} and converts itself into a typed {@link
 * net.ygbstudio.jbrave.core.model.SearchOptionCarrier} for URL construction.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.api.options;
