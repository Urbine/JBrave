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

package net.ygbstudio.jbrave.core.domain.dto.web;

/**
 * Represents a reference to a search result or a group of results. This is used in the Brave Search
 * API to reference specific results or result sets in the API response.
 *
 * @param type The type of result being referenced.
 * @param index The index position of the result in the results array.
 * @param all A flag indicating if all results of the specified type are referenced.
 */
public record ResultReference(String type, Integer index, Boolean all) {}
