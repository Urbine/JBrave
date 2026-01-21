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

package net.ygbstudio.jbrave.core.domain.dto.result.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.ygbstudio.jbrave.core.domain.dto.web.Language;
import net.ygbstudio.jbrave.core.domain.dto.web.SearchOperators;

/**
 * Represents web query information in search results. This class provides structured data about web
 * queries in the Brave Search API results.
 *
 * @param original The original query string.
 * @param altered The altered query string.
 * @param cleaned The cleaned query string.
 * @param showStrictWarning Indicates if a strict warning should be shown.
 * @param safeSearch Indicates if the search is safe.
 * @param isNavigational Indicates if the query is navigational.
 * @param isGeolocal Indicates if the query is geolocal.
 * @param localDecision The local decision.
 * @param localLocationsIdx The local locations index.
 * @param isTrending Indicates if the query is trending.
 * @param isNewsBreaking Indicates if the query is news breaking.
 * @param askForLocation Indicates if a location should be asked.
 * @param language The language of the query.
 * @param spellcheckOff Indicates if spell checking is off.
 * @param country The country of the query.
 * @param badResults Indicates if the results are bad.
 * @param shouldFallback Indicates if the query should fallback.
 * @param lat The latitude.
 * @param lon The longitude.
 * @param postalCode The postal code.
 * @param city The city.
 * @param state The state.
 * @param headerCountry The country in the header.
 * @param moreResultsAvailable Indicates if more results are available.
 * @param customLocationLabel The custom location label.
 * @param redditCluster The Reddit cluster.
 * @param summaryKey The summary key.
 * @param searchOperators The search operators.
 */
public record WebQuery(
    String original,
    String altered,
    String cleaned,
    Boolean showStrictWarning,
    @JsonProperty("safesearch") Boolean safeSearch,
    Boolean isNavigational,
    Boolean isGeolocal,
    String localDecision,
    Integer localLocationsIdx,
    Boolean isTrending,
    Boolean isNewsBreaking,
    Boolean askForLocation,
    Language language,
    Boolean spellcheckOff,
    String country,
    Boolean badResults,
    Boolean shouldFallback,
    String lat,
    String lon,
    String postalCode,
    String city,
    String state,
    String headerCountry,
    Boolean moreResultsAvailable,
    String customLocationLabel,
    String redditCluster,
    String summaryKey,
    SearchOperators searchOperators) {}
