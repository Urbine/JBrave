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

package net.ygbstudio.jbrave.core.domain.dto.response;

import java.util.List;
import net.ygbstudio.jbrave.core.domain.dto.Extra;
import net.ygbstudio.jbrave.core.domain.dto.image.ImageQuery;
import net.ygbstudio.jbrave.core.domain.dto.image.ImageResult;

/**
 * Represents the response from the Brave Search API for image searches. This class contains the
 * original query, a list of image results, and extra information.
 *
 * @param type The type of the response. This will always be "image_results".
 * @param query The original query submitted by the user.
 * @param results A list of image results provided by the API. Each result contains information
 *     about the image, such as its URL, width, height, and more.
 * @param extra Additional information about the response, such as the total number of results and
 *     the search engine used.
 */
public record ImageSearchApiResponse(
    String type, ImageQuery query, List<ImageResult> results, Extra extra) implements ApiResponse {}
