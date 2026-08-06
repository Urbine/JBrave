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
 * Represents an answer to a question in the FAQ section of Brave Search API responses. This is
 * typically used in the context of question-and-answer content, forum posts, or community answers.
 *
 * @param text The text content of the answer.
 * @param author The name or identifier of the author who provided the answer.
 * @param upvoteCount The number of upvotes the answer has received.
 * @param downvoteCount The number of downvotes the answer has received.
 */
public record Answer(String text, String author, Integer upvoteCount, Integer downvoteCount) {}
