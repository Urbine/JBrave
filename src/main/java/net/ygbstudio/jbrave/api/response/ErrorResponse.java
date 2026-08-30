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
import net.ygbstudio.jbrave.core.domain.dto.error.ApiErrorModel;
import org.jetbrains.annotations.NotNull;

/**
 * Error response object that the Brave Search API sends in case the client's request results in
 * either 404, 422, or 429 http status codes.
 *
 * <p>This response record contains methods to deserialise and serialise instances.
 *
 * <p>Factory methods are available for {@link String}, {@link File}, {@link Reader}, and {@link
 * HttpResponse} inputs. You can also write the contents of a response object to the filesystem;
 * {@link #write(File)} can be useful for that purpose.
 *
 * @param type name of the error
 * @param error error response object
 * @param time timestamp
 */
public record ErrorResponse(String type, ApiErrorModel error, Integer time) implements ApiResponse {

  /**
   * Deserializes a {@link ErrorResponse} from a JSON {@link File}.
   *
   * @param dataFile the file containing the JSON response data
   * @return the deserialized response
   */
  public static ErrorResponse from(File dataFile) {
    return ApiResponse.from(dataFile, ErrorResponse.class);
  }

  /**
   * Deserializes a {@link ErrorResponse} from a JSON {@link String}.
   *
   * @param dataString the string containing the JSON response data
   * @return the deserialized response
   */
  public static ErrorResponse from(String dataString) {
    return ApiResponse.from(dataString, ErrorResponse.class);
  }

  /**
   * Deserializes a {@link ErrorResponse} from a JSON {@link Reader}.
   *
   * @param dataReader the reader supplying the JSON response data
   * @return the deserialized response
   */
  public static ErrorResponse from(Reader dataReader) {
    return ApiResponse.from(dataReader, ErrorResponse.class);
  }

  /**
   * Deserializes a {@link ErrorResponse} from the body of an HTTP response.
   *
   * @param dataResponse the HTTP response whose body contains the JSON response data
   * @return the deserialized response
   */
  public static ErrorResponse from(@NotNull HttpResponse<String> dataResponse) {
    return from(dataResponse.body());
  }
}
