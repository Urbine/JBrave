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

package net.ygbstudio.jbrave.core.executors;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code BraveRequestExecutor} class is a concrete implementation of the {@link
 * AbstractRequestExecutor} abstract class. It provides a builder pattern for constructing and
 * executing HTTP requests for query builders in the project.
 */
public final class BraveRequestExecutor extends AbstractRequestExecutor<BraveRequestExecutor> {

  private BraveRequestExecutor() {}

  /**
   * Returns a new instance of {@link BraveRequestExecutor}.
   *
   * @return a new instance of {@link BraveRequestExecutor}
   */
  @Contract(" -> new")
  public static @NotNull BraveRequestExecutor getInstance() {
    return new BraveRequestExecutor();
  }

  /**
   * Submits a request to the request list.
   *
   * @param requestSupplier the request to be added
   * @return the current instance of the builder
   */
  public BraveRequestExecutor submitTask(Supplier<HttpRequest> requestSupplier) {
    return submit(requestSupplier);
  }

  /**
   * Returns a {@link Stream} of {@link HttpResponse}s, in request order.
   *
   * @return a {@link Stream} of {@link HttpResponse}s, in request order
   */
  public @NotNull Stream<HttpResponse<String>> responseStream() {
    return executeAllPaced(Function.identity()).stream();
  }

  /**
   * Executes the request and returns a single {@link HttpResponse} as a string.
   *
   * @param requestSupplier the request to be executed
   * @return a single {@link HttpResponse} as a string
   */
  public HttpResponse<String> executeStringResponseOnce(Supplier<HttpRequest> requestSupplier) {
    return submitTask(requestSupplier).executeAllPaced(Function.identity()).getFirst();
  }
}
