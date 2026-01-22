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
import net.ygbstudio.jbrave.core.utils.JsonSupport;
import org.jetbrains.annotations.NotNull;

/**
 * Error response object that the Brave Search API sends in case the client's request results in
 * either 404, 422, or 429 http status codes.
 *
 * <p>This response record contains methods to deserialise and serialise instances.
 *
 * <p>Factory methods support:
 * <li>{@link String}
 * <li>{@link File}
 * <li>{@link Reader}
 * <li>{@link HttpResponse} <br>
 *
 *     <p>You can also write the contents of a response object to that filesystem, method {@link
 *     #write(File)} can be useful for that purpose.
 *
 * @param type name of the error
 * @param error error response object
 * @param time timestamp
 */
public record ErrorResponse(String type, ApiErrorModel error, Integer time) implements ApiResponse {

  public static ErrorResponse from(File dataFile) {
    return JsonSupport.readJsonFs(dataFile, ErrorResponse.class);
  }

  public static ErrorResponse from(String dataString) {
    return JsonSupport.objectFromJson(dataString, ErrorResponse.class);
  }

  public static ErrorResponse from(Reader dataReader) {
    return JsonSupport.jsonReader(dataReader, ErrorResponse.class);
  }

  public static ErrorResponse from(@NotNull HttpResponse<String> dataResponse) {
    return from(dataResponse.body());
  }

  public void write(File target) {
    JsonSupport.writeJsonFs(target, this);
  }
}
