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

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import net.ygbstudio.jbrave.core.domain.dto.result.profile.DataProvider;
import net.ygbstudio.jbrave.core.domain.dto.web.Action;
import net.ygbstudio.jbrave.core.domain.dto.web.Contact;
import net.ygbstudio.jbrave.core.domain.dto.web.OpeningHours;
import net.ygbstudio.jbrave.core.domain.dto.web.PictureResults;
import net.ygbstudio.jbrave.core.domain.dto.web.PostalAddress;
import net.ygbstudio.jbrave.core.domain.dto.web.Rating;
import net.ygbstudio.jbrave.core.domain.dto.web.Reviews;
import net.ygbstudio.jbrave.core.domain.dto.web.Unit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a location result in search results. This class provides structured information about
 * locations in the Brave Search API results.
 */
@JsonDeserialize(builder = LocationResult.Builder.class)
public final class LocationResult extends Result {
  private final String type;
  private final String id;
  private final String providerUrl;
  private final List<Double> coordinates;
  private final Integer zoomLevel;
  private final Thumbnail thumbnail;
  private final PostalAddress postalAddress;
  private final OpeningHours openingHours;
  private final Contact contact;
  private final String priceRange;
  private final Rating rating;
  private final Unit distance;
  private final List<DataProvider> profiles;
  private final Reviews reviews;
  private final PictureResults pictures;
  private final Action action;
  private final List<String> servesCuisine;
  private final List<String> categories;
  private final String iconCategory;
  private final String timezone;
  private final String timezoneOffset;

