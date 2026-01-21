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

import net.ygbstudio.jbrave.core.domain.dto.error.ApiErrorModel;

/**
 * Error response object that the Brave Search API sends in case the client's request results in
 * either 404, 422, or 429 http status codes.
 *
 * @param type name of the error
 * @param error error response object
 * @param time timestamp
 */
public record ErrorResponse(String type, ApiErrorModel error, Integer time)
    implements ApiResponse {}
