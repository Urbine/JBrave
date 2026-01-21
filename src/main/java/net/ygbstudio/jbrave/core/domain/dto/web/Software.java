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
 * Represents software information in search results. This is used in the Brave Search API to
 * provide structured data about software packages, including version, repository, and metadata.
 *
 * @param name The name of the software.
 * @param author The author or organization that created the software.
 * @param version The current version of the software.
 * @param codeRepository The URL of the code repository (e.g., GitHub, GitLab).
 * @param homepage The URL of the software's homepage or documentation.
 * @param datePublished The date when this version was published.
 * @param isNpm Indicates if the software is a npm package.
 * @param isPypi Indicates if the software is a Python package on PyPI.
 * @param stars The number of stars the repository has received.
 * @param forks The number of times the repository has been forked.
 * @param programmingLanguage The primary programming language used in the software.
 */
public record Software(
    String name,
    String author,
    String version,
    String codeRepository,
    String homepage,
    String datePublished,
    Boolean isNpm,
    Boolean isPypi,
    Integer stars,
    Integer forks,
    String programmingLanguage) {}
