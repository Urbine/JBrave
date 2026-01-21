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

package net.ygbstudio.jbrave.core.domain.dto.image;

import net.ygbstudio.jbrave.core.domain.dto.MetaUrl;

/**
 * Represents an image result in search responses. This class provides structured data about images
 * in the Brave Search API results.
 *
 * @param type The type of the image result.
 * @param title The title of the image.
 * @param url The URL of the image.
 * @param source The source of the image.
 * @param pageFetched The date when the page was fetched.
 * @param thumbnail The thumbnail representation of the image.
 * @param properties The properties of the image.
 * @param metaUrl Metadata about the image URL.
 * @param confidence The confidence score of the image result.
 */
public record ImageResult(
    String type,
    String title,
    String url,
    String source,
    String pageFetched,
    ImageThumbnail thumbnail,
    Properties properties,
    MetaUrl metaUrl,
    String confidence) {}
