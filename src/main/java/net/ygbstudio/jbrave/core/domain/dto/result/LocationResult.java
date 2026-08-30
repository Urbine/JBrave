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

  /**
   * Creates a new location result from the given builder.
   *
   * @param builder the builder carrying the location fields
   */
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

  /** Builder for {@link LocationResult} instances. */
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

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the location type.
     *
     * @param type the location type
     * @return this builder
     */
    public Builder type(String type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the location identifier.
     *
     * @param id the location id
     * @return this builder
     */
    public Builder id(String id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the provider URL.
     *
     * @param providerUrl the provider URL
     * @return this builder
     */
    public Builder providerUrl(String providerUrl) {
      this.providerUrl = providerUrl;
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
     * Sets the postal address.
     *
     * @param postalAddress the postal address
     * @return this builder
     */
    public Builder postalAddress(PostalAddress postalAddress) {
      this.postalAddress = postalAddress;
      return this;
    }

    /**
     * Sets the opening hours.
     *
     * @param openingHours the opening hours
     * @return this builder
     */
    public Builder openingHours(OpeningHours openingHours) {
      this.openingHours = openingHours;
      return this;
    }

    /**
     * Sets the contact information.
     *
     * @param contact the contact
     * @return this builder
     */
    public Builder contact(Contact contact) {
      this.contact = contact;
      return this;
    }

    /**
     * Sets the price range.
     *
     * @param priceRange the price range
     * @return this builder
     */
    public Builder priceRange(String priceRange) {
      this.priceRange = priceRange;
      return this;
    }

    /**
     * Sets the rating.
     *
     * @param rating the rating
     * @return this builder
     */
    public Builder rating(Rating rating) {
      this.rating = rating;
      return this;
    }

    /**
     * Sets the distance.
     *
     * @param distance the distance
     * @return this builder
     */
    public Builder distance(Unit distance) {
      this.distance = distance;
      return this;
    }

    /**
     * Sets the profiles.
     *
     * @param profiles the profiles
     * @return this builder
     */
    public Builder profiles(List<DataProvider> profiles) {
      this.profiles = profiles;
      return this;
    }

    /**
     * Sets the reviews.
     *
     * @param reviews the reviews
     * @return this builder
     */
    public Builder reviews(Reviews reviews) {
      this.reviews = reviews;
      return this;
    }

    /**
     * Sets the pictures.
     *
     * @param pictures the pictures
     * @return this builder
     */
    public Builder pictures(PictureResults pictures) {
      this.pictures = pictures;
      return this;
    }

    /**
     * Sets the action.
     *
     * @param action the action
     * @return this builder
     */
    public Builder action(Action action) {
      this.action = action;
      return this;
    }

    /**
     * Sets the served cuisines.
     *
     * @param servesCuisine the served cuisines
     * @return this builder
     */
    public Builder servesCuisine(List<String> servesCuisine) {
      this.servesCuisine = servesCuisine;
      return this;
    }

    /**
     * Sets the categories.
     *
     * @param categories the categories
     * @return this builder
     */
    public Builder categories(List<String> categories) {
      this.categories = categories;
      return this;
    }

    /**
     * Sets the icon category.
     *
     * @param iconCategory the icon category
     * @return this builder
     */
    public Builder iconCategory(String iconCategory) {
      this.iconCategory = iconCategory;
      return this;
    }

    /**
     * Sets the timezone.
     *
     * @param timezone the timezone
     * @return this builder
     */
    public Builder timezone(String timezone) {
      this.timezone = timezone;
      return this;
    }

    /**
     * Sets the timezone offset.
     *
     * @param timezoneOffset the timezone offset
     * @return this builder
     */
    public Builder timezoneOffset(String timezoneOffset) {
      this.timezoneOffset = timezoneOffset;
      return this;
    }

    /**
     * Builds a new {@link LocationResult} from the current builder state.
     *
     * @return a new {@code LocationResult} instance
     */
    @Override
    @Contract(" -> new")
    public @NotNull LocationResult build() {
      return new LocationResult(this);
    }
  }

  /**
   * Returns the location type.
   *
   * @return the location type
   */
  public String getType() {
    return type;
  }

  /**
   * Returns the location identifier.
   *
   * @return the location id
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the provider URL.
   *
   * @return the provider URL
   */
  public String getProviderUrl() {
    return providerUrl;
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

  /**
   * Returns the thumbnail.
   *
   * @return the thumbnail
   */
  public Thumbnail getThumbnail() {
    return thumbnail;
  }

  /**
   * Returns the postal address.
   *
   * @return the postal address
   */
  public PostalAddress getPostalAddress() {
    return postalAddress;
  }

  /**
   * Returns the opening hours.
   *
   * @return the opening hours
   */
  public OpeningHours getOpeningHours() {
    return openingHours;
  }

  /**
   * Returns the contact information.
   *
   * @return the contact
   */
  public Contact getContact() {
    return contact;
  }

  /**
   * Returns the price range.
   *
   * @return the price range
   */
  public String getPriceRange() {
    return priceRange;
  }

  /**
   * Returns the rating.
   *
   * @return the rating
   */
  public Rating getRating() {
    return rating;
  }

  /**
   * Returns the distance.
   *
   * @return the distance
   */
  public Unit getDistance() {
    return distance;
  }

  /**
   * Returns the profiles.
   *
   * @return the profiles
   */
  public List<DataProvider> getProfiles() {
    return profiles;
  }

  /**
   * Returns the reviews.
   *
   * @return the reviews
   */
  public Reviews getReviews() {
    return reviews;
  }

  /**
   * Returns the pictures.
   *
   * @return the pictures
   */
  public PictureResults getPictures() {
    return pictures;
  }

  /**
   * Returns the action.
   *
   * @return the action
   */
  public Action getAction() {
    return action;
  }

  /**
   * Returns the served cuisines.
   *
   * @return the served cuisines
   */
  public List<String> getServesCuisine() {
    return servesCuisine;
  }

  /**
   * Returns the categories.
   *
   * @return the categories
   */
  public List<String> getCategories() {
    return categories;
  }

  /**
   * Returns the icon category.
   *
   * @return the icon category
   */
  public String getIconCategory() {
    return iconCategory;
  }

  /**
   * Returns the timezone.
   *
   * @return the timezone
   */
  public String getTimezone() {
    return timezone;
  }

  /**
   * Returns the timezone offset.
   *
   * @return the timezone offset
   */
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
