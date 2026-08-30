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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import net.ygbstudio.jbrave.core.domain.dto.result.profile.DataProvider;
import net.ygbstudio.jbrave.core.domain.dto.result.profile.GraphInfoboxProfile;
import net.ygbstudio.jbrave.core.domain.dto.web.MovieData;
import net.ygbstudio.jbrave.core.domain.dto.web.Rating;
import net.ygbstudio.jbrave.core.domain.dto.web.Unit;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents an abstract base class for graph infoboxes in search results. This class is part of
 * the Brave Search API and serves as a base for various types of infoboxes that provide structured
 * information in search results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "subtype")
@JsonSubTypes({
  @JsonSubTypes.Type(EntityInfobox.class),
  @JsonSubTypes.Type(GenericInfobox.class),
  @JsonSubTypes.Type(InfoboxWithLocation.class),
  @JsonSubTypes.Type(InfoboxPlace.class),
  @JsonSubTypes.Type(QAInfobox.class),
})
@JsonDeserialize(builder = AbstractGraphInfobox.Builder.class)
public sealed class AbstractGraphInfobox extends Result
    permits EntityInfobox, GenericInfobox, InfoboxPlace, InfoboxWithLocation, QAInfobox {
  /** The infobox type. */
  protected final String type;

  /** The infobox position. */
  protected final Integer position;

  /** The infobox label. */
  protected final String label;

  /** The infobox category. */
  protected final String category;

  /** The long description of the infobox. */
  protected final String longDesc;

  /** The infobox thumbnail. */
  protected final Thumbnail thumbnail;

  /** The infobox attributes as a list of key-value pairs. */
  protected final List<List<String>> attributes;

  /** The graph infobox profiles. */
  protected final List<GraphInfoboxProfile> profiles;

  /** The website URL associated with the infobox. */
  protected final String websiteUrl;

  /** The infobox ratings. */
  protected final List<Rating> ratings;

  /** The infobox data providers. */
  protected final List<DataProvider> providers;

  /** The distance associated with the infobox. */
  protected final Unit distance;

  /** The infobox images. */
  protected final List<Thumbnail> images;

  /** The movie data associated with the infobox. */
  protected final MovieData movie;

  /**
   * Creates a new graph infobox from the given builder.
   *
   * @param builder the builder carrying the infobox fields
   */
  public AbstractGraphInfobox(Builder builder) {
    super(builder);
    this.type = builder.type;
    this.position = builder.position;
    this.label = builder.label;
    this.category = builder.category;
    this.longDesc = builder.longDesc;
    this.thumbnail = builder.thumbnail;
    this.attributes = builder.attributes;
    this.profiles = builder.profiles;
    this.websiteUrl = builder.websiteUrl;
    this.ratings = builder.ratings;
    this.providers = builder.providers;
    this.distance = builder.distance;
    this.images = builder.images;
    this.movie = builder.movie;
  }

  /** Builder for {@link AbstractGraphInfobox} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder extends Result.Builder {
    private String type;
    private Integer position;
    private String label;
    private String category;
    private String longDesc;
    private Thumbnail thumbnail;
    private List<List<String>> attributes;
    private List<GraphInfoboxProfile> profiles;
    private String websiteUrl;
    private List<Rating> ratings;
    private List<DataProvider> providers;
    private Unit distance;
    private List<Thumbnail> images;
    private MovieData movie;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the infobox type.
     *
     * @param type the infobox type
     * @return this builder
     */
    public Builder type(String type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the infobox position.
     *
     * @param position the infobox position
     * @return this builder
     */
    public Builder position(Integer position) {
      this.position = position;
      return this;
    }

    /**
     * Sets the infobox label.
     *
     * @param label the infobox label
     * @return this builder
     */
    public Builder label(String label) {
      this.label = label;
      return this;
    }

    /**
     * Sets the infobox category.
     *
     * @param category the infobox category
     * @return this builder
     */
    public Builder category(String category) {
      this.category = category;
      return this;
    }

    /**
     * Sets the long description of the infobox.
     *
     * @param longDesc the long description
     * @return this builder
     */
    public Builder longDesc(String longDesc) {
      this.longDesc = longDesc;
      return this;
    }

    /**
     * Sets the infobox thumbnail.
     *
     * @param thumbnail the infobox thumbnail
     * @return this builder
     */
    public Builder thumbnail(Thumbnail thumbnail) {
      this.thumbnail = thumbnail;
      return this;
    }

    /**
     * Sets the infobox attributes.
     *
     * @param attributes the infobox attributes as a list of key-value pairs
     * @return this builder
     */
    public Builder attributes(List<List<String>> attributes) {
      this.attributes = attributes;
      return this;
    }

    /**
     * Sets the graph infobox profiles.
     *
     * @param profiles the graph infobox profiles
     * @return this builder
     */
    public Builder profiles(List<GraphInfoboxProfile> profiles) {
      this.profiles = profiles;
      return this;
    }

    /**
     * Sets the website URL associated with the infobox.
     *
     * @param websiteUrl the website URL
     * @return this builder
     */
    public Builder websiteUrl(String websiteUrl) {
      this.websiteUrl = websiteUrl;
      return this;
    }

    /**
     * Sets the infobox ratings.
     *
     * @param ratings the infobox ratings
     * @return this builder
     */
    public Builder ratings(List<Rating> ratings) {
      this.ratings = ratings;
      return this;
    }

    /**
     * Sets the infobox data providers.
     *
     * @param providers the infobox data providers
     * @return this builder
     */
    public Builder providers(List<DataProvider> providers) {
      this.providers = providers;
      return this;
    }

    /**
     * Sets the distance associated with the infobox.
     *
     * @param distance the distance
     * @return this builder
     */
    public Builder distance(Unit distance) {
      this.distance = distance;
      return this;
    }

    /**
     * Sets the infobox images.
     *
     * @param images the infobox images
     * @return this builder
     */
    public Builder images(List<Thumbnail> images) {
      this.images = images;
      return this;
    }

    /**
     * Sets the movie data associated with the infobox.
     *
     * @param movie the movie data
     * @return this builder
     */
    public Builder movie(MovieData movie) {
      this.movie = movie;
      return this;
    }

    /**
     * Builds a new {@link AbstractGraphInfobox} from the current builder state.
     *
     * @return a new {@code AbstractGraphInfobox} instance
     */
    @Override
    public AbstractGraphInfobox build() {
      return new AbstractGraphInfobox(this);
    }
  }

  /**
   * Returns the infobox type.
   *
   * @return the infobox type
   */
  public String getType() {
    return type;
  }

  /**
   * Returns the infobox position.
   *
   * @return the infobox position
   */
  public Integer getPosition() {
    return position;
  }

  /**
   * Returns the infobox label.
   *
   * @return the infobox label
   */
  public String getLabel() {
    return label;
  }

  /**
   * Returns the infobox category.
   *
   * @return the infobox category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Returns the long description of the infobox.
   *
   * @return the long description
   */
  public String getLongDesc() {
    return longDesc;
  }

  /**
   * Returns the infobox thumbnail.
   *
   * @return the infobox thumbnail
   */
  public Thumbnail getThumbnail() {
    return thumbnail;
  }

  /**
   * Returns the infobox attributes as a list of key-value pairs.
   *
   * @return the infobox attributes
   */
  public List<List<String>> getAttributes() {
    return attributes;
  }

  /**
   * Returns the graph infobox profiles.
   *
   * @return the graph infobox profiles
   */
  public List<GraphInfoboxProfile> getProfiles() {
    return profiles;
  }

  /**
   * Returns the website URL associated with the infobox.
   *
   * @return the website URL
   */
  public String getWebsiteUrl() {
    return websiteUrl;
  }

  /**
   * Returns the infobox ratings.
   *
   * @return the infobox ratings
   */
  public List<Rating> getRatings() {
    return ratings;
  }

  /**
   * Returns the infobox data providers.
   *
   * @return the infobox data providers
   */
  public List<DataProvider> getProviders() {
    return providers;
  }

  /**
   * Returns the distance associated with the infobox.
   *
   * @return the distance
   */
  public Unit getDistance() {
    return distance;
  }

  /**
   * Returns the infobox images.
   *
   * @return the infobox images
   */
  public List<Thumbnail> getImages() {
    return images;
  }

  /**
   * Returns the movie data associated with the infobox.
   *
   * @return the movie data
   */
  public MovieData getMovie() {
    return movie;
  }
}
