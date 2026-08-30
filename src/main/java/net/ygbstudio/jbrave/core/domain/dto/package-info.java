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
 * Shared data-transfer objects that mirror the JSON payloads returned by the Brave Search API.
 *
 * <p>This package holds the small, reusable record types ({@code Query}, {@code Thumbnail}, {@code
 * MetaUrl}, {@code OriginalQuery}, and {@code Extra}) that appear across multiple response shapes.
 * Richer, vertical-specific payloads live in the {@code dto.result}, {@code dto.web}, {@code
 * dto.image}, and {@code dto.error} sub-packages.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.core.domain.dto;
