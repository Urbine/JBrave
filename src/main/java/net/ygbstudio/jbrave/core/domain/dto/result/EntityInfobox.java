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

package net.ygbstudio.jbrave.core.domain.dto.result;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Objects;
import java.util.StringJoiner;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents an entity infobox in search results. This class provides structured information about
 * entities in the Brave Search API results.
 */
@JsonDeserialize(builder = EntityInfobox.Builder.class)
@JsonTypeName(value = "entity")
public final class EntityInfobox extends AbstractGraphInfobox {

  private final String subType;

  /**
   * Creates a new entity infobox from the given builder.
   *
   * @param builder the builder carrying the infobox fields
   */
  private EntityInfobox(Builder builder) {
    super(builder);
    this.subType = builder.subtype;
  }

  /** Builder for {@link EntityInfobox} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends AbstractGraphInfobox.Builder {
    private String subtype;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the entity subtype.
     *
     * @param subtype the entity subtype
     * @return this builder
     */
    public Builder subtype(String subtype) {
      this.subtype = subtype;
      return this;
    }

    /**
     * Builds a new {@link EntityInfobox} from the current builder state.
     *
     * @return a new {@code EntityInfobox} instance
     */
    @Contract(" -> new")
    @Override
    public @NotNull EntityInfobox build() {
      return new EntityInfobox(this);
    }
  }

  /**
   * Returns the entity subtype.
   *
   * @return the entity subtype
   */
  public String getSubType() {
    return subType;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    EntityInfobox that = (EntityInfobox) o;
    return Objects.equals(subType, that.subType);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(subType);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", EntityInfobox.class.getSimpleName() + "[", "]")
        .add("subType='" + subType + "'")
        .add("type='" + type + "'")
        .add("position=" + position)
        .add("label='" + label + "'")
        .add("category='" + category + "'")
        .add("longDesc='" + longDesc + "'")
        .add("thumbnail=" + thumbnail)
        .add("attributes=" + attributes)
        .add("profiles=" + profiles)
        .add("websiteUrl='" + websiteUrl + "'")
        .add("ratings=" + ratings)
        .add("providers=" + providers)
        .add("distance=" + distance)
        .add("images=" + images)
        .add("movie=" + movie)
        .add("title='" + title + "'")
        .add("url='" + url + "'")
        .add("isSourceLocal=" + isSourceLocal)
        .add("isSourceBoth=" + isSourceBoth)
        .add("description='" + description + "'")
        .add("pageAge='" + pageAge + "'")
        .add("pageFetched='" + pageFetched + "'")
        .add("profile=" + profile)
        .add("language='" + language + "'")
        .add("familyFriendly=" + familyFriendly)
        .toString();
  }
}
