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
 * Represents a person in search results. This class provides structured information about a person
 * in the Brave Search API results.
 */
@JsonDeserialize(builder = Person.Builder.class)
public final class Person extends Thing {
  private final String email;

  /**
   * Constructs a new Person instance.
   *
   * @param builder The builder instance.
   */
  @Contract(pure = true)
  public Person(@NotNull Builder builder) {
    super(builder);
    this.email = builder.email;
  }

  /** Builder class for Person. */
  /** Builder for {@link Person} instances. */
  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder extends Thing.Builder {
    private String email;

    /** Creates a new builder with all fields unset. */
    public Builder() {}

    /**
     * Sets the email address of the person.
     *
     * @param email The email address.
     * @return The builder instance.
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * Builds a new Person instance.
     *
     * @return A new Person instance.
     */
    @Contract(value = " -> new")
    @Override
    public @NotNull Person build() {
      return new Person(this);
    }
  }

  /**
   * Gets the email address of the person.
   *
   * @return The email address.
   */
  public String getEmail() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(getEmail(), person.getEmail());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getEmail());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
        .add("email='" + email + "'")
        .add("type='" + type + "'")
        .add("name='" + name + "'")
        .add("url='" + url + "'")
        .add("thumbnail=" + thumbnail)
        .toString();
  }
}
