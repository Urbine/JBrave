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

package net.ygbstudio.jbrave.api.response;

import java.io.File;
import java.io.Reader;
import java.net.http.HttpResponse;
import java.util.List;
import net.ygbstudio.jbrave.core.domain.dto.OriginalQuery;
import net.ygbstudio.jbrave.core.domain.dto.result.SuggestResult;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the response from the Brave Search API for suggestions. This class contains the string
 * query and a list of suggestion results.
 *
 * <p>This response record contains methods to deserialise and serialise instances.
 *
 * <p>Factory methods are available for {@link String}, {@link File}, {@link Reader}, and {@link
 * HttpResponse} inputs. You can also write the contents of a response object to the filesystem;
 * {@link #write(File)} can be useful for that purpose.
 *
 * @param type The type of the response.
 * @param query The original query submitted by the user.
 * @param results A list of suggestion results provided by the API.
 */
public record SuggestSearchApiResponse(
    String type, OriginalQuery query, List<SuggestResult> results) implements ApiResponse {

  public static SuggestSearchApiResponse from(File dataFile) {
    return ApiResponse.from(dataFile, SuggestSearchApiResponse.class);
  }

  public static SuggestSearchApiResponse from(String dataString) {
    return ApiResponse.from(dataString, SuggestSearchApiResponse.class);
  }

  public static SuggestSearchApiResponse from(Reader dataReader) {
    return ApiResponse.from(dataReader, SuggestSearchApiResponse.class);
  }

  public static SuggestSearchApiResponse from(@NotNull HttpResponse<String> dataResponse) {
    return from(dataResponse.body());
  }
}
