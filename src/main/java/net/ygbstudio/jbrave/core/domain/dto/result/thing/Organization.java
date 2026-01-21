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

package net.ygbstudio.jbrave.core.domain.dto.result.thing;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents an organization in search results. This class provides structured information about an
 * organization in the Brave Search API results.
 */
@JsonDeserialize(builder = Organization.Builder.class)
public final class Organization extends Thing {
  private final List<ContactPoint> contactPoints;

  /**
   * Constructs an Organization instance.
   *
   * @param builder The builder instance.
   */
  @Contract(pure = true)
  private Organization(@NotNull Builder builder) {
    super(builder);
    this.contactPoints = builder.contactPoints;
  }

  /** Builder class for Organization. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends Thing.Builder {
    private List<ContactPoint> contactPoints;

    /**
     * Sets the contact points for the organization.
     *
     * @param contactPoints The contact points.
     * @return The builder instance.
     */
    public Builder contactPoints(List<ContactPoint> contactPoints) {
      this.contactPoints = contactPoints;
      return this;
    }

    /**
     * Builds an Organization instance.
     *
     * @return The Organization instance.
     */
    @Override
    public Organization build() {
      return new Organization(this);
    }
  }

  /**
   * Gets the contact points for the organization.
   *
   * @return The contact points.
   */
  public List<ContactPoint> getContactPoints() {
    return contactPoints;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Organization that = (Organization) o;
    return Objects.equals(getContactPoints(), that.getContactPoints());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getContactPoints());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Organization.class.getSimpleName() + "[", "]")
        .add("contactPoints=" + contactPoints)
        .add("type='" + type + "'")
        .add("name='" + name + "'")
        .add("url='" + url + "'")
        .add("thumbnail=" + thumbnail)
        .toString();
  }
}
