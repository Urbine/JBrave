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

import java.util.List;

/**
 * Represents a step in a how-to guide or tutorial. This is used in the Brave Search API to provide
 * structured data for step-by-step instructions in search results.
 *
 * @param text The text content of the step.
 * @param name The name or title of the step.
 * @param url A URL with more information about this step.
 * @param image A list of image URLs related to this step.
 */
public record HowTo(String text, String name, String url, List<String> image) {}
