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

package net.ygbstudio.jbrave.core.model;

import org.jetbrains.annotations.Contract;

public enum BraveSearchOperator {
  EXT("ext:"),
  FILETYPE("filetype:"),
  INTITLE("intitle:"),
  INBODY("inbody:"),
  INPAGE("inpage:"),
  LANG("lang:"),
  LOC("loc:"),
  SITE("site:"),
  PLUS("+"),
  MINUS("-"),
  QUOTE(""),
  AND("AND"),
  OR("OR"),
  NOT("NOT");

  private final String value;

  BraveSearchOperator(String value) {
    this.value = value;
  }

  /**
   * Returns true if the operator is a logical operator (AND, OR, NOT).
   *
   * @return true if the operator is a logical operator
   */
  @Contract(pure = true)
  public boolean isLogicalOperator() {
    return this == AND || this == OR || this == NOT;
  }

  @Override
  public String toString() {
    return value;
  }
}
