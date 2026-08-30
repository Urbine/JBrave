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
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a generic infobox in search results. This class provides structured information that
 * can be used for various types of infoboxes in the Brave Search API results.
 */
@JsonDeserialize(builder = GenericInfobox.Builder.class)
@JsonTypeName(value = "generic")
public final class GenericInfobox extends AbstractGraphInfobox {
  private final String subType;
  private final List<String> foundInUrls;

  /**
   * Creates a new generic infobox from the given builder.
   *
   * @param builder the builder carrying the infobox fields
   */
  private GenericInfobox(Builder builder) {
    super(builder);
    this.subType = builder.subType;
    this.foundInUrls = builder.foundInUrls;
  }

  /** Builder for {@link GenericInfobox} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends AbstractGraphInfobox.Builder {
    private String subType;
    private List<String> foundInUrls;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the infobox subtype.
     *
     * @param subType the subtype
     * @return this builder
     */
    public Builder subType(String subType) {
      this.subType = subType;
      return this;
    }

    /**
     * Sets the URLs where this infobox was found.
     *
     * @param foundInUrls the found-in URLs
     * @return this builder
     */
    public Builder foundInUrls(List<String> foundInUrls) {
      this.foundInUrls = foundInUrls;
      return this;
    }

    /**
     * Builds a new {@link GenericInfobox} from the current builder state.
     *
     * @return a new {@code GenericInfobox} instance
     */
    @Contract(" -> new")
    @Override
    public @NotNull GenericInfobox build() {
      return new GenericInfobox(this);
    }
  }

  /**
   * Returns the infobox subtype.
   *
   * @return the subtype
   */
  public String getSubType() {
    return subType;
  }

  /**
   * Returns the URLs where this infobox was found.
   *
   * @return the found-in URLs
   */
  public List<String> getFoundInUrls() {
    return foundInUrls;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    GenericInfobox that = (GenericInfobox) o;
    return Objects.equals(subType, that.subType) && Objects.equals(foundInUrls, that.foundInUrls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subType, foundInUrls);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", GenericInfobox.class.getSimpleName() + "[", "]")
        .add("subType='" + subType + "'")
        .add("foundInUrls=" + foundInUrls)
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
