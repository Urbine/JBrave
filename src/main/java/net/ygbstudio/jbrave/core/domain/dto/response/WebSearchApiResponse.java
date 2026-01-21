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

package net.ygbstudio.jbrave.core.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import net.ygbstudio.jbrave.core.domain.dto.result.search.Discussions;
import net.ygbstudio.jbrave.core.domain.dto.result.search.FAQ;
import net.ygbstudio.jbrave.core.domain.dto.result.search.GraphInfobox;
import net.ygbstudio.jbrave.core.domain.dto.result.search.Locations;
import net.ygbstudio.jbrave.core.domain.dto.result.search.MixedResponse;
import net.ygbstudio.jbrave.core.domain.dto.result.search.News;
import net.ygbstudio.jbrave.core.domain.dto.result.search.RichCallbackInfo;
import net.ygbstudio.jbrave.core.domain.dto.result.search.Search;
import net.ygbstudio.jbrave.core.domain.dto.result.search.Summarizer;
import net.ygbstudio.jbrave.core.domain.dto.result.search.Videos;
import net.ygbstudio.jbrave.core.domain.dto.result.search.WebQuery;

/**
 * Represents the top-level response from the Brave Search API. This class contains all the
 * different types of search results that can be returned by the API, organized by result type.
 *
 * @param type The type of the response.
 * @param discussions Forum and discussion results related to the search query.
 * @param faq Frequently asked questions and their answers.
 * @param infobox Structured information box with key details.
 * @param locations Location-based results and points of interest.
 * @param mixed Mixed collection of different result types.
 * @param news News article results.
 * @param query Information about the search query.
 * @param videos Video search results.
 * @param web Standard web search results.
 * @param summarizer AI-generated summary of search results.
 * @param rich Additional rich callback information.
 */
@JsonInclude(Include.NON_NULL)
public record WebSearchApiResponse(
    String type,
    Discussions discussions,
    FAQ faq,
    GraphInfobox infobox,
    Locations locations,
    MixedResponse mixed,
    News news,
    WebQuery query,
    Videos videos,
    Search web,
    Summarizer summarizer,
    RichCallbackInfo rich)
    implements ApiResponse {}
