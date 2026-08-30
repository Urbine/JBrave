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

/**
 * Internal constants that group filter value enums under their URL parameter modes.
 *
 * <p>This package is intentionally {@link org.jetbrains.annotations.ApiStatus.Internal} and not
 * part of the public API. {@link net.ygbstudio.jbrave.core.domain.modes.SearchFilterMode} separates
 * the API parameter names ({@code freshness}, {@code safesearch}, {@code result_filter}) from the
 * client-facing value enums in {@link net.ygbstudio.jbrave.api.filters}, improving discoverability
 * and avoiding ambiguity about which enum to use.
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.core.domain.modes;
