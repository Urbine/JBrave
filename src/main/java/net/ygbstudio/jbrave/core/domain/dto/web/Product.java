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
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;

/**
 * Represents a product in search results, containing detailed information about the product such as
 * its name, category, price, and GTIN identifiers. This is used in the Brave Search API to provide
 * structured data about products that are relevant to the search query.
 *
 * @param type The type of the product.
 * @param name The name or title of the product.
 * @param category The category or type of the product.
 * @param price The price of the product as a formatted string.
 * @param thumbnail A thumbnail image representing the product.
 * @param description A description of the product.
 * @param offers A list of offers available for this product.
 * @param rating The rating information for the product.
 * @param gtin The Global Trade Item Number (GTIN) of the product (format may vary).
 * @param gtin8 The 8-digit GTIN of the product.
 * @param gtin12 The 12-digit GTIN (UPC) of the product.
 * @param gtin13 The 13-digit GTIN (EAN) of the product.
 * @param gtin14 The 14-digit GTIN (ITF-14) of the product.
 */
public record Product(
    String type,
    String name,
    String category,
    String price,
    Thumbnail thumbnail,
    String description,
    List<Offer> offers,
    Rating rating,
    String gtin,
    String gtin8,
    String gtin12,
    String gtin13,
    String gtin14) {}
