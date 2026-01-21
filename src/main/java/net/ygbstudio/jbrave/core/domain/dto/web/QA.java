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

import net.ygbstudio.jbrave.core.domain.dto.MetaUrl;

/**
 * Represents a question and answer pair in search results, typically used in FAQ sections or Q&A
 * platforms. This provides a structured way to present question-answer content in the Brave Search
 * API responses.
 *
 * @param question The question being asked.
 * @param answer The answer to the question.
 * @param title The title of the page or section containing the Q&A.
 * @param url The URL where the Q&A can be found.
 * @param metaUrl Aggregated information about the URL, including protocol, domain, and favicon.
 */
public record QA(String question, String answer, String title, String url, MetaUrl metaUrl) {}
