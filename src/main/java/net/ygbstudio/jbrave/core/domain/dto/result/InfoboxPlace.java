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
 * Represents an infobox for a place in search results. This class provides
 *
 * <p>structured information about places in the Brave Search API results.
 */
@JsonDeserialize(builder = InfoboxPlace.Builder.class)
@JsonTypeName(value = "place")
public final class InfoboxPlace extends AbstractGraphInfobox {
  private final String subType;
  private final LocationResult location;

  private InfoboxPlace(Builder builder) {
    super(builder);
    this.subType = builder.subType;
    this.location = builder.location;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends AbstractGraphInfobox.Builder {
    private String subType;
    private LocationResult location;

    public Builder subType(String subType) {
      this.subType = subType;
      return this;
    }

    public Builder location(LocationResult location) {
      this.location = location;
      return this;
    }

    @Contract(" -> new")
    @Override
    public @NotNull InfoboxPlace build() {
      return new InfoboxPlace(this);
    }
  }

  public String getSubType() {
    return subType;
  }

  public LocationResult getLocation() {
    return location;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    InfoboxPlace that = (InfoboxPlace) o;
    return Objects.equals(getSubType(), that.getSubType())
        && Objects.equals(getLocation(), that.getLocation());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSubType(), getLocation());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", InfoboxPlace.class.getSimpleName() + "[", "]")
        .add("subType='" + subType + "'")
        .add("location=" + location)
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
