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

import java.util.List;

/**
 * Represents the opening hours for a business or location, including both the current day's hours
 * and a weekly schedule. This is used in the Brave Search API to provide structured information
 * about when a business is open or closed.
 *
 * @param currentDay The opening hours for the current day.
 * @param days A list of lists containing opening hours for each day of the week. Each inner list
 *     contains one or more time slots for that day.
 */
public record OpeningHours(List<DayOpeningHours> currentDay, List<List<DayOpeningHours>> days) {}
