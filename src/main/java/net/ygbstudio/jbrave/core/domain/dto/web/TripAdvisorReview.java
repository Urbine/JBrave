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

import net.ygbstudio.jbrave.core.domain.dto.result.thing.Person;

/**
 * Represents a review from TripAdvisor in search results. This is used in the Brave Search API to
 * provide structured data about reviews from TripAdvisor.
 *
 * @param title The title of the review.
 * @param description The full text content of the review.
 * @param date The date when the review was posted.
 * @param rating The rating given in the review.
 * @param author The person who wrote the review.
 * @param reviewUrl The URL of the full review on TripAdvisor.
 * @param language The language the review is written in.
 */
public record TripAdvisorReview(
    String title,
    String description,
    String date,
    Rating rating,
    Person author,
    String reviewUrl,
    String language) {}
