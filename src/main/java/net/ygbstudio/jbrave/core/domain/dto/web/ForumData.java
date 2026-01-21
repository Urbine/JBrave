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
 * Represents forum data in search results, containing information about forum posts and discussions
 * that are relevant to the search query. This is typically used in the context of community
 * discussions and Q&A platforms.
 *
 * @param forumName The name of the forum where the post is located.
 * @param numAnswers The number of answers or replies to the forum post.
 * @param score The score or rating of the forum post, if available.
 * @param title The title of the forum post.
 * @param question The main question or content of the forum post.
 * @param topComment The top or most relevant comment from the forum post.
 */
public record ForumData(
    String forumName,
    Integer numAnswers,
    String score,
    String title,
    String question,
    String topComment) {}