  public LocationResult(@NotNull Builder builder) {
    super(builder);
    this.type = builder.type;
    this.id = builder.id;
    this.providerUrl = builder.providerUrl;
    this.coordinates = builder.coordinates;
    this.zoomLevel = builder.zoomLevel;
    this.thumbnail = builder.thumbnail;
    this.postalAddress = builder.postalAddress;
    this.openingHours = builder.openingHours;
    this.contact = builder.contact;
    this.priceRange = builder.priceRange;
    this.rating = builder.rating;
    this.distance = builder.distance;
    this.profiles = builder.profiles;
    this.reviews = builder.reviews;
    this.pictures = builder.pictures;
    this.action = builder.action;
    this.servesCuisine = builder.servesCuisine;
    this.categories = builder.categories;
    this.iconCategory = builder.iconCategory;
    this.timezone = builder.timezone;
    this.timezoneOffset = builder.timezoneOffset;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends Result.Builder {
    private String type;
    private String id;
    private String providerUrl;
    private List<Double> coordinates;
    private Integer zoomLevel;
    private Thumbnail thumbnail;
    private PostalAddress postalAddress;
    private OpeningHours openingHours;
    private Contact contact;
    private String priceRange;
    private Rating rating;
    private Unit distance;
    private List<DataProvider> profiles;
    private Reviews reviews;
    private PictureResults pictures;
    private Action action;
    private List<String> servesCuisine;
    private List<String> categories;
    private String iconCategory;
    private String timezone;
    private String timezoneOffset;

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder providerUrl(String providerUrl) {
      this.providerUrl = providerUrl;
      return this;
    }

    public Builder coordinates(List<Double> coordinates) {
      this.coordinates = coordinates;
      return this;
    }

    public Builder zoomLevel(Integer zoomLevel) {
      this.zoomLevel = zoomLevel;
      return this;
    }

    public Builder thumbnail(Thumbnail thumbnail) {
      this.thumbnail = thumbnail;
      return this;
    }

    public Builder postalAddress(PostalAddress postalAddress) {
      this.postalAddress = postalAddress;
      return this;
    }

    public Builder openingHours(OpeningHours openingHours) {
      this.openingHours = openingHours;
      return this;
    }

    public Builder contact(Contact contact) {
      this.contact = contact;
      return this;
    }

    public Builder priceRange(String priceRange) {
      this.priceRange = priceRange;
      return this;
    }

    public Builder rating(Rating rating) {
      this.rating = rating;
      return this;
    }

    public Builder distance(Unit distance) {
      this.distance = distance;
      return this;
    }

    public Builder profiles(List<DataProvider> profiles) {
      this.profiles = profiles;
      return this;
    }

    public Builder reviews(Reviews reviews) {
      this.reviews = reviews;
      return this;
    }

    public Builder pictures(PictureResults pictures) {
      this.pictures = pictures;
      return this;
    }

    public Builder action(Action action) {
      this.action = action;
      return this;
    }

    public Builder servesCuisine(List<String> servesCuisine) {
      this.servesCuisine = servesCuisine;
      return this;
    }

    public Builder categories(List<String> categories) {
      this.categories = categories;
      return this;
    }

    public Builder iconCategory(String iconCategory) {
      this.iconCategory = iconCategory;
      return this;
    }

    public Builder timezone(String timezone) {
      this.timezone = timezone;
      return this;
    }

    public Builder timezoneOffset(String timezoneOffset) {
      this.timezoneOffset = timezoneOffset;
      return this;
    }

    @Override
    @Contract(" -> new")
    public @NotNull LocationResult build() {
      return new LocationResult(this);
    }
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public String getProviderUrl() {
    return providerUrl;
  }

  public List<Double> getCoordinates() {
    return coordinates;
  }

  public Integer getZoomLevel() {
    return zoomLevel;
  }

  public Thumbnail getThumbnail() {
    return thumbnail;
  }

  public PostalAddress getPostalAddress() {
    return postalAddress;
  }

  public OpeningHours getOpeningHours() {
    return openingHours;
  }

  public Contact getContact() {
    return contact;
  }

  public String getPriceRange() {
    return priceRange;
  }

  public Rating getRating() {
    return rating;
  }

  public Unit getDistance() {
    return distance;
  }

  public List<DataProvider> getProfiles() {
    return profiles;
  }

  public Reviews getReviews() {
    return reviews;
  }

  public PictureResults getPictures() {
    return pictures;
  }

  public Action getAction() {
    return action;
  }

  public List<String> getServesCuisine() {
    return servesCuisine;
  }

  public List<String> getCategories() {
    return categories;
  }

  public String getIconCategory() {
    return iconCategory;
  }

  public String getTimezone() {
    return timezone;
  }

  public String getTimezoneOffset() {
    return timezoneOffset;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    LocationResult that = (LocationResult) o;
    return Objects.equals(getType(), that.getType())
        && Objects.equals(getId(), that.getId())
        && Objects.equals(getProviderUrl(), that.getProviderUrl())
        && Objects.equals(getCoordinates(), that.getCoordinates())
        && Objects.equals(getZoomLevel(), that.getZoomLevel())
        && Objects.equals(getThumbnail(), that.getThumbnail())
        && Objects.equals(getPostalAddress(), that.getPostalAddress())
        && Objects.equals(getOpeningHours(), that.getOpeningHours())
        && Objects.equals(getContact(), that.getContact())
        && Objects.equals(getPriceRange(), that.getPriceRange())
        && Objects.equals(getRating(), that.getRating())
        && Objects.equals(getDistance(), that.getDistance())
        && Objects.equals(getProfiles(), that.getProfiles())
        && Objects.equals(getReviews(), that.getReviews())
        && Objects.equals(getPictures(), that.getPictures())
        && Objects.equals(getAction(), that.getAction())
        && Objects.equals(getServesCuisine(), that.getServesCuisine())
        && Objects.equals(getCategories(), that.getCategories())
        && Objects.equals(getIconCategory(), that.getIconCategory())
        && Objects.equals(getTimezone(), that.getTimezone())
        && Objects.equals(getTimezoneOffset(), that.getTimezoneOffset());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getType(),
        getId(),
        getProviderUrl(),
        getCoordinates(),
        getZoomLevel(),
        getThumbnail(),
        getPostalAddress(),
        getOpeningHours(),
        getContact(),
        getPriceRange(),
        getRating(),
        getDistance(),
        getProfiles(),
        getReviews(),
        getPictures(),
        getAction(),
        getServesCuisine(),
        getCategories(),
        getIconCategory(),
        getTimezone(),
        getTimezoneOffset());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", LocationResult.class.getSimpleName() + "[", "]")
        .add("type='" + type + "'")
        .add("id='" + id + "'")
        .add("providerUrl='" + providerUrl + "'")
        .add("coordinates=" + coordinates)
        .add("zoomLevel=" + zoomLevel)
        .add("thumbnail=" + thumbnail)
        .add("postalAddress=" + postalAddress)
        .add("openingHours=" + openingHours)
        .add("contact=" + contact)
        .add("priceRange='" + priceRange + "'")
        .add("rating=" + rating)
        .add("distance=" + distance)
        .add("profiles=" + profiles)
        .add("reviews=" + reviews)
        .add("pictures=" + pictures)
        .add("action=" + action)
        .add("servesCuisine=" + servesCuisine)
        .add("categories=" + categories)
        .add("iconCategory='" + iconCategory + "'")
        .add("timezone='" + timezone + "'")
        .add("timezoneOffset='" + timezoneOffset + "'")
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
