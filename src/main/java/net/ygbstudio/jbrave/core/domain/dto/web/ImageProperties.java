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

/**
 * Represents the properties of an image in search results. This is used in the Brave Search API to
 * provide structured data about image attributes including dimensions, format, and URLs.
 *
 * @param url The original URL of the image.
 * @param resized A URL to a resized version of the image.
 * @param placeholder A URL to a placeholder version of the image.
 * @param height The height of the image in pixels.
 * @param width The width of the image in pixels.
 * @param format The file format of the image.
 * @param contentSize The size of the image content.
 */
public record ImageProperties(
    String url,
    String resized,
    String placeholder,
    Integer height,
    Integer width,
    String format,
    String contentSize) {}
