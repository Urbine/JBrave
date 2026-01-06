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

package net.ygbstudio.jbrave.core.builders;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.function.Supplier;
import net.ygbstudio.jbrave.core.exceptions.BraveClientException;
import net.ygbstudio.jbrave.core.utils.GzipBodyHandler;

/**
 * Abstract class for building the request and executing it.
 *
 * <p>This class is designed to be extended by concrete implementations of request executors. It
 * provides a base implementation for executing requests.
 *
 * @param <T> The type of the concrete implementation of this abstract class.
 */
public abstract class AbstractRequestExecutor<T extends AbstractRequestExecutor<T>> {

  /**
   * Returns the current instance of the builder.
   *
   * <p>The cast is safe because the method is declared to return the type parameter T, which is
   * defined as {@code T extends AbstractRequestExecutor<T>}. This means that T will always be a
   * subclass of AbstractRequestExecutor<T>, so it is safe to cast "this" to T.
   *
   * @return Current instance of the builder.
   */
  @SuppressWarnings("unchecked")
  protected T self() {
    return (T) this;
  }

  /**
   * Executes the request and returns an optional response.
   *
   * @return an optional response to the request
   * @throws InterruptedException if the execution is interrupted
   */
  public HttpResponse<String> execute(HttpRequest request) throws InterruptedException {
    try (HttpClient client = HttpClient.newHttpClient()) {
      return client.send(request, new GzipBodyHandler());
    } catch (IOException ioEx) {
      Supplier<String> clientEx =
          () ->
              "Unable to process request for "
                  + request.uri().toString()
                  + (Objects.nonNull(ioEx.getCause()) ? " Caused by: " + ioEx.getCause() : "");
      throw new BraveClientException(clientEx);
    }
  }
}
