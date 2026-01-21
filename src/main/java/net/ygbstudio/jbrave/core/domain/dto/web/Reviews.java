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

import java.util.List;

/**
 * Represents a collection of reviews in search results, typically associated with a business,
 * product, or service. This is used in the Brave Search API to group multiple reviews together,
 * particularly for platforms like TripAdvisor.
 *
 * @param results A list of TripAdvisorReview objects containing individual review data.
 * @param viewMoreUrl A URL where more reviews can be viewed, if available.
 * @param reviewsInForeignLanguage Indicates if some reviews are in a language different from the
 *     primary language of the search results.
 */
public record Reviews(
    List<TripAdvisorReview> results, String viewMoreUrl, Boolean reviewsInForeignLanguage) {}
