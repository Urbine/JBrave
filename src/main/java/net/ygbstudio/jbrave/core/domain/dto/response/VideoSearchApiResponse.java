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
import net.ygbstudio.jbrave.core.domain.dto.Query;
import net.ygbstudio.jbrave.core.domain.dto.result.VideoResult;

/**
 * Represents the response from the Brave Search API for video searches. This class contains the
 * original query, a list of video results, and extra information.
 *
 * @param type The type of the response.
 * @param query The original query submitted by the user.
 * @param results A list of video results provided by the API.
 * @param extra Additional information about the response.
 */
public record VideoSearchApiResponse(
    String type, Query query, List<VideoResult> results, Extra extra) implements ApiResponse {}
