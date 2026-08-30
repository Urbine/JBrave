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

package net.ygbstudio.jbrave.core.builders;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import net.ygbstudio.jbrave.core.model.BraveSearchOperator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A builder class for constructing search operators.
 *
 * <p>This class provides a fluent API for constructing search operators. The {@link #build()}
 * method returns the constructed search operator string.
 *
 * <p>Example usage:
 *
 * {@snippet :
 * String searchOperator = SearchOperatorBuilder.builder()
 *     .filetype("html")
 *     .and(op -> op.loc("ca"), op -> op.intitle("java"))
 *     .build();
 * }
 *
 * <p>This will construct the search operator string {@code filetype:html loc:caANDintitle:java}.
 */
public final class SearchOperatorBuilder {
  private final Map<BraveSearchOperator, String> operatorTracker =
      new EnumMap<>(BraveSearchOperator.class);

  private SearchOperatorBuilder() {}

  /**
   * Creates a new instance of the {@link SearchOperatorBuilder} class.
   *
   * @return a new instance of the {@link SearchOperatorBuilder} class
   */
  @Contract(" -> new")
  public static @NotNull SearchOperatorBuilder builder() {
    return new SearchOperatorBuilder();
  }

  /**
   * Adds an operator and its corresponding value to the builder.
   *
   * @param operator the operator to add
   * @param value the value to associate with the operator
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  private SearchOperatorBuilder addOperator(
      @NotNull BraveSearchOperator operator, @NotNull String value) {
    operatorTracker.put(operator, value);
    return this;
  }

  /**
   * Adds an "ext" operator and its corresponding value to the builder.
   *
   * @param extension the file extension to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder ext(@NotNull String extension) {
    return addOperator(BraveSearchOperator.EXT, extension);
  }

  /**
   * Adds a "filetype" operator and its corresponding value to the builder.
   *
   * @param filetype the file type to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder filetype(@NotNull String filetype) {
    return addOperator(BraveSearchOperator.FILETYPE, filetype);
  }

  /**
   * Adds an "intitle" operator and its corresponding value to the builder.
   *
   * @param intitleContent the content to search for in the title
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder intitle(@NotNull String intitleContent) {
    return addOperator(BraveSearchOperator.INTITLE, intitleContent);
  }

  /**
   * Adds an "inpage" operator and its corresponding value to the builder.
   *
   * @param inpageContent the content to search for within the page
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder inpage(@NotNull String inpageContent) {
    return addOperator(BraveSearchOperator.INPAGE, inpageContent);
  }

  /**
   * Adds a "lang" operator and its corresponding value to the builder.
   *
   * @param language the language to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder lang(@NotNull String language) {
    return addOperator(BraveSearchOperator.LANG, language);
  }

  /**
   * Adds a "loc" operator and its corresponding value to the builder.
   *
   * @param location the location to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder loc(@NotNull String location) {
    return addOperator(BraveSearchOperator.LOC, location);
  }

  /**
   * Adds a "site" operator and its corresponding value to the builder.
   *
   * @param site the site to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder site(@NotNull String site) {
    return addOperator(BraveSearchOperator.SITE, site);
  }

  /**
   * Adds an "+" operator with its corresponding value to the builder.
   *
   * @param toInclude the content to include in the search
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder include(@NotNull String toInclude) {
    return addOperator(BraveSearchOperator.PLUS, toInclude);
  }

  /**
   * Adds a "-" operator with its corresponding value to the builder.
   *
   * @param toExclude the content to exclude from the search
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder exclude(@NotNull String toExclude) {
    return addOperator(BraveSearchOperator.MINUS, toExclude);
  }

  /**
   * Adds double quotes to the corresponding value (exact search term match) and adds it to the
   * builder.
   *
   * @param exactMatch the exact match to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder exactMatch(@NotNull String exactMatch) {
    return addOperator(BraveSearchOperator.QUOTE, "\"" + exactMatch + "\"");
  }

  /**
   * Adds the logical {@code AND} operator and its corresponding values to the builder.
   *
   * @param termOne the first term to search for
   * @param termTwo the second term to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder and(@NotNull String termOne, @NotNull String termTwo) {
    return addOperator(BraveSearchOperator.AND, termOne + BraveSearchOperator.AND + termTwo);
  }

  /**
   * Adds the logical {@code AND} operator and its corresponding values to the builder. Combines two
   * builder lambdas in order to construct logical expressions with search operators.
   *
   * @param operatorOne the first operator to search for
   * @param operatorTwo the second operator to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder and(
      @NotNull UnaryOperator<SearchOperatorBuilder> operatorOne,
      @NotNull UnaryOperator<SearchOperatorBuilder> operatorTwo) {
    return and(
        operatorOne.apply(SearchOperatorBuilder.builder()).build(),
        operatorTwo.apply(SearchOperatorBuilder.builder()).build());
  }

  /**
   * Adds the logical {@code OR} operator and its corresponding values to the builder.
   *
   * @param termOne the first term to search for
   * @param termTwo the second term to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder or(@NotNull String termOne, @NotNull String termTwo) {
    return addOperator(BraveSearchOperator.OR, termOne + BraveSearchOperator.OR + termTwo);
  }

  /**
   * Adds the logical {@code OR} operator and its corresponding values to the builder. Combines two
   * builder lambdas in order to construct logical expressions with search operators.
   *
   * @param operatorOne the first operator to search for
   * @param operatorTwo the second operator to search for
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder or(
      @NotNull UnaryOperator<SearchOperatorBuilder> operatorOne,
      @NotNull UnaryOperator<SearchOperatorBuilder> operatorTwo) {
    return or(
        operatorOne.apply(SearchOperatorBuilder.builder()).build(),
        operatorTwo.apply(SearchOperatorBuilder.builder()).build());
  }

  /**
   * Adds the logical {@code NOT} operator and its corresponding values to the builder.
   *
   * @param termOne the term to search for that should not be present
   * @param termTwo the term that should not be present
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder not(@NotNull String termOne, @NotNull String termTwo) {
    return addOperator(BraveSearchOperator.NOT, termOne + BraveSearchOperator.NOT + termTwo);
  }

  /**
   * Adds the logical {@code NOT} operator and its corresponding values to the builder. Combines two
   * builder lambdas in order to construct logical expressions with search operators.
   *
   * @param operatorOne the first operator to search for that should not be present
   * @param operatorTwo the second operator to search for that should not be present
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder not(
      @NotNull UnaryOperator<SearchOperatorBuilder> operatorOne,
      @NotNull UnaryOperator<SearchOperatorBuilder> operatorTwo) {
    return not(
        operatorOne.apply(SearchOperatorBuilder.builder()).build(),
        operatorTwo.apply(SearchOperatorBuilder.builder()).build());
  }

  /**
   * Clears the search operator builder by removing all the operators and their corresponding
   * values.
   *
   * @return the current instance of the {@link SearchOperatorBuilder} class
   */
  public SearchOperatorBuilder clear() {
    operatorTracker.clear();
    return this;
  }

  /**
   * Constructs the search operator string by concatenating the values of the builder with the
   * corresponding operators.
   *
   * @return the search operator string
   */
  public String build() {
    return operatorTracker.entrySet().stream()
        .map(
            entry ->
                entry.getKey().isLogicalOperator()
                    ? entry.getValue()
                    : entry.getKey() + entry.getValue())
        .collect(Collectors.joining(" "));
  }
}
