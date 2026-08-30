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
 * Represents an infobox with location information in search results. This class provides structured
 * information about locations in the Brave Search API results.
 */
@JsonDeserialize(builder = InfoboxWithLocation.Builder.class)
@JsonTypeName(value = "location")
public final class InfoboxWithLocation extends AbstractGraphInfobox {
  private final String subType;
  private final Boolean isLocation;
  private final List<Double> coordinates;
  private final Integer zoomLevel;
  private final LocationResult location;

  /**
   * Creates a new location infobox from the given builder.
   *
   * @param builder the builder carrying the infobox fields
   */
  private InfoboxWithLocation(Builder builder) {
    super(builder);
    this.subType = builder.subType;
    this.isLocation = builder.isLocation;
    this.coordinates = builder.coordinates;
    this.zoomLevel = builder.zoomLevel;
    this.location = builder.location;
  }

  /** Builder for {@link InfoboxWithLocation} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends AbstractGraphInfobox.Builder {
    private String subType;
    private Boolean isLocation;
    private List<Double> coordinates;
    private Integer zoomLevel;
    private LocationResult location;

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
     * Sets whether this is a location.
     *
     * @param isLocation {@code true} if this is a location
     * @return this builder
     */
    public Builder isLocation(Boolean isLocation) {
      this.isLocation = isLocation;
      return this;
    }

    /**
     * Sets the coordinates.
     *
     * @param coordinates the coordinates
     * @return this builder
     */
    public Builder coordinates(List<Double> coordinates) {
      this.coordinates = coordinates;
      return this;
    }

    /**
     * Sets the zoom level.
     *
     * @param zoomLevel the zoom level
     * @return this builder
     */
    public Builder zoomLevel(Integer zoomLevel) {
      this.zoomLevel = zoomLevel;
      return this;
    }

    /**
     * Sets the location.
     *
     * @param location the location
     * @return this builder
     */
    public Builder location(LocationResult location) {
      this.location = location;
      return this;
    }

    /**
     * Builds a new {@link InfoboxWithLocation} from the current builder state.
     *
     * @return a new {@code InfoboxWithLocation} instance
     */
    @Contract(" -> new")
    @Override
    public @NotNull InfoboxWithLocation build() {
      return new InfoboxWithLocation(this);
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
   * Returns whether this is a location.
   *
   * @return whether this is a location
   */
  public Boolean getIsLocation() {
    return isLocation;
  }

  /**
   * Returns the coordinates.
   *
   * @return the coordinates
   */
  public List<Double> getCoordinates() {
    return coordinates;
  }

  /**
   * Returns the zoom level.
   *
   * @return the zoom level
   */
  public Integer getZoomLevel() {
    return zoomLevel;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    InfoboxWithLocation that = (InfoboxWithLocation) o;
    return Objects.equals(getSubType(), that.getSubType())
        && Objects.equals(isLocation, that.isLocation)
        && Objects.equals(getCoordinates(), that.getCoordinates())
        && Objects.equals(getZoomLevel(), that.getZoomLevel())
        && Objects.equals(getIsLocation(), that.getIsLocation());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getSubType(), isLocation, getCoordinates(), getZoomLevel(), getIsLocation());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", InfoboxWithLocation.class.getSimpleName() + "[", "]")
        .add("subType='" + subType + "'")
        .add("isLocation=" + isLocation)
        .add("coordinates=" + coordinates)
        .add("zoomLevel=" + zoomLevel)
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
