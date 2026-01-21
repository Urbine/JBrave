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

package net.ygbstudio.jbrave.core.domain.dto.result.thing;

import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Thing.Builder.class)
public sealed class Thing permits ContactPoint, Organization, Person {
  protected final String type;
  protected final String name;
  protected final String url;
  protected final Thumbnail thumbnail;

  @Contract(pure = true)
  Thing(@NotNull Builder builder) {
    this.type = builder.type;
    this.name = builder.name;
    this.url = builder.url;
    this.thumbnail = builder.thumbnail;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
    private String type;
    private String name;
    private String url;
    private Thumbnail thumbnail;

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder thumbnail(Thumbnail thumbnail) {
      this.thumbnail = thumbnail;
      return this;
    }

    public Thing build() {
      return new Thing(this);
    }
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public Thumbnail getThumbnail() {
    return thumbnail;
  }
}
