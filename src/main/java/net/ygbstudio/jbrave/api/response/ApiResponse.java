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
import net.ygbstudio.jbrave.core.utils.JsonSupport;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a response from the Brave Search API.
 *
 * <p>Each implementation corresponds to a search vertical (Web, Image, News, Video, Spellcheck, and
 * Suggest) or to an API error. Responses are deserialized from JSON with the {@code from} methods
 * and serialized back to JSON with {@link #toJson()} and {@link #write(java.io.File)}.
 */
public sealed interface ApiResponse
    permits WebSearchApiResponse,
        ErrorResponse,
        ImageSearchApiResponse,
        NewsSearchApiResponse,
        SpellcheckSearchApiResponse,
        VideoSearchApiResponse,
        SuggestSearchApiResponse {

  /**
   * Serializes the response object to a JSON string.
   *
   * @return a JSON string representation of the response object.
   */
  default String toJson() {
    return JsonSupport.toJsonString(this);
  }

  /**
   * Writes the response object to a file on the filesystem.
   *
   * @param target the file to write the response object to.
   */
  default void write(File target) {
    JsonSupport.writeJsonFs(target, this);
  }

  /**
   * Deserializes an API response from a JSON file.
   *
   * @param <T> the type of the API response
   * @param dataFile the file containing the JSON response data
   * @param clazz the API response class to deserialize into
   * @return the deserialized response
   */
  static <T extends ApiResponse> T from(File dataFile, Class<T> clazz) {
    return JsonSupport.readJsonFs(dataFile, clazz);
  }

  /**
   * Deserializes an API response from a JSON string.
   *
   * @param <T> the type of the API response
   * @param dataString the string containing the JSON response data
   * @param clazz the API response class to deserialize into
   * @return the deserialized response
   */
  static <T extends ApiResponse> T from(String dataString, Class<T> clazz) {
    return JsonSupport.objectFromJson(dataString, clazz);
  }

  /**
   * Deserializes an API response from a JSON reader.
   *
   * @param <T> the type of the API response
   * @param dataReader the reader supplying the JSON response data
   * @param clazz the API response class to deserialize into
   * @return the deserialized response
   */
  static <T extends ApiResponse> T from(Reader dataReader, Class<T> clazz) {
    return JsonSupport.jsonReader(dataReader, clazz);
  }

  /**
   * Deserializes an API response from the body of an HTTP response.
   *
   * @param <T> the type of the API response
   * @param dataResponse the HTTP response whose body contains the JSON response data
   * @param clazz the API response class to deserialize into
   * @return the deserialized response
   */
  static <T extends ApiResponse> T from(
      @NotNull HttpResponse<String> dataResponse, Class<T> clazz) {
    return from(dataResponse.body(), clazz);
  }
}
