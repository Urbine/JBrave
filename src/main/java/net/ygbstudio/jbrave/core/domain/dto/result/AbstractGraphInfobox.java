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
  protected final String type;
  protected final Integer position;
  protected final String label;
  protected final String category;
  protected final String longDesc;
  protected final Thumbnail thumbnail;
  protected final List<List<String>> attributes;
  protected final List<GraphInfoboxProfile> profiles;
  protected final String websiteUrl;
  protected final List<Rating> ratings;
  protected final List<DataProvider> providers;
  protected final Unit distance;
  protected final List<Thumbnail> images;
  protected final MovieData movie;

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

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder position(Integer position) {
      this.position = position;
      return this;
    }

    public Builder label(String label) {
      this.label = label;
      return this;
    }

    public Builder category(String category) {
      this.category = category;
      return this;
    }

    public Builder longDesc(String longDesc) {
      this.longDesc = longDesc;
      return this;
    }

    public Builder thumbnail(Thumbnail thumbnail) {
      this.thumbnail = thumbnail;
      return this;
    }

    public Builder attributes(List<List<String>> attributes) {
      this.attributes = attributes;
      return this;
    }

    public Builder profiles(List<GraphInfoboxProfile> profiles) {
      this.profiles = profiles;
      return this;
    }

    public Builder websiteUrl(String websiteUrl) {
      this.websiteUrl = websiteUrl;
      return this;
    }

    public Builder ratings(List<Rating> ratings) {
      this.ratings = ratings;
      return this;
    }

    public Builder providers(List<DataProvider> providers) {
      this.providers = providers;
      return this;
    }

    public Builder distance(Unit distance) {
      this.distance = distance;
      return this;
    }

    public Builder images(List<Thumbnail> images) {
      this.images = images;
      return this;
    }

    public Builder movie(MovieData movie) {
      this.movie = movie;
      return this;
    }

    @Override
    public AbstractGraphInfobox build() {
      return new AbstractGraphInfobox(this);
    }
  }

  public String getType() {
    return type;
  }

  public Integer getPosition() {
    return position;
  }

  public String getLabel() {
    return label;
  }

  public String getCategory() {
    return category;
  }

  public String getLongDesc() {
    return longDesc;
  }

  public Thumbnail getThumbnail() {
    return thumbnail;
  }

  public List<List<String>> getAttributes() {
    return attributes;
  }

  public List<GraphInfoboxProfile> getProfiles() {
    return profiles;
  }

  public String getWebsiteUrl() {
    return websiteUrl;
  }

  public List<Rating> getRatings() {
    return ratings;
  }

  public List<DataProvider> getProviders() {
    return providers;
  }

  public Unit getDistance() {
    return distance;
  }

  public List<Thumbnail> getImages() {
    return images;
  }

  public MovieData getMovie() {
    return movie;
  }
}
