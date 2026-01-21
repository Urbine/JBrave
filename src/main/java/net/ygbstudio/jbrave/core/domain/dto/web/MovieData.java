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
import net.ygbstudio.jbrave.core.domain.dto.result.thing.Person;

/**
 * Represents detailed information about a movie in search results. This is used in the Brave Search
 * API to provide structured data about movies that match the user's query.
 *
 * @param name The title of the movie.
 * @param description A brief summary or description of the movie's plot.
 * @param url A URL where more information about the movie can be found.
 * @param thumbnail A thumbnail image representing the movie.
 * @param release The release date or year of the movie.
 * @param directors A list of people who directed the movie.
 * @param actors A list of actors who appear in the movie.
 * @param rating The rating information for the movie.
 * @param duration The duration of the movie in a human-readable format.
 * @param genre A list of genres that describe the movie.
 * @param query The original search query that returned this movie result.
 */
public record MovieData(
    String name,
    String description,
    String url,
    Thumbnail thumbnail,
    String release,
    List<Person> directors,
    List<Person> actors,
    Rating rating,
    String duration,
    List<String> genre,
    String query) {}
