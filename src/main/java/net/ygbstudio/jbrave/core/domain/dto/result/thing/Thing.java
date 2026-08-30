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

/**
 * Base type for the schema.org-style {@code Thing} entities referenced by search results.
 *
 * <p>This sealed class models the fields common to all entity results &mdash; {@code type}, {@code
 * name}, {@code url}, and {@code thumbnail} &mdash; and permits exactly the subtypes {@code
 * Person}, {@code Organization}, and {@code ContactPoint}.
 */
@JsonDeserialize(builder = Thing.Builder.class)
public sealed class Thing permits ContactPoint, Organization, Person {
  /** The thing type. */
  protected final String type;

  /** The thing name. */
  protected final String name;

  /** The thing URL. */
  protected final String url;

  /** The thing thumbnail. */
  protected final Thumbnail thumbnail;

  @Contract(pure = true)
  /**
   * Creates a new thing from the given builder.
   *
   * @param builder the builder carrying the thing fields
   */
  Thing(@NotNull Builder builder) {
    this.type = builder.type;
    this.name = builder.name;
    this.url = builder.url;
    this.thumbnail = builder.thumbnail;
  }

  /** Builder for {@link Thing} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
    private String type;
    private String name;
    private String url;
    private Thumbnail thumbnail;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the thing type.
     *
     * @param type the thing type
     * @return this builder
     */
    public Builder type(String type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the thing name.
     *
     * @param name the thing name
     * @return this builder
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the thing URL.
     *
     * @param url the URL
     * @return this builder
     */
    public Builder url(String url) {
      this.url = url;
      return this;
    }

    /**
     * Sets the thumbnail.
     *
     * @param thumbnail the thumbnail
     * @return this builder
     */
    public Builder thumbnail(Thumbnail thumbnail) {
      this.thumbnail = thumbnail;
      return this;
    }

    /**
     * Builds a new {@link Thing} from the current builder state.
     *
     * @return a new {@code Thing} instance
     */
    public Thing build() {
      return new Thing(this);
    }
  }

  /**
   * Returns the thing type.
   *
   * @return the thing type
   */
  public String getType() {
    return type;
  }

  /**
   * Returns the thing name.
   *
   * @return the thing name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the thing URL.
   *
   * @return the URL
   */
  public String getUrl() {
    return url;
  }

  /**
   * Returns the thumbnail.
   *
   * @return the thumbnail
   */
  public Thumbnail getThumbnail() {
    return thumbnail;
  }
}
