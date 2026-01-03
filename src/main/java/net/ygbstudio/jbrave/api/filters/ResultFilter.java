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

package net.ygbstudio.jbrave.api.filters;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import net.ygbstudio.jbrave.core.ClientProvidedOption;
import net.ygbstudio.jbrave.core.SearchFilter;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import net.ygbstudio.jbrave.api.filters.modes.SearchFilterMode;
import net.ygbstudio.jbrave.api.options.BraveSearchOption;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Enumeration of possible result filters to be used in the api requests.
 *
 * <p>A {@link ResultFilter} is typically a comma-delimited string of result types to include in the
 * search response.
 *
 * <p>According to the official documentation: <i>Not specifying this parameter will return back all
 * result types in search response where data is available and a plan with the corresponding option
 * is subscribed. The response always includes query and type to identify any query modifications
 * and response type respectively.</i>
 *
 * <p>Support for advanced plans is currently limited.
 *
 * @see SearchFilter
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public enum ResultFilter implements SearchFilter, ClientProvidedOption {
  DISCUSSIONS("discussions"),
  FAQ("faq"),
  INFOBOX("infobox"),
  LOCATIONS("locations"),
  NEWS("news"),
  QUERY("query"),
  SUMMARIZER("summarizer"),
  VIDEOS("videos"),
  WEB("web");

  private final String value;

  ResultFilter(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  @Contract(pure = true)
  public @NotNull String urlParam() {
    return SearchFilterMode.RESULT_FILTER.urlParam();
  }

  /**
   * Joins the given {@link ResultFilter} options with a comma, to be used in the URL parameter.
   *
   * @param options the {@link ResultFilter} options to join.
   * @return the joined options as a string.
   */
  private static @NotNull String joinOptions(ResultFilter... options) {
    return Arrays.stream(options).map(ResultFilter::value).collect(Collectors.joining(","));
  }

  @Contract("_ -> new")
  public static @NotNull @Unmodifiable SearchOptionCarrier<String> from(
      @NotNull Set<ResultFilter> resultFilterSet) {
    return BraveSearchOption.of(
        SearchFilterMode.RESULT_FILTER, joinOptions(resultFilterSet.toArray(ResultFilter[]::new)));
  }

  @Contract(" -> new")
  @Override
  public @NotNull @Unmodifiable SearchOptionCarrier<String> toSearchOption() {
    return BraveSearchOption.of(SearchFilterMode.RESULT_FILTER, value);
  }
}
