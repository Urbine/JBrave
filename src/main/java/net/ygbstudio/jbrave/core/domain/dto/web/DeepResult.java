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
import net.ygbstudio.jbrave.core.domain.dto.result.NewsResult;
import net.ygbstudio.jbrave.core.domain.dto.result.VideoResult;

/**
 * Represents a comprehensive set of information about a search result in the Brave Search API. This
 * includes related news articles, action buttons, videos, and images that provide additional
 * context and functionality for the search result.
 *
 * @param news List of news articles related to the search result.
 * @param buttons List of action buttons providing quick actions for the search result.
 * @param videos List of videos related to the search result.
 * @param images List of images associated with the search result.
 */
public record DeepResult(
    List<NewsResult> news,
    List<ButtonResult> buttons,
    List<VideoResult> videos,
    List<Image> images) {}
