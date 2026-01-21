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

import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;

/**
 * Represents a creative work in search results, which is a broad category that includes various
 * types of content such as articles, books, movies, music, and more. This is a base class for more
 * specific types of creative works in the Brave Search API.
 *
 * @param name The name or title of the creative work.
 * @param thumbnail A thumbnail image representing the creative work.
 * @param rating The rating information for the creative work.
 */
public record CreativeWork(String name, Thumbnail thumbnail, Rating rating) {}
