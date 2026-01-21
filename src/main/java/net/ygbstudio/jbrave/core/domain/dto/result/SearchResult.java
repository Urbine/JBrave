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
import net.ygbstudio.jbrave.core.domain.dto.MetaUrl;
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import net.ygbstudio.jbrave.core.domain.dto.result.search.FAQ;
import net.ygbstudio.jbrave.core.domain.dto.result.thing.Organization;
import net.ygbstudio.jbrave.core.domain.dto.web.Article;
import net.ygbstudio.jbrave.core.domain.dto.web.Book;
import net.ygbstudio.jbrave.core.domain.dto.web.CreativeWork;
import net.ygbstudio.jbrave.core.domain.dto.web.DeepResult;
import net.ygbstudio.jbrave.core.domain.dto.web.MovieData;
import net.ygbstudio.jbrave.core.domain.dto.web.MusicRecording;
import net.ygbstudio.jbrave.core.domain.dto.web.ProductPair;
import net.ygbstudio.jbrave.core.domain.dto.web.QAPage;
import net.ygbstudio.jbrave.core.domain.dto.web.Rating;
import net.ygbstudio.jbrave.core.domain.dto.web.Recipe;
import net.ygbstudio.jbrave.core.domain.dto.web.Review;
import net.ygbstudio.jbrave.core.domain.dto.web.Software;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a search result in search results. This class provides structured information about
 * search results in the Brave Search API results.
 */
@JsonDeserialize(builder = SearchResult.Builder.class)
public sealed class SearchResult extends Result permits DiscussionResult {
  protected final String type;
  protected final String subtype;
  protected final Boolean isLive;
  protected final DeepResult deepResults;
  protected final List<List<String>> schemas;
  protected final MetaUrl metaUrl;
  protected final Thumbnail thumbnail;
  protected final String age;
  protected final LocationResult location;
  protected final VideoData video;
  protected final MovieData movie;
  protected final FAQ faq;
  protected final QAPage qa;
  protected final Book book;
  protected final Rating rating;
  protected final Article article;
  protected final ProductPair product;
  protected final List<ProductPair> productCluster;
  protected final String clusterType;
  protected final List<Result> cluster;
  protected final CreativeWork creativeWork;
  protected final MusicRecording musicRecording;
  protected final Review review;
  protected final Software software;
  protected final Recipe recipe;
  protected final Organization organization;
  protected final String contentType;
  protected final List<String> extraSnippets;

