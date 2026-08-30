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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a news result in search results. This class provides structured information about news
 * articles in the Brave Search API results.
 */
@JsonDeserialize(builder = NewsResult.Builder.class)
public final class NewsResult extends Result {
  private final MetaUrl metaUrl;
  private final String source;
  private final Boolean breaking;
  private final Boolean isLive;
  private final Thumbnail thumbnail;
  private final String age;
  private final List<String> extraSnippets;

  /**
   * Creates a new news result from the given builder.
   *
   * @param builder the builder carrying the news fields
   */
  public NewsResult(@NotNull Builder builder) {
    super(builder);
    this.metaUrl = builder.metaUrl;
    this.source = builder.source;
    this.breaking = builder.breaking;
    this.isLive = builder.isLive;
    this.thumbnail = builder.thumbnail;
    this.age = builder.age;
    this.extraSnippets = builder.extraSnippets;
  }

  /** Builder for {@link NewsResult} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends Result.Builder {
    private MetaUrl metaUrl;
    private String source;
    private Boolean breaking;
    private Boolean isLive;
    private Thumbnail thumbnail;
    private String age;
    private List<String> extraSnippets;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

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
     * Sets the source.
     *
     * @param source the source
     * @return this builder
     */
    public Builder source(String source) {
      this.source = source;
      return this;
    }

    /**
     * Sets whether the news is breaking.
     *
     * @param breaking {@code true} if breaking
     * @return this builder
     */
    public Builder breaking(Boolean breaking) {
      this.breaking = breaking;
      return this;
    }

    /**
     * Sets whether the news is live.
     *
     * @param isLive {@code true} if live
     * @return this builder
     */
    public Builder isLive(Boolean isLive) {
      this.isLive = isLive;
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
     * Sets the age of the news.
     *
     * @param age the age
     * @return this builder
     */
    public Builder age(String age) {
      this.age = age;
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
     * Builds a new {@link NewsResult} from the current builder state.
     *
     * @return a new {@code NewsResult} instance
     */
    @Override
    @Contract(" -> new")
    public @NotNull NewsResult build() {
      return new NewsResult(this);
    }
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
   * Returns the source.
   *
   * @return the source
   */
  public String source() {
    return source;
  }

  /**
   * Returns whether the news is breaking.
   *
   * @return whether the news is breaking
   */
  public Boolean breaking() {
    return breaking;
  }

  /**
   * Returns whether the news is live.
   *
   * @return whether the news is live
   */
  public Boolean isLive() {
    return isLive;
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
   * Returns the age of the news.
   *
   * @return the age
   */
  public String age() {
    return age;
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
    NewsResult that = (NewsResult) o;
    return Objects.equals(metaUrl, that.metaUrl)
        && Objects.equals(source, that.source)
        && Objects.equals(breaking, that.breaking)
        && Objects.equals(isLive, that.isLive)
        && Objects.equals(thumbnail, that.thumbnail)
        && Objects.equals(age, that.age)
        && Objects.equals(extraSnippets, that.extraSnippets);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metaUrl, source, breaking, isLive, thumbnail, age, extraSnippets);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", NewsResult.class.getSimpleName() + "[", "]")
        .add("metaUrl=" + metaUrl)
        .add("source='" + source + "'")
        .add("breaking=" + breaking)
        .add("isLive=" + isLive)
        .add("thumbnail=" + thumbnail)
        .add("age='" + age + "'")
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
