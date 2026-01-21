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
import net.ygbstudio.jbrave.core.domain.dto.MetaUrl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a location web result in search results. This class provides structured information
 * about web locations in the Brave Search API results.
 */
@JsonDeserialize(builder = LocationWebResult.Builder.class)
public final class LocationWebResult extends Result {

  private final MetaUrl metaUrl;

  public LocationWebResult(@NotNull Builder builder) {
    super(builder);
    this.metaUrl = builder.metaUrl;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends Result.Builder {
    private MetaUrl metaUrl;

    public Builder metaUrl(MetaUrl metaUrl) {
      this.metaUrl = metaUrl;
      return this;
    }

    @Override
    @Contract(" -> new")
    public @NotNull LocationWebResult build() {
      return new LocationWebResult(this);
    }
  }

  public MetaUrl getMetaUrl() {
    return metaUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    LocationWebResult that = (LocationWebResult) o;
    return Objects.equals(getMetaUrl(), that.getMetaUrl());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getMetaUrl());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", LocationWebResult.class.getSimpleName() + "[", "]")
        .add("metaUrl=" + metaUrl)
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
