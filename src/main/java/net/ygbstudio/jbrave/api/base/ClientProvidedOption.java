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

package net.ygbstudio.jbrave.api.base;

import net.ygbstudio.jbrave.api.base.model.SearchOptionCarrier;

/**
 * This interface identifies search options that must provide methods to ensure that they produce
 * the correct typed {@link SearchOptionCarrier} since their values are provided by the client, not
 * by the API.
 *
 * <p>Options of type {@link ClientProvidedOption} require a that a {@link SearchOptionCarrier} is
 * instantiated with a client-provided value. If an option is provided by the API, its conversion to
 * an option carrier object does not need a validated input and that is what makes this interface
 * different from {@link ProvidedOption}.
 *
 * @see SearchOption
 * @see SearchOptionCarrier
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public non-sealed interface ClientProvidedOption extends SearchOption {}
