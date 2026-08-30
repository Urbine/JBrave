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

/**
 * Enumeration of the search operators supported by the Brave Search API.
 *
 * <p>Each constant carries the literal prefix appended to a value when an operator string is built
 * via {@link net.ygbstudio.jbrave.core.builders.SearchOperatorBuilder} (for example {@code
 * filetype:html} or {@code site:example.com}). {@link #isLogicalOperator()} identifies the logical
 * combinators {@code AND}, {@code OR}, and {@code NOT}.
 */
public enum BraveSearchOperator {
  /** File extension search operator. */
  EXT("ext:"),

  /** Filetype search operator. */
  FILETYPE("filetype:"),

  /** Title content search operator. */
  INTITLE("intitle:"),

  /** Body content search operator. */
  INBODY("inbody:"),

  /** Page content search operator. */
  INPAGE("inpage:"),

  /** Language search operator. */
  LANG("lang:"),

  /** Location search operator. */
  LOC("loc:"),

  /** Site restriction operator. */
  SITE("site:"),

  /** Inclusion operator ({@code +}). */
  PLUS("+"),

  /** Exclusion operator ({@code -}). */
  MINUS("-"),

  /** Exact match operator (wraps a value in double quotes). */
  QUOTE(""),

  /** Logical AND operator. */
  AND("AND"),

  /** Logical OR operator. */
  OR("OR"),

  /** Logical NOT operator. */
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
