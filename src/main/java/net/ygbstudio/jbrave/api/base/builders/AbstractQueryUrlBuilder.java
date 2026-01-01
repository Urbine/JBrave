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

package net.ygbstudio.jbrave.api.base.builders;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import net.ygbstudio.jbrave.api.base.BraveAPIConstant;
import net.ygbstudio.jbrave.api.base.SearchOption;
import net.ygbstudio.jbrave.api.base.SearchVertical;
import net.ygbstudio.jbrave.api.base.model.SearchOptionCarrier;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract class that provides a base for building API queries.
 *
 * @param <T> The concrete builder class.
 */
public abstract class AbstractQueryUrlBuilder<T extends AbstractQueryUrlBuilder<T>> {

  protected final String queryPrompt = "q=";
  protected StringBuilder urlEnd;
  protected SearchVertical vertical;

  /**
   * Returns the current instance of the builder.
   *
   * <p>The cast is safe because the method is declared to return the type parameter T, which is
   * defined as {@code T extends AbstractQueryUrlBuilder<T>}. This means that T will always be a
   * subclass of AbstractQueryUrlBuilder<T>, so it is safe to cast "this" to T.
   *
   * @return Current instance of the builder.
   */
  @SuppressWarnings("unchecked")
  protected T self() {
    return (T) this;
  }

  /**
   * Checks if the given search option is already present in the URL query.
   *
   * @param option The search option to check.
   * @return {@code true} if the option is not already present in the URL query, {@code false}
   *     otherwise.
   */
  protected boolean optionCheck(@NotNull SearchOption option) {
    return !urlEnd.toString().contains(option.urlParam());
  }

  /**
   * Checks if the query prompt is already present in the URL query.
   *
   * <p>The query prompt is the string {@code "?q="}.
   *
   * @return {@code true} if the query prompt is not already present in the URL query, {@code false}
   *     otherwise.
   */
  protected boolean queryCheck() {
    return !urlEnd.toString().contains(queryPrompt);
  }

  /**
   * Adds a vertical to the current builder instance so that it can be reused.
   *
   * <p>This method is typically called when the builder is instantiated <br>
   * since methods like {@link #clear()} depend on an initialized vertical field.
   *
   * @param vertical The vertical of builder instance.
   * @return The current instance of the builder.
   */
  protected T addInstanceVertical(@NotNull SearchVertical vertical) {
    this.vertical = vertical;
    return self();
  }

  /**
   * Adds a query term to the URL query and encodes it using UTF-8.
   *
   * @param queryTerm The query term to add.
   * @return The current instance of the builder.
   */
  protected T addQueryTerm(String queryTerm) {
    if (queryCheck())
      urlEnd
          .append(queryPrompt)
          .append(URLEncoder.encode(queryTerm, StandardCharsets.UTF_8))
          .append("&");
    return self();
  }

  /**
   * Adds an option to the URL query.
   *
   * @param option The option to add.
   * @param value The value of the option.
   * @param <U> The type of the option value.
   * @return The current instance of the builder.
   */
  protected <U> T addOption(@NotNull SearchOption option, U value) {
    if (optionCheck(option)) urlEnd.append(option.urlParam()).append(value).append("&");
    return self();
  }

  /**
   * Adds a search option carrier to the URL query.
   *
   * @param optionCarrier The option carrier to add.
   * @param <V> The type of the option value.
   * @return The current instance of the builder.
   */
  protected <V> T addOptionCarrier(@NotNull SearchOptionCarrier<V> optionCarrier) {
    if (optionCheck(optionCarrier.option())) urlEnd.append(optionCarrier.buildParam()).append("&");
    return self();
  }

  /**
   * Clears the URL query by resetting the internal builder to an empty state.
   *
   * @return The current instance of the builder.
   */
  protected T clear() {
    urlEnd = new StringBuilder();
    return self();
  }

  /**
   * Builds the final URL query.
   *
   * @return The final URL query.
   */
  protected String build() {
    StringBuilder urlStart = new StringBuilder();
    urlStart
        .append(BraveAPIConstant.SEARCH_API_BASE)
        .append("/")
        .append(vertical.urlParam())
        .append("?");
    if (!urlEnd.isEmpty()) urlEnd.insert(0, urlStart);
    String builtUrl = urlEnd.toString();
    return builtUrl.endsWith("&") ? builtUrl.substring(0, builtUrl.lastIndexOf("&")) : builtUrl;
  }
}
