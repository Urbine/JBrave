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
import net.ygbstudio.jbrave.core.domain.dto.result.AbstractGraphInfobox;

/**
 * Represents graph infobox information in search results. This class provides structured data about
 * graph infoboxes in the Brave Search API results.
 *
 * @param type the type of the graph infobox
 * @param results the list of {@link AbstractGraphInfobox} objects
 */
public record GraphInfobox(String type, List<AbstractGraphInfobox> results) {}
