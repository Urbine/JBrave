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
 * Represents an image in search results, containing the image's URL, thumbnail, and additional
 * properties. This is used in the Brave Search API to provide structured image data that can be
 * displayed to users.
 *
 * @param thumbnail A thumbnail representation of the image, typically with smaller dimensions for
 *     preview purposes.
 * @param url The direct URL to the full-size image.
 * @param properties Additional properties of the image, such as dimensions and format.
 */
public record Image(Thumbnail thumbnail, String url, ImageProperties properties) {}
