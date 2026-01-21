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

package net.ygbstudio.jbrave.core.domain.dto.result.profile;

/**
 * Represents a profile in search results. This class provides structured data about profiles in the
 * Brave Search API results.
 *
 * @param name The name of the profile.
 * @param longName The long name of the profile.
 * @param url The URL associated with the profile.
 * @param img The URL of the profile image.
 */
public record InfoboxProfile(String name, String longName, String url, String img)
    implements GraphInfoboxProfile {}
