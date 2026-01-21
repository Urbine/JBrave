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

/**
 * Represents an image query in search requests. This class provides structured data about image
 * queries in the Brave Search API results.
 *
 * <p>The image query object contains information about the original query submitted by the user, as
 * well as any alterations made to the query during processing. It also includes flags indicating
 * whether spellcheck is disabled and whether a strict warning should be shown.
 *
 * @param original The original query string submitted by the user.
 * @param altered The altered version of the query after processing.
 * @param spellcheckOff Indicates if spellcheck is disabled.
 * @param showStrictWarning Indicates if a strict warning is shown.
 */
public record ImageQuery(
    String original, String altered, Boolean spellcheckOff, Boolean showStrictWarning) {}
