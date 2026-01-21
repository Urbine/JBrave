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
 * Represents a physical mailing address in search results. This is used in the Brave Search API to
 * provide structured address information for locations, businesses, and other geographical
 * entities.
 *
 * @param type The type of address
 * @param country The country component of the address.
 * @param postalCode The postal or ZIP code of the address.
 * @param streetAddress The street address, including building number and street name.
 * @param addressRegion The region or state component of the address.
 * @param addressLocality The city or locality component of the address.
 * @param displayAddress A pre-formatted, human-readable representation of the full address.
 */
public record PostalAddress(
    String type,
    String country,
    String postalCode,
    String streetAddress,
    String addressRegion,
    String addressLocality,
    String displayAddress) {}
