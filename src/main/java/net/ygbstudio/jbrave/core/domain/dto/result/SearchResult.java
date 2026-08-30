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
  /** The result type. */
  protected final String type;

  /** The result subtype. */
  protected final String subtype;

  /** Whether the result is a live stream. */
  protected final Boolean isLive;

  /** Deep results associated with this result. */
  protected final DeepResult deepResults;

  /** The schema definitions for this result. */
  protected final List<List<String>> schemas;

  /** The meta URL information for this result. */
  protected final MetaUrl metaUrl;

  /** The thumbnail for this result. */
  protected final Thumbnail thumbnail;

  /** The age of the result content. */
  protected final String age;

  /** The location associated with this result. */
  protected final LocationResult location;

  /** The video data for this result. */
  protected final VideoData video;

  /** The movie data for this result. */
  protected final MovieData movie;

  /** The FAQ data for this result. */
  protected final FAQ faq;

  /** The Q&amp;A page data for this result. */
  protected final QAPage qa;

  /** The book data for this result. */
  protected final Book book;

  /** The rating for this result. */
  protected final Rating rating;

  /** The article data for this result. */
  protected final Article article;

  /** The primary product for this result. */
  protected final ProductPair product;

  /** The cluster of related products. */
  protected final List<ProductPair> productCluster;

  /** The type of cluster this result belongs to. */
  protected final String clusterType;

  /** The cluster of results this result belongs to. */
  protected final List<Result> cluster;

  /** The creative work data for this result. */
  protected final CreativeWork creativeWork;

  /** The music recording data for this result. */
  protected final MusicRecording musicRecording;

  /** The review data for this result. */
  protected final Review review;

  /** The software data for this result. */
  protected final Software software;

  /** The recipe data for this result. */
  protected final Recipe recipe;

  /** The organization data for this result. */
  protected final Organization organization;

  /** The content type of this result. */
  protected final String contentType;

  /** Extra snippets for this result. */
  protected final List<String> extraSnippets;

  /**
   * Creates a new search result from the given builder.
   *
   * @param builder the builder carrying the result fields
   */
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

  /** Builder for {@link SearchResult} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder extends Result.Builder {
    /** The result type. */
    protected String type;

    /** The result subtype. */
    protected String subtype;

    /** Whether the result is a live stream. */
    protected Boolean isLive;

    /** Deep results associated with this result. */
    protected DeepResult deepResults;

    /** The schema definitions for this result. */
    protected List<List<String>> schemas;

    /** The meta URL information for this result. */
    protected MetaUrl metaUrl;

    /** The thumbnail for this result. */
    protected Thumbnail thumbnail;

    /** The age of the result content. */
    protected String age;

    /** The location associated with this result. */
    protected LocationResult location;

    /** The video data for this result. */
    protected VideoData video;

    /** The movie data for this result. */
    protected MovieData movie;

    /** The FAQ data for this result. */
    protected FAQ faq;

    /** The Q&amp;A page data for this result. */
    protected QAPage qa;

    /** The book data for this result. */
    protected Book book;

    /** The rating for this result. */
    protected Rating rating;

    /** The article data for this result. */
    protected Article article;

    /** The primary product for this result. */
    protected ProductPair product;

    /** The cluster of related products. */
    protected List<ProductPair> productCluster;

    /** The type of cluster this result belongs to. */
    protected String clusterType;

    /** The cluster of results this result belongs to. */
    protected List<Result> cluster;

    /** The creative work data for this result. */
    protected CreativeWork creativeWork;

    /** The music recording data for this result. */
    protected MusicRecording musicRecording;

    /** The review data for this result. */
    protected Review review;

    /** The software data for this result. */
    protected Software software;

    /** The recipe data for this result. */
    protected Recipe recipe;

    /** The organization data for this result. */
    protected Organization organization;

    /** The content type of this result. */
    protected String contentType;

    /** Extra snippets for this result. */
    protected List<String> extraSnippets;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the result type.
     *
     * @param type the result type
     * @return this builder
     */
    public Builder type(String type) {
      this.type = type;
      return this;
    }

    /**
     * Sets the result subtype.
     *
     * @param subtype the result subtype
     * @return this builder
     */
    public Builder subtype(String subtype) {
      this.subtype = subtype;
      return this;
    }

    /**
     * Sets whether the result is a live stream.
     *
     * @param isLive {@code true} if the result is live
     * @return this builder
     */
    public Builder isLive(Boolean isLive) {
      this.isLive = isLive;
      return this;
    }

    /**
     * Sets the deep results.
     *
     * @param deepResults the deep results
     * @return this builder
     */
    public Builder deepResults(DeepResult deepResults) {
      this.deepResults = deepResults;
      return this;
    }

    /**
     * Sets the schema definitions.
     *
     * @param schemas the schemas
     * @return this builder
     */
    public Builder schemas(List<List<String>> schemas) {
      this.schemas = schemas;
      return this;
    }

    /**
     * Sets the meta URL.
     *
     * @param metaUrl the meta URL
     * @return this builder
     */
    public Builder metaUrl(MetaUrl metaUrl) {
      this.metaUrl = metaUrl;
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
     * Sets the age of the result.
     *
     * @param age the age
     * @return this builder
     */
    public Builder age(String age) {
      this.age = age;
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
     * Sets the video data.
     *
     * @param video the video data
     * @return this builder
     */
    public Builder video(VideoData video) {
      this.video = video;
      return this;
    }

    /**
     * Sets the movie data.
     *
     * @param movie the movie data
     * @return this builder
     */
    public Builder movie(MovieData movie) {
      this.movie = movie;
      return this;
    }

    /**
     * Sets the FAQ data.
     *
     * @param faq the FAQ data
     * @return this builder
     */
    public Builder faq(FAQ faq) {
      this.faq = faq;
      return this;
    }

    /**
     * Sets the Q&amp;A page data.
     *
     * @param qa the Q&amp;A page data
     * @return this builder
     */
    public Builder qa(QAPage qa) {
      this.qa = qa;
      return this;
    }

    /**
     * Sets the book data.
     *
     * @param book the book data
     * @return this builder
     */
    public Builder book(Book book) {
      this.book = book;
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
     * Sets the article data.
     *
     * @param article the article data
     * @return this builder
     */
    public Builder article(Article article) {
      this.article = article;
      return this;
    }

    /**
     * Sets the primary product.
     *
     * @param product the product
     * @return this builder
     */
    public Builder product(ProductPair product) {
      this.product = product;
      return this;
    }

    /**
     * Sets the product cluster.
     *
     * @param productCluster the product cluster
     * @return this builder
     */
    public Builder productCluster(List<ProductPair> productCluster) {
      this.productCluster = productCluster;
      return this;
    }

    /**
     * Sets the cluster type.
     *
     * @param clusterType the cluster type
     * @return this builder
     */
    public Builder clusterType(String clusterType) {
      this.clusterType = clusterType;
      return this;
    }

    /**
     * Sets the result cluster.
     *
     * @param cluster the cluster
     * @return this builder
     */
    public Builder cluster(List<Result> cluster) {
      this.cluster = cluster;
      return this;
    }

    /**
     * Sets the creative work data.
     *
     * @param creativeWork the creative work data
     * @return this builder
     */
    public Builder creativeWork(CreativeWork creativeWork) {
      this.creativeWork = creativeWork;
      return this;
    }

    /**
     * Sets the music recording data.
     *
     * @param musicRecording the music recording data
     * @return this builder
     */
    public Builder musicRecording(MusicRecording musicRecording) {
      this.musicRecording = musicRecording;
      return this;
    }

    /**
     * Sets the review data.
     *
     * @param review the review data
     * @return this builder
     */
    public Builder review(Review review) {
      this.review = review;
      return this;
    }

    /**
     * Sets the software data.
     *
     * @param software the software data
     * @return this builder
     */
    public Builder software(Software software) {
      this.software = software;
      return this;
    }

    /**
     * Sets the recipe data.
     *
     * @param recipe the recipe data
     * @return this builder
     */
    public Builder recipe(Recipe recipe) {
      this.recipe = recipe;
      return this;
    }

    /**
     * Sets the organization data.
     *
     * @param organization the organization data
     * @return this builder
     */
    public Builder organization(Organization organization) {
      this.organization = organization;
      return this;
    }

    /**
     * Sets the content type.
     *
     * @param contentType the content type
     * @return this builder
     */
    public Builder contentType(String contentType) {
      this.contentType = contentType;
      return this;
    }

    /**
     * Sets the extra snippets.
     *
     * @param extraSnippets the extra snippets
     * @return this builder
     */
    public Builder extraSnippets(List<String> extraSnippets) {
      this.extraSnippets = extraSnippets;
      return this;
    }

    /**
     * Builds a new {@link SearchResult} from the current builder state.
     *
     * @return a new {@code SearchResult} instance
     */
    @Override
    public SearchResult build() {
      return new SearchResult(this);
    }
  }

  /**
   * Returns the result type.
   *
   * @return the result type
   */
  public String type() {
    return type;
  }

  /**
   * Returns the result subtype.
   *
   * @return the result subtype
   */
  public String subtype() {
    return subtype;
  }

  /**
   * Returns whether the result is a live stream.
   *
   * @return whether the result is live
   */
  public Boolean isLive() {
    return isLive;
  }

  /**
   * Returns the deep results.
   *
   * @return the deep results
   */
  public DeepResult deepResults() {
    return deepResults;
  }

  /**
   * Returns the schema definitions.
   *
   * @return the schemas
   */
  public List<List<String>> schemas() {
    return schemas;
  }

  /**
   * Returns the meta URL.
   *
   * @return the meta URL
   */
  public MetaUrl metaUrl() {
    return metaUrl;
  }

  /**
   * Returns the thumbnail.
   *
   * @return the thumbnail
   */
  public Thumbnail thumbnail() {
    return thumbnail;
  }

  /**
   * Returns the age of the result.
   *
   * @return the age
   */
  public String age() {
    return age;
  }

  /**
   * Returns the location.
   *
   * @return the location
   */
  public LocationResult location() {
    return location;
  }

  /**
   * Returns the video data.
   *
   * @return the video data
   */
  public VideoData video() {
    return video;
  }

  /**
   * Returns the movie data.
   *
   * @return the movie data
   */
  public MovieData movie() {
    return movie;
  }

  /**
   * Returns the FAQ data.
   *
   * @return the FAQ data
   */
  public FAQ faq() {
    return faq;
  }

  /**
   * Returns the Q&amp;A page data.
   *
   * @return the Q&amp;A page data
   */
  public QAPage qa() {
    return qa;
  }

  /**
   * Returns the book data.
   *
   * @return the book data
   */
  public Book book() {
    return book;
  }

  /**
   * Returns the rating.
   *
   * @return the rating
   */
  public Rating rating() {
    return rating;
  }

  /**
   * Returns the article data.
   *
   * @return the article data
   */
  public Article article() {
    return article;
  }

  /**
   * Returns the primary product.
   *
   * @return the product
   */
  public ProductPair product() {
    return product;
  }

  /**
   * Returns the product cluster.
   *
   * @return the product cluster
   */
  public List<ProductPair> productCluster() {
    return productCluster;
  }

  /**
   * Returns the cluster type.
   *
   * @return the cluster type
   */
  public String clusterType() {
    return clusterType;
  }

  /**
   * Returns the result cluster.
   *
   * @return the cluster
   */
  public List<Result> cluster() {
    return cluster;
  }

  /**
   * Returns the creative work data.
   *
   * @return the creative work data
   */
  public CreativeWork creativeWork() {
    return creativeWork;
  }

  /**
   * Returns the music recording data.
   *
   * @return the music recording data
   */
  public MusicRecording musicRecording() {
    return musicRecording;
  }

  /**
   * Returns the review data.
   *
   * @return the review data
   */
  public Review review() {
    return review;
  }

  /**
   * Returns the software data.
   *
   * @return the software data
   */
  public Software software() {
    return software;
  }

  /**
   * Returns the recipe data.
   *
   * @return the recipe data
   */
  public Recipe recipe() {
    return recipe;
  }

  /**
   * Returns the organization data.
   *
   * @return the organization data
   */
  public Organization organization() {
    return organization;
  }

  /**
   * Returns the content type.
   *
   * @return the content type
   */
  public String contentType() {
    return contentType;
  }

  /**
   * Returns the extra snippets.
   *
   * @return the extra snippets
   */
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
