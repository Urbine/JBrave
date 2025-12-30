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

package net.ybstudio.jbrave.api.filters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.ybstudio.jbrave.api.base.SearchFilter;
import net.ybstudio.jbrave.api.exceptions.InvalidFreshnessInterval;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An enumeration representing freshness filters for the API requests. Filters search results by
 * when they were discovered.
 *
 * @see SearchFilter
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public enum Freshness implements SearchFilter {
  WITHIN_24H("pd"),
  WITHIN_7D("pw"),
  WITHIN_31D("pm"),
  WITHIN_1Y("py");

  private final String value;

  Freshness(String value) {
    this.value = value;
  }

  /**
   * Joins the given dates with the "to" keyword. Used for freshness filters that require a date
   * interval.
   *
   * <p>If you need to join dates with the "to" keyword and return it prefixed by the parameter for
   * the freshness filter, use {@link #betweenWithParam(LocalDate, LocalDate)} instead.
   *
   * @param startDate the first date.
   * @param endDate the second date.
   * @return the joined dates as a string.
   * @throws InvalidFreshnessInterval if the end date is before the start date.
   */
  public static @NotNull String between(@NotNull LocalDate startDate, @NotNull LocalDate endDate) {
    if (endDate.isBefore(startDate))
      throw new InvalidFreshnessInterval(
          () -> "Invalid date interval as " + endDate + " is before " + startDate);
    DateTimeFormatter dateISO = DateTimeFormatter.ISO_LOCAL_DATE;
    return dateISO.format(startDate) + "to" + dateISO.format(endDate);
  }

  /**
   * Joins the given dates with the "to" keyword and returns it as a parameter for the freshness
   * filter.
   *
   * <p>If you need to join dates with the "to" keyword without the freshness filter parameter, use
   * {@link #between(LocalDate, LocalDate)} instead.
   *
   * @param startDate the first date.
   * @param endDate the second date.
   * @return the freshness filter parameter with the joined dates as a string.
   * @throws InvalidFreshnessInterval if the end date is before the start date.
   */
  public static @NotNull String betweenWithParam(
      @NotNull LocalDate startDate, @NotNull LocalDate endDate) {
    return SearchFilter.FRESHNESS + "=" + between(startDate, endDate);
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  @Contract(pure = true)
  public @NotNull String urlParam() {
    return SearchFilter.FRESHNESS + "=" + value;
  }
}