  public SearchResult(@NotNull Builder builder) {
    super(builder);
    this.type = builder.type;
    this.subtype = builder.subtype;
    this.isLive = builder.isLive;
    this.deepResults = builder.deepResults;
    this.schemas = builder.schemas;
    this.metaUrl = builder.metaUrl;
    this.thumbnail = builder.thumbnail;
    this.age = builder.age;
    this.location = builder.location;
    this.video = builder.video;
    this.movie = builder.movie;
    this.faq = builder.faq;
    this.qa = builder.qa;
    this.book = builder.book;
    this.rating = builder.rating;
    this.article = builder.article;
    this.product = builder.product;
    this.productCluster = builder.productCluster;
    this.clusterType = builder.clusterType;
    this.cluster = builder.cluster;
    this.creativeWork = builder.creativeWork;
    this.musicRecording = builder.musicRecording;
    this.review = builder.review;
    this.software = builder.software;
    this.recipe = builder.recipe;
    this.organization = builder.organization;
    this.contentType = builder.contentType;
    this.extraSnippets = builder.extraSnippets;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder extends Result.Builder {
    protected String type;
    protected String subtype;
    protected Boolean isLive;
    protected DeepResult deepResults;
    protected List<List<String>> schemas;
    protected MetaUrl metaUrl;
    protected Thumbnail thumbnail;
    protected String age;
    protected LocationResult location;
    protected VideoData video;
    protected MovieData movie;
    protected FAQ faq;
    protected QAPage qa;
    protected Book book;
    protected Rating rating;
    protected Article article;
    protected ProductPair product;
    protected List<ProductPair> productCluster;
    protected String clusterType;
    protected List<Result> cluster;
    protected CreativeWork creativeWork;
    protected MusicRecording musicRecording;
    protected Review review;
    protected Software software;
    protected Recipe recipe;
    protected Organization organization;
    protected String contentType;
    protected List<String> extraSnippets;

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder subtype(String subtype) {
      this.subtype = subtype;
      return this;
    }

    public Builder isLive(Boolean isLive) {
      this.isLive = isLive;
      return this;
    }

    public Builder deepResults(DeepResult deepResults) {
      this.deepResults = deepResults;
      return this;
    }

    public Builder schemas(List<List<String>> schemas) {
      this.schemas = schemas;
      return this;
    }

    public Builder metaUrl(MetaUrl metaUrl) {
      this.metaUrl = metaUrl;
      return this;
    }

    public Builder thumbnail(Thumbnail thumbnail) {
      this.thumbnail = thumbnail;
      return this;
    }

    public Builder age(String age) {
      this.age = age;
      return this;
    }

    public Builder location(LocationResult location) {
      this.location = location;
      return this;
    }

    public Builder video(VideoData video) {
      this.video = video;
      return this;
    }

    public Builder movie(MovieData movie) {
      this.movie = movie;
      return this;
    }

    public Builder faq(FAQ faq) {
      this.faq = faq;
      return this;
    }

    public Builder qa(QAPage qa) {
      this.qa = qa;
      return this;
    }

    public Builder book(Book book) {
      this.book = book;
      return this;
    }

    public Builder rating(Rating rating) {
      this.rating = rating;
      return this;
    }

    public Builder article(Article article) {
      this.article = article;
      return this;
    }

    public Builder product(ProductPair product) {
      this.product = product;
      return this;
    }

    public Builder productCluster(List<ProductPair> productCluster) {
      this.productCluster = productCluster;
      return this;
    }

    public Builder clusterType(String clusterType) {
      this.clusterType = clusterType;
      return this;
    }

    public Builder cluster(List<Result> cluster) {
      this.cluster = cluster;
      return this;
    }

    public Builder creativeWork(CreativeWork creativeWork) {
      this.creativeWork = creativeWork;
      return this;
    }

    public Builder musicRecording(MusicRecording musicRecording) {
      this.musicRecording = musicRecording;
      return this;
    }

    public Builder review(Review review) {
      this.review = review;
      return this;
    }

    public Builder software(Software software) {
      this.software = software;
      return this;
    }

    public Builder recipe(Recipe recipe) {
      this.recipe = recipe;
      return this;
    }

    public Builder organization(Organization organization) {
      this.organization = organization;
      return this;
    }

    public Builder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    public Builder extraSnippets(List<String> extraSnippets) {
      this.extraSnippets = extraSnippets;
      return this;
    }

    @Override
    public SearchResult build() {
      return new SearchResult(this);
    }
  }

  public String type() {
    return type;
  }

  public String subtype() {
    return subtype;
  }

  public Boolean isLive() {
    return isLive;
  }

  public DeepResult deepResults() {
    return deepResults;
  }

  public List<List<String>> schemas() {
    return schemas;
  }

  public MetaUrl metaUrl() {
    return metaUrl;
  }

  public Thumbnail thumbnail() {
    return thumbnail;
  }

  public String age() {
    return age;
  }

  public LocationResult location() {
    return location;
  }

  public VideoData video() {
    return video;
  }

  public MovieData movie() {
    return movie;
  }

  public FAQ faq() {
    return faq;
  }

  public QAPage qa() {
    return qa;
  }

  public Book book() {
    return book;
  }

  public Rating rating() {
    return rating;
  }

  public Article article() {
    return article;
  }

  public ProductPair product() {
    return product;
  }

  public List<ProductPair> productCluster() {
    return productCluster;
  }

  public String clusterType() {
    return clusterType;
  }

  public List<Result> cluster() {
    return cluster;
  }

  public CreativeWork creativeWork() {
    return creativeWork;
  }

  public MusicRecording musicRecording() {
    return musicRecording;
  }

  public Review review() {
    return review;
  }

  public Software software() {
    return software;
  }

  public Recipe recipe() {
    return recipe;
  }

  public Organization organization() {
    return organization;
  }

  public String contentType() {
    return contentType;
  }

  public List<String> extraSnippets() {
    return extraSnippets;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    SearchResult that = (SearchResult) o;
    return Objects.equals(type, that.type)
        && Objects.equals(subtype, that.subtype)
        && Objects.equals(isLive, that.isLive)
        && Objects.equals(deepResults, that.deepResults)
        && Objects.equals(schemas, that.schemas)
        && Objects.equals(metaUrl, that.metaUrl)
        && Objects.equals(thumbnail, that.thumbnail)
        && Objects.equals(age, that.age)
        && Objects.equals(location, that.location)
        && Objects.equals(video, that.video)
        && Objects.equals(movie, that.movie)
        && Objects.equals(faq, that.faq)
        && Objects.equals(qa, that.qa)
        && Objects.equals(book, that.book)
        && Objects.equals(rating, that.rating)
        && Objects.equals(article, that.article)
        && Objects.equals(product, that.product)
        && Objects.equals(productCluster, that.productCluster)
        && Objects.equals(clusterType, that.clusterType)
        && Objects.equals(cluster, that.cluster)
        && Objects.equals(creativeWork, that.creativeWork)
        && Objects.equals(musicRecording, that.musicRecording)
        && Objects.equals(review, that.review)
        && Objects.equals(software, that.software)
        && Objects.equals(recipe, that.recipe)
        && Objects.equals(organization, that.organization)
        && Objects.equals(contentType, that.contentType)
        && Objects.equals(extraSnippets, that.extraSnippets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        type,
        subtype,
        isLive,
        deepResults,
        schemas,
        metaUrl,
        thumbnail,
        age,
        location,
        video,
        movie,
        faq,
        qa,
        book,
        rating,
        article,
        product,
        productCluster,
        clusterType,
        cluster,
        creativeWork,
        musicRecording,
        review,
        software,
        recipe,
        organization,
        contentType,
        extraSnippets);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SearchResult.class.getSimpleName() + "[", "]")
        .add("type='" + type + "'")
        .add("subtype='" + subtype + "'")
        .add("isLive=" + isLive)
        .add("deepResults=" + deepResults)
        .add("schemas=" + schemas)
        .add("metaUrl=" + metaUrl)
        .add("thumbnail=" + thumbnail)
        .add("age='" + age + "'")
        .add("location=" + location)
        .add("video=" + video)
        .add("movie=" + movie)
        .add("faq=" + faq)
        .add("qa=" + qa)
        .add("book=" + book)
        .add("rating=" + rating)
        .add("article=" + article)
        .add("product=" + product)
        .add("productCluster=" + productCluster)
        .add("clusterType='" + clusterType + "'")
        .add("cluster=" + cluster)
        .add("creativeWork=" + creativeWork)
        .add("musicRecording=" + musicRecording)
        .add("review=" + review)
        .add("software=" + software)
        .add("recipe=" + recipe)
        .add("organization=" + organization)
        .add("contentType='" + contentType + "'")
        .add("extraSnippets=" + extraSnippets)
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
