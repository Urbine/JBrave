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

import java.util.Objects;
import java.util.StringJoiner;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a contact point for an organization or person. This class provides structured contact
 * information in the Brave Search API results.
 */
@JsonDeserialize(builder = ContactPoint.Builder.class)
public final class ContactPoint extends Thing {
  private final String telephone;
  private final String email;

  /**
   * Creates a new contact point from the given builder.
   *
   * @param builder the builder carrying the contact fields
   */
  private ContactPoint(@NotNull Builder builder) {
    super(builder);
    this.telephone = builder.telephone;
    this.email = builder.email;
  }

  /** Builder for {@link ContactPoint} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends Thing.Builder {
    private String telephone;
    private String email;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the telephone number for the contact point.
     *
     * @param telephone The telephone number.
     * @return The builder instance.
     */
    public Builder telephone(String telephone) {
      this.telephone = telephone;
      return this;
    }

    /**
     * Sets the email address for the contact point.
     *
     * @param email The email address.
     * @return The builder instance.
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    @Contract(" -> new")
    @Override
    public @NotNull ContactPoint build() {
      return new ContactPoint(this);
    }
  }

  /**
   * The telephone number for the contact point.
   *
   * @return The telephone number.
   */
  public String getTelephone() {
    return telephone;
  }

  /**
   * The email address for the contact point.
   *
   * @return The email address.
   */
  public String getEmail() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ContactPoint)) return false;
    ContactPoint that = (ContactPoint) o;
    return Objects.equals(telephone, that.telephone)
        && Objects.equals(email, that.email)
        && super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), telephone, email);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ContactPoint.class.getSimpleName() + "[", "]")
        .add("telephone='" + telephone + "'")
        .add("email='" + email + "'")
        .add("type='" + type + "'")
        .add("name='" + name + "'")
        .add("url='" + url + "'")
        .add("thumbnail=" + thumbnail)
        .toString();
  }
}
