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
import net.ygbstudio.jbrave.core.domain.dto.result.VideoData;

/**
 * Represents a recipe in search results. This is used in the Brave Search API to provide structured
 * data about cooking recipes, including ingredients, instructions, and nutritional information.
 *
 * @param title The title of the recipe.
 * @param description A brief description or summary of the recipe.
 * @param thumbnail An image representing the finished dish.
 * @param url The URL where the full recipe can be found.
 * @param domain The domain name where the recipe is hosted.
 * @param favicon The URL of the website's favicon.
 * @param time The total time required to prepare and cook the recipe.
 * @param prepTime The preparation time required before cooking.
 * @param cookTime The cooking time required.
 * @param ingredients A formatted string containing the list of ingredients.
 * @param instructions Step-by-step instructions for preparing the recipe.
 * @param servings The number of servings the recipe yields.
 * @param calories The total number of calories per serving.
 * @param rating The average user rating of the recipe.
 * @param recipeCategory The category of the recipe (e.g., "Dinner", "Dessert").
 * @param recipeCusine The cuisine type of the recipe (e.g., "Italian", "Mexican").
 * @param video A video demonstrating how to prepare the recipe.
 */
public record Recipe(
    String title,
    String description,
    Thumbnail thumbnail,
    String url,
    String domain,
    String favicon,
    String time,
    String prepTime,
    String cookTime,
    String ingredients,
    List<HowTo> instructions,
    Integer servings,
    Integer calories,
    Rating rating,
    String recipeCategory,
    String recipeCusine,
    VideoData video) {}
