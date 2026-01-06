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

package net.ygbstudio.jbrave.api.codes;

import net.ygbstudio.jbrave.core.domain.provided.LanguageIdentifier;
import net.ygbstudio.jbrave.core.model.BraveSearchOption;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Enum representing the different language identifiers supported by the Brave Search API.
 *
 * @see LanguageIdentifier
 * @author Yoham Gabriel B. (YGBStudio)
 */
public enum SearchLanguage implements LanguageIdentifier {
  ARABIC("ar"),
  BASQUE("eu"),
  BENGALI("bn"),
  BULGARIAN("bg"),
  CATALAN("ca"),
  CHINESE_SIMPLIFIED("zh-hans"),
  CHINESE_TRADITIONAL("zh-hant"),
  CROATIAN("hr"),
  CZECH("cs"),
  DANISH("da"),
  DUTCH("nl"),
  ENGLISH("en"),
  ENGLISH_UNITED_KINGDOM("en-gb"),
  ESTONIAN("et"),
  FINNISH("fi"),
  FRENCH("fr"),
  GALICIAN("gl"),
  GERMAN("de"),
  GREEK("el"),
  GUJARATI("gu"),
  HEBREW("he"),
  HINDI("hi"),
  HUNGARIAN("hu"),
  ICELANDIC("is"),
  ITALIAN("it"),
  JAPANESE("jp"),
  KANNADA("kn"),
  KOREAN("ko"),
  LATVIAN("lv"),
  LITHUANIAN("lt"),
  MALAY("ms"),
  MALAYALAM("ml"),
  MARATHI("mr"),
  NORWEGIAN_BOKMAL("nb"),
  POLISH("pl"),
  PORTUGUESE_BRAZIL("pt-br"),
  PORTUGUESE_PORTUGAL("pt-pt"),
  PUNJABI("pa"),
  ROMANIAN("ro"),
  RUSSIAN("ru"),
  SERBIAN_CYRILIC("sr"),
  SLOVAK("sk"),
  SLOVENIAN("sl"),
  SPANISH("es"),
  SWEDISH("sv"),
  TAMIL("ta"),
  TELUGU("te"),
  THAI("th"),
  TURKISH("tr"),
  UKRAINIAN("uk"),
  VIETNAMESE("vi");

  private final String value;

  SearchLanguage(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  public @NotNull String urlParam() {
    return LanguageIdentifier.urlParam(this);
  }

  @Override
  @Contract(" -> new")
  public @NotNull @Unmodifiable SearchOptionCarrier<String> toSearchOption() {
    return BraveSearchOption.of(this, value);
  }
}
