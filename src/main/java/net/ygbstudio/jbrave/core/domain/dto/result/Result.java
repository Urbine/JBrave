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
import net.ygbstudio.jbrave.core.domain.dto.result.profile.Profile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a generic result in search results. This class serves as a base for various types of
 * results in the Brave Search API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = Result.Builder.class)
public sealed class Result
    permits AbstractGraphInfobox,
        LocationResult,
        LocationWebResult,
        NewsResult,
        SearchResult,
        VideoResult {

  /** The result title. */
  protected final String title;

  /** The result URL. */
  protected final String url;

  /** Whether the source is local. */
  protected final Boolean isSourceLocal;

  /** Whether the source is both. */
  protected final Boolean isSourceBoth;

  /** The result description. */
  protected final String description;

  /** The page age. */
  protected final String pageAge;

  /** The page fetched timestamp string. */
  protected final String pageFetched;

  /** The fetched content timestamp. */
  protected final Integer fetchedContentTimestamp;

  /** The profile. */
  protected final Profile profile;

  /** The result language. */
  protected final String language;

  /** Whether the result is family friendly. */
  protected final Boolean familyFriendly;

  /**
   * Creates a new result from the given builder.
   *
   * @param builder the builder carrying the result fields
   */
  @Contract(pure = true)
  public Result(@NotNull Builder builder) {
    this.title = builder.title;
    this.url = builder.url;
    this.isSourceLocal = builder.isSourceLocal;
    this.isSourceBoth = builder.isSourceBoth;
    this.description = builder.description;
    this.pageAge = builder.pageAge;
    this.pageFetched = builder.pageFetched;
    this.profile = builder.profile;
    this.language = builder.language;
    this.familyFriendly = builder.familyFriendly;
    this.fetchedContentTimestamp = builder.fetchedContentTimestamp;
  }

  /** Builder for {@link Result} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
    private String title;
    private String url;
    private Boolean isSourceLocal;
    private Boolean isSourceBoth;
    private String description;
    private String pageAge;
    private String pageFetched;
    private Integer fetchedContentTimestamp;
    private Profile profile;
    private String language;
    private Boolean familyFriendly;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the result title.
     *
     * @param title the title
     * @return this builder
     */
    public Builder title(String title) {
      this.title = title;
      return this;
    }

    /**
     * Sets the result URL.
     *
     * @param url the URL
     * @return this builder
     */
    public Builder url(String url) {
      this.url = url;
      return this;
    }

    /**
     * Sets whether the source is local.
     *
     * @param isSourceLocal whether the source is local
     * @return this builder
     */
    public Builder isSourceLocal(Boolean isSourceLocal) {
      this.isSourceLocal = isSourceLocal;
      return this;
    }

    /**
     * Sets whether the source is both.
     *
     * @param isSourceBoth whether the source is both
     * @return this builder
     */
    public Builder isSourceBoth(Boolean isSourceBoth) {
      this.isSourceBoth = isSourceBoth;
      return this;
    }

    /**
     * Sets the result description.
     *
     * @param description the description
     * @return this builder
     */
    public Builder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets the page age.
     *
     * @param pageAge the page age
     * @return this builder
     */
    public Builder pageAge(String pageAge) {
      this.pageAge = pageAge;
      return this;
    }

    /**
     * Sets the page fetched timestamp string.
     *
     * @param pageFetched the page fetched timestamp
     * @return this builder
     */
    public Builder pageFetched(String pageFetched) {
      this.pageFetched = pageFetched;
      return this;
    }

    /**
     * Sets the fetched content timestamp.
     *
     * @param fetchedContentTimestamp the fetched content timestamp
     * @return this builder
     */
    public Builder fetchedContentTimestamp(Integer fetchedContentTimestamp) {
      this.fetchedContentTimestamp = fetchedContentTimestamp;
      return this;
    }

    /**
     * Sets the profile.
     *
     * @param profile the profile
     * @return this builder
     */
    public Builder profile(Profile profile) {
      this.profile = profile;
      return this;
    }

    /**
     * Sets the result language.
     *
     * @param language the language
     * @return this builder
     */
    public Builder language(String language) {
      this.language = language;
      return this;
    }

    /**
     * Sets whether the result is family friendly.
     *
     * @param familyFriendly whether the result is family friendly
     * @return this builder
     */
    public Builder familyFriendly(Boolean familyFriendly) {
      this.familyFriendly = familyFriendly;
      return this;
    }

    /**
     * Builds a new {@link Result} from the current builder state.
     *
     * @return a new {@code Result} instance
     */
    public Result build() {
      return new Result(this);
    }
  }

  /**
   * Returns the result title.
   *
   * @return the title
   */
  public String title() {
    return title;
  }

  /**
   * Returns the result URL.
   *
   * @return the URL
   */
  public String url() {
    return url;
  }

  /**
   * Returns whether the source is local.
   *
   * @return whether the source is local
   */
  public Boolean isSourceLocal() {
    return isSourceLocal;
  }

  /**
   * Returns whether the source is both.
   *
   * @return whether the source is both
   */
  public Boolean isSourceBoth() {
    return isSourceBoth;
  }

  /**
   * Returns the result description.
   *
   * @return the description
   */
  public String description() {
    return description;
  }

  /**
   * Returns the page age.
   *
   * @return the page age
   */
  public String pageAge() {
    return pageAge;
  }

  /**
   * Returns the page fetched timestamp string.
   *
   * @return the page fetched timestamp
   */
  public String pageFetched() {
    return pageFetched;
  }

  /**
   * Returns the fetched content timestamp.
   *
   * @return the fetched content timestamp
   */
  public Integer fetchedContentTimestamp() {
    return fetchedContentTimestamp;
  }

  /**
   * Returns the profile.
   *
   * @return the profile
   */
  public Profile profile() {
    return profile;
  }

  /**
   * Returns the result language.
   *
   * @return the language
   */
  public String language() {
    return language;
  }

  /**
   * Returns whether the result is family friendly.
   *
   * @return whether the result is family friendly
   */
  public Boolean familyFriendly() {
    return familyFriendly;
  }
}
