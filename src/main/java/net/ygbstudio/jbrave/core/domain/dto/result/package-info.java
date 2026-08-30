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

/**
 * Data-transfer objects that model web and generic search results from the Brave Search API.
 *
 * <p>This package contains the sealed {@code Result} hierarchy and its {@code SearchResult}
 * subtype, which capture the shared and web-specific fields of a single organic result. Related
 * container and rich-result shapes live in the {@code result.search}, {@code result.web}, {@code
 * result.profile}, and {@code result.thing} sub-packages.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.core.domain.dto.result;
