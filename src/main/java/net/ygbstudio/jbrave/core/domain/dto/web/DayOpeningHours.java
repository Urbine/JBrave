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

package net.ygbstudio.jbrave.core.domain.dto.web;

/**
 * Represents the opening hours for a specific day of the week for a business or location. This is
 * used in the Brave Search API to provide structured information about when a business is open or
 * closed on a particular day.
 *
 * @param abbrName The abbreviated name of the day (e.g., "Mon", "Tue").
 * @param fullName The full name of the day (e.g., "Monday", "Tuesday").
 * @param opens The time when the business opens, typically in 24-hour format (e.g., "09:00").
 * @param closes The time when the business closes, typically in 24-hour format (e.g., "17:00").
 */
public record DayOpeningHours(String abbrName, String fullName, String opens, String closes) {}
