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
import net.ygbstudio.jbrave.core.domain.dto.OriginalQuery;
import net.ygbstudio.jbrave.core.domain.dto.result.SpellCheckResult;

/**
 * Represents the response from the Brave Search API for spell check. This class contains the
 * original query and a list of spell check results.
 *
 * @param type The type of the response.
 * @param query The original query submitted by the user.
 * @param results A list of spell check results provided by the API.
 */
public record SpellCheckSearchApiResponse(
    String type, OriginalQuery query, List<SpellCheckResult> results) implements ApiResponse {}
