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

package net.ygbstudio.jbrave.core.domain.dto.result;

/**
 * Represents a suggestion result in search responses. This class provides structured data about
 * search suggestions in the Brave Search API results.
 *
 * @param query The suggested query string.
 * @param isEntity Indicates if the suggestion is an entity.
 * @param title The title of the suggestion.
 * @param description A brief description of the suggestion.
 * @param img The URL of an image associated with the suggestion.
 */
public record SuggestResult(
    String query, Boolean isEntity, String title, String description, String img) {}
