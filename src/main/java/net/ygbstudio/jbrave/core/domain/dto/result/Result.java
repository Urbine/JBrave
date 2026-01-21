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

  protected final String title;
  protected final String url;
  protected final Boolean isSourceLocal;
  protected final Boolean isSourceBoth;
  protected final String description;
  protected final String pageAge;
  protected final String pageFetched;
  protected final Integer fetchedContentTimestamp;
  protected final Profile profile;
  protected final String language;
  protected final Boolean familyFriendly;

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

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder isSourceLocal(Boolean isSourceLocal) {
      this.isSourceLocal = isSourceLocal;
      return this;
    }

    public Builder isSourceBoth(Boolean isSourceBoth) {
      this.isSourceBoth = isSourceBoth;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder pageAge(String pageAge) {
      this.pageAge = pageAge;
      return this;
    }

    public Builder pageFetched(String pageFetched) {
      this.pageFetched = pageFetched;
      return this;
    }

    public Builder fetchedContentTimestamp(Integer fetchedContentTimestamp) {
      this.fetchedContentTimestamp = fetchedContentTimestamp;
      return this;
    }

    public Builder profile(Profile profile) {
      this.profile = profile;
      return this;
    }

    public Builder language(String language) {
      this.language = language;
      return this;
    }

    public Builder familyFriendly(Boolean familyFriendly) {
      this.familyFriendly = familyFriendly;
      return this;
    }

    public Result build() {
      return new Result(this);
    }
  }

  public String title() {
    return title;
  }

  public String url() {
    return url;
  }

  public Boolean isSourceLocal() {
    return isSourceLocal;
  }

  public Boolean isSourceBoth() {
    return isSourceBoth;
  }

  public String description() {
    return description;
  }

  public String pageAge() {
    return pageAge;
  }

  public String pageFetched() {
    return pageFetched;
  }

  public Integer fetchedContentTimestamp() {
    return fetchedContentTimestamp;
  }

  public Profile profile() {
    return profile;
  }

  public String language() {
    return language;
  }

  public Boolean familyFriendly() {
    return familyFriendly;
  }
}
