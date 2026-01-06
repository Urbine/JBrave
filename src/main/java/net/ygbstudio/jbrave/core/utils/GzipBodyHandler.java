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

package net.ygbstudio.jbrave.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import net.ygbstudio.jbrave.core.exceptions.ResponseDecompressionException;
import org.jetbrains.annotations.NotNull;

/**
 * A BodyHandler that decompresses the response body if it is compressed with GZIP.
 *
 * <p>This class is a BodyHandler that can be used with {@link HttpClient} to decompress the
 * response body if it is compressed with GZIP.
 *
 * @see HttpClient
 * @see HttpResponse
 * @see HttpResponse.BodyHandler
 */
public final class GzipBodyHandler implements HttpResponse.BodyHandler<String> {

  /**
   * Applies the function to the given {@link HttpResponse.ResponseInfo} and returns a {@link
   * BodySubscriber} that can be used to obtain the response body as a {@link String}.
   *
   * @param responseInfo the {@link HttpResponse.ResponseInfo} of the response
   * @return a {@link BodySubscriber} that can be used to obtain the response body as a {@link
   *     String}
   */
  @Override
  public @NotNull BodySubscriber<String> apply(HttpResponse.ResponseInfo responseInfo) {

    BodySubscriber<InputStream> upstream = BodySubscribers.ofInputStream();

    return BodySubscribers.mapping(
        upstream,
        inputStream -> {
          try (InputStream in =
                  isCompressed(responseInfo) ? new GZIPInputStream(inputStream) : inputStream;
              BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            return reader.lines().collect(Collectors.joining("\n"));
          } catch (IOException ioEx) {
            Supplier<String> decompressErr =
                () ->
                    "Failed to decompress GZIP response body."
                        + (Objects.nonNull(ioEx.getCause())
                            ? " Caused by "
                                + ioEx.getCause()
                                + " Reason: "
                                + ioEx.getCause().getMessage()
                            : "");
            throw new ResponseDecompressionException(decompressErr);
          }
        });
  }

  /**
   * Checks if the response is compressed with GZIP.
   *
   * @param responseInfo the {@link HttpResponse.ResponseInfo} of the response
   * @return true if the response is compressed with GZIP, false otherwise
   */
  private boolean isCompressed(HttpResponse.@NotNull ResponseInfo responseInfo) {
    return responseInfo
        .headers()
        .firstValue("Content-Encoding")
        .orElse("")
        .equalsIgnoreCase("gzip");
  }
}
