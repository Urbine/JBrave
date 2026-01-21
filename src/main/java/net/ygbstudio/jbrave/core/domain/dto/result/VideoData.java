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

package net.ygbstudio.jbrave.core.domain.dto.result;

import java.util.List;
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import net.ygbstudio.jbrave.core.domain.dto.result.profile.Profile;

/**
 * Represents video data in search results. This record provides more specific and structured
 * metadata about videos. It includes parameters such as duration, views, creator, publisher, thumbnail, tags,
 * author, and requiresSubscription.
 *
 * @param duration the duration of the video
 * @param views the number of views of the video
 * @param creator the creator of the video
 * @param publisher the publisher of the video
 * @param thumbnail the thumbnail of the video
 * @param tags the tags associated with the video
 * @param author the author profile of the video
 * @param requiresSubscription a boolean indicating whether the video requires subscription
 */
public record VideoData(
    String duration,
    String views,
    String creator,
    String publisher,
    Thumbnail thumbnail,
    List<String> tags,
    Profile author,
    Boolean requiresSubscription) {}
