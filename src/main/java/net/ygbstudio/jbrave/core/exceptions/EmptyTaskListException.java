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

package net.ygbstudio.jbrave.core.exceptions;

import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when a task list is empty and the client requested an asynchronous execution.
 *
 * <p>The executor will collect the tasks and send them to a specialised executor method for
 * processing, however, the client must add the tasks as part of the lifecycle of any concrete
 * object.
 *
 * @see net.ygbstudio.jbrave.core.executors.AbstractRequestExecutor
 */
public class EmptyTaskListException extends BraveClientException {
  public EmptyTaskListException(String message) {
    super(message);
  }

  public EmptyTaskListException(@NotNull Supplier<String> message) {
    super(message.get());
  }
}
