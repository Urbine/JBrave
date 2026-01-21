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

package net.ygbstudio.jbrave.core.domain.dto.error;

import java.util.Objects;
import java.util.StringJoiner;
import net.ygbstudio.jbrave.core.model.BraveErrorCode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Error model object that Brave Search API uses to communicate with the user and provide
 * remediation steps. It deserialises the error code to a native enum constant to facilitate error
 * handling strategies with switch statements.
 */
@JsonDeserialize(builder = ApiErrorModel.Builder.class)
public final class ApiErrorModel {
  private final String id;
  private final BraveErrorCode code;
  private final String detail;
  private final Integer status;
  private final ErrorMeta meta;

  @Contract(pure = true)
  public ApiErrorModel(@NotNull Builder builder) {
    this.id = builder.id;
    this.code = builder.code;
    this.detail = builder.detail;
    this.status = builder.status;
    this.meta = builder.meta;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
    private String id;
    private BraveErrorCode code;
    private String detail;
    private Integer status;
    private ErrorMeta meta;

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder code(String code) {
      this.code = BraveErrorCode.valueOf(code);
      return this;
    }

    public Builder detail(String detail) {
      this.detail = detail;
      return this;
    }

    public Builder status(Integer status) {
      this.status = status;
      return this;
    }

    public Builder meta(ErrorMeta meta) {
      this.meta = meta;
      return this;
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull ApiErrorModel build() {
      return new ApiErrorModel(this);
    }
  }

  public String id() {
    return id;
  }

  public BraveErrorCode code() {
    return code;
  }

  public String detail() {
    return detail;
  }

  public Integer status() {
    return status;
  }

  public ErrorMeta meta() {
    return meta;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    ApiErrorModel that = (ApiErrorModel) o;
    return Objects.equals(id, that.id)
        && code == that.code
        && Objects.equals(detail, that.detail)
        && Objects.equals(status, that.status)
        && Objects.equals(meta, that.meta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, code, detail, status, meta);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ApiErrorModel.class.getSimpleName() + "[", "]")
        .add("id='" + id + "'")
        .add("code=" + code)
        .add("detail='" + detail + "'")
        .add("status=" + status)
        .add("meta=" + meta)
        .toString();
  }
}
