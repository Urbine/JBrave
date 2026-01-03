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

package net.ygbstudio.jbrave.core.domain;

/**
 * An interface representing a search option.
 *
 * <p>This interface extends {@link BraveAPIConstant} and is implemented by enums that represent
 * search options.
 *
 * <p>A search option is a parameter that can be used to modify the behavior of the API requests. In
 * JBrave's base model, all search options appear in the query parameters of requests and that is
 * why identifiers, verticals, filters and options implement a subinterface of this interface by
 * default.
 *
 * @see BraveAPIConstant
 * @see ProvidedOption
 * @see ClientProvidedOption
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public sealed interface SearchOption extends BraveAPIConstant
    permits ProvidedOption, ClientProvidedOption {}
