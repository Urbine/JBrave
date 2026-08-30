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
 * Internal building blocks that assemble Brave Search API request URLs and HTTP requests.
 *
 * <p>This package contains the abstract base classes that enforce the builder lifecycle (vertical
 * selection, option tracking, and URI construction) and the concrete request builders and header
 * builders used by the public {@link net.ygbstudio.jbrave.api.builders} package. The {@link
 * net.ygbstudio.jbrave.core.builders.SearchOperatorBuilder} constructs advanced search-operator
 * strings (for example {@code filetype:}, {@code site:}, and logical combinations).
 *
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
package net.ygbstudio.jbrave.core.builders;
