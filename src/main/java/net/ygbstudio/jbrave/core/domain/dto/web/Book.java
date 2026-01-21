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
import net.ygbstudio.jbrave.core.domain.dto.result.thing.Person;

/**
 * Represents a book in search results, containing detailed information about the book such as its
 * title, authors, publication details, and ratings. This is used in the Brave Search API to provide
 * structured data about books that are relevant to the search query.
 *
 * @param title The title of the book.
 * @param author List of authors who wrote the book.
 * @param date The publication date of the book.
 * @param price The price information of the book.
 * @param pages The number of pages in the book.
 * @param publisher The publisher of the book.
 * @param rating The rating information for the book.
 */
public record Book(
    String title,
    List<Person> author,
    String date,
    Price price,
    Integer pages,
    Person publisher,
    Rating rating) {}
