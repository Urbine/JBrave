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
 * Represents a music recording (e.g., a song or track) in search results. This is used in the Brave
 * Search API to provide structured data about music recordings that match the user's query.
 *
 * @param name The title of the music recording.
 * @param thumbnail A thumbnail image representing the music recording or album.
 * @param rating The rating information for the music recording.
 */
public record MusicRecording(String name, Thumbnail thumbnail, Rating rating) {}
