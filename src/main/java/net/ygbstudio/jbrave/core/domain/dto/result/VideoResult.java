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
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a video result in search results. This class provides structured information about
 * videos in the Brave Search API results.
 */
@JsonDeserialize(builder = VideoResult.Builder.class)
public final class VideoResult extends Result {
  private final String type;
  private final VideoData video;
  private final MetaUrl metaUrl;
  private final Thumbnail thumbnail;
  private final String age;

  /**
   * Creates a new video result from the given builder.
   *
   * @param builder the builder carrying the video fields
   */
  public VideoResult(@NotNull Builder builder) {
    super(builder);
    this.type = builder.type;
    this.video = builder.video;
    this.metaUrl = builder.metaUrl;
    this.thumbnail = builder.thumbnail;
    this.age = builder.age;
  }

  /** Builder for {@link VideoResult} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends Result.Builder {
    private String type;
    private VideoData video;
    private MetaUrl metaUrl;
    private Thumbnail thumbnail;
    private String age;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the video type.
     *
     * @param type the video type
     * @return this builder
     */
    public Builder type(String type) {
      this.type = type;
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
     * Sets the age of the video.
     *
     * @param age the age
     * @return this builder
     */
    public Builder age(String age) {
      this.age = age;
      return this;
    }

    /**
     * Builds a new {@link VideoResult} from the current builder state.
     *
     * @return a new {@code VideoResult} instance
     */
    @Override
    @Contract(" -> new")
    public @NotNull VideoResult build() {
      return new VideoResult(this);
    }
  }

  /**
   * Returns the video type.
   *
   * @return the video type
   */
  public String type() {
    return type;
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
   * Returns the age of the video.
   *
   * @return the age
   */
  public String age() {
    return age;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    VideoResult that = (VideoResult) o;
    return Objects.equals(type, that.type)
        && Objects.equals(video, that.video)
        && Objects.equals(metaUrl, that.metaUrl)
        && Objects.equals(thumbnail, that.thumbnail)
        && Objects.equals(age, that.age);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, video, metaUrl, thumbnail, age);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", VideoResult.class.getSimpleName() + "[", "]")
        .add("type='" + type + "'")
        .add("video=" + video)
        .add("metaUrl=" + metaUrl)
        .add("thumbnail=" + thumbnail)
        .add("age='" + age + "'")
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
