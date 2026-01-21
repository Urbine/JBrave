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

import net.ygbstudio.jbrave.core.domain.dto.result.profile.Profile;

/**
 * Represents rating information for various entities in search results, such as products,
 * businesses, or services. This is used to provide structured rating data in the Brave Search API
 * responses.
 *
 * @param ratingValue The numerical rating value, typically on a scale defined by bestRating.
 * @param bestRating The highest possible rating in the rating system (e.g., 5.0, 10.0).
 * @param reviewCount The total number of reviews or ratings that contributed to this rating.
 * @param profile Information about the entity being rated, including name and other details.
 * @param isTripadvisor Indicates whether this rating is specifically from TripAdvisor.
 */
public record Rating(
    Double ratingValue,
    Double bestRating,
    Integer reviewCount,
    Profile profile,
    Boolean isTripadvisor) {}
