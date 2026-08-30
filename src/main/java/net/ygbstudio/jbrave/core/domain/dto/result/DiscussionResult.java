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

import java.util.Objects;
import java.util.StringJoiner;
import net.ygbstudio.jbrave.core.domain.dto.web.ForumData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a discussion result in search results. This class provides structured information
 * about discussions in the Brave Search API results.
 */
@JsonDeserialize(builder = DiscussionResult.Builder.class)
public final class DiscussionResult extends SearchResult {

  private final ForumData data;

  /**
   * Creates a new discussion result from the given builder.
   *
   * @param builder the builder carrying the discussion fields
   */
  public DiscussionResult(@NotNull Builder builder) {
    super(builder);
    this.data = builder.data;
  }

  /** Builder for {@link DiscussionResult} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends SearchResult.Builder {
    private ForumData data;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the forum data.
     *
     * @param data the forum data
     * @return this builder
     */
    public Builder data(ForumData data) {
      this.data = data;
      return this;
    }

    /**
     * Builds a new {@link DiscussionResult} from the current builder state.
     *
     * @return a new {@code DiscussionResult} instance
     */
    @Contract(" -> new")
    @Override
    public @NotNull DiscussionResult build() {
      return new DiscussionResult(this);
    }
  }

  /**
   * Returns the forum data.
   *
   * @return the forum data
   */
  public ForumData getData() {
    return data;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    DiscussionResult that = (DiscussionResult) o;
    return Objects.equals(getData(), that.getData());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getData());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DiscussionResult.class.getSimpleName() + "[", "]")
        .add("data=" + data)
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
