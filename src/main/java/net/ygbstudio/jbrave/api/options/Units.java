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

package net.ygbstudio.jbrave.api.options;

import net.ygbstudio.jbrave.core.domain.ProvidedOption;
import net.ygbstudio.jbrave.core.domain.SearchOption;
import net.ygbstudio.jbrave.core.model.BraveSearchOption;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Enumeration of the different units of measurement that can be used in the search methods.
 *
 * @see SearchOption
 */
public enum Units implements ProvidedOption {
  METRIC("metric"),
  IMPERIAL("imperial");

  private final String value;

  Units(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  @Contract(pure = true)
  public @NotNull String urlParam() {
    return ProvidedOption.UNITS + "=" + value;
  }

  @Contract(" -> new")
  @Override
  public @NotNull @Unmodifiable SearchOptionCarrier<String> toSearchOption() {
    return BraveSearchOption.of(this, value);
  }
}
