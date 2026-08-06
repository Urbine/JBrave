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

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Objects;
import java.util.StringJoiner;
import net.ygbstudio.jbrave.core.domain.dto.MetaUrl;
import net.ygbstudio.jbrave.core.domain.dto.web.QAPage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a QA infobox in search results. This class provides structured information about
 * question-and-answer content in the Brave Search API results.
 */
@JsonDeserialize(builder = QAInfobox.Builder.class)
@JsonTypeName(value = "code")
public final class QAInfobox extends AbstractGraphInfobox {
  private final String subType;
  private final QAPage data;
  private final MetaUrl metaUrl;

  public QAInfobox(Builder builder) {
    super(builder);
    this.subType = builder.subType;
    this.data = builder.data;
    this.metaUrl = builder.metaUrl;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends AbstractGraphInfobox.Builder {
    private String subType;
    private QAPage data;
    private MetaUrl metaUrl;

    public Builder subType(String subType) {
      this.subType = subType;
      return this;
    }

    public Builder data(QAPage data) {
      this.data = data;
      return this;
    }

    public Builder metaUrl(MetaUrl metaUrl) {
      this.metaUrl = metaUrl;
      return this;
    }

    @Contract(" -> new")
    @Override
    public @NotNull QAInfobox build() {
      return new QAInfobox(this);
    }
  }

  public String getSubType() {
    return subType;
  }

  public QAPage getData() {
    return data;
  }

  public MetaUrl getMetaUrl() {
    return metaUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    QAInfobox qaInfobox = (QAInfobox) o;
    return Objects.equals(getSubType(), qaInfobox.getSubType())
        && Objects.equals(getData(), qaInfobox.getData())
        && Objects.equals(getMetaUrl(), qaInfobox.getMetaUrl());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSubType(), getData(), getMetaUrl());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", QAInfobox.class.getSimpleName() + "[", "]")
        .add("subType='" + subType + "'")
        .add("data=" + data)
        .add("metaUrl=" + metaUrl)
        .add("type='" + type + "'")
        .add("position=" + position)
        .add("label='" + label + "'")
        .add("category='" + category + "'")
        .add("longDesc='" + longDesc + "'")
        .add("thumbnail=" + thumbnail)
        .add("attributes=" + attributes)
        .add("profiles=" + profiles)
        .add("websiteUrl='" + websiteUrl + "'")
        .add("ratings=" + ratings)
        .add("providers=" + providers)
        .add("distance=" + distance)
        .add("images=" + images)
        .add("movie=" + movie)
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
