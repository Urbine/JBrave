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

package net.ygbstudio.jbrave.core.domain.dto.result.search;

import java.util.List;
import net.ygbstudio.jbrave.core.domain.dto.web.ResultReference;

/**
 * Represents mixed response information in search results. This class provides structured data
 * about mixed responses in the Brave Search API results. The class holds three lists of
 * {@link ResultReference} objects:
 * <ul>
 *     <li>Main: A list of main results.</li>
 *     <li>Top: A list of top results.</li>
 *     <li>Side: A list of side results.</li>
 * </ul>
 *
 * @param type The type of the mixed response.
 * @param main A list of main results.
 * @param top A list of top results.
 * @param side A list of side results.
 */
public record MixedResponse(
    String type,
    List<ResultReference> main,
    List<ResultReference> top,
    List<ResultReference> side) {}
