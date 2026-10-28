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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Concrete request executor used by {@link BraveExecutionGate}.
 *
 * <p>This class completes the package-private execution pipeline defined by {@link
 * AbstractRequestExecutor} and exposes a factory entry point for gates that need an executor
 * instance. It does not build or configure HTTP requests; request construction is handled by the
 * builders in the {@code api} package.
 */
final class BraveRequestExecutor extends AbstractRequestExecutor {

  private BraveRequestExecutor() {}

  /**
   * Creates a new executor instance.
   *
   * <p>This method does not return a singleton; each invocation creates an independent executor.
   *
   * @return a new instance of {@link BraveRequestExecutor}
   */
  @Contract(" -> new")
  static @NotNull BraveRequestExecutor newInstance() {
    return new BraveRequestExecutor();
  }
}
