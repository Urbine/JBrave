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

package net.ygbstudio.jbrave.core.model;

/**
 * Enumeration of Brave API error codes.
 *
 * <p>This enum represents the different error codes that the Brave API can return.
 *
 * @author Yoham Gabriel @ YGB Studio
 */
public enum BraveErrorCode {
  /** An internal server error. */
  INTERNAL,

  /** The request quota for the current period has been exhausted. */
  QUOTA_LIMITED,

  /** The request was rate-limited; the client should honor the {@code X-RateLimit-Reset} window. */
  RATE_LIMITED,

  /** The provided subscription token is not valid. */
  SUBSCRIPTION_TOKEN_INVALID,

  /** The subscription token does not match any active subscription. */
  SUBSCRIPTION_NOT_FOUND,

  /** The requested resource is not allowed under the current subscription plan. */
  RESOURCE_NOT_ALLOWED,

  /** The requested option is not included in the current subscription plan. */
  OPTION_NOT_IN_PLAN,

  /** The usage limit for the current subscription has been exceeded. */
  USAGE_LIMIT_EXCEEDED,

  /** The request contained an invalid URL parameter. */
  INVALID_URL,

  /** The request failed request-body validation. */
  VALIDATION;

  @Override
  public String toString() {
    return this.name();
  }
}
