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

package net.ygbstudio.jbrave.core.domain.dto;

/**
 * Represents a query in search requests. This class provides structured data about queries in the
 * Brave Search API results.
 *
 * @param original The original query string submitted by the user.
 * @param altered The altered version of the query after processing.
 * @param cleaned The cleaned version of the query.
 * @param spellcheckOff Indicates if spellcheck is disabled.
 * @param showStrictWarning Indicates if a strict warning should be shown.
 */
public record Query(
    String original,
    String altered,
    String cleaned,
    Boolean spellcheckOff,
    Boolean showStrictWarning) {}
