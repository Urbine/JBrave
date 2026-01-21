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
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import net.ygbstudio.jbrave.core.domain.dto.result.thing.Organization;
import net.ygbstudio.jbrave.core.domain.dto.result.thing.Person;

/**
 * Represents an article in search results, containing metadata about the article such as authors,
 * publication date, and accessibility information. This is typically used in the context of news
 * articles, blog posts, and other published content within the Brave Search API responses.
 *
 * @param author List of authors who contributed to the article.
 * @param date The publication date of the article.
 * @param publisher The organization that published the article.
 * @param thumbnail A thumbnail image representing the article.
 * @param isAccessibleForFree Indicates whether the article is freely accessible without a
 *     subscription or payment.
 */
public record Article(
    List<Person> author,
    String date,
    Organization publisher,
    Thumbnail thumbnail,
    Boolean isAccessibleForFree) {}
