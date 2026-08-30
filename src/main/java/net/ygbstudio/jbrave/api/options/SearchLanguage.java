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

package net.ygbstudio.jbrave.api.options;

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
 */
public enum SearchLanguage implements LanguageIdentifier {
  /** Arabic. */
  ARABIC("ar"),

  /** Basque. */
  BASQUE("eu"),

  /** Bengali. */
  BENGALI("bn"),

  /** Bulgarian. */
  BULGARIAN("bg"),

  /** Catalan. */
  CATALAN("ca"),

  /** Chinese (Simplified). */
  CHINESE_SIMPLIFIED("zh-hans"),

  /** Chinese (Traditional). */
  CHINESE_TRADITIONAL("zh-hant"),

  /** Croatian. */
  CROATIAN("hr"),

  /** Czech. */
  CZECH("cs"),

  /** Danish. */
  DANISH("da"),

  /** Dutch. */
  DUTCH("nl"),

  /** English. */
  ENGLISH("en"),

  /** English (United Kingdom). */
  ENGLISH_UNITED_KINGDOM("en-gb"),

  /** Estonian. */
  ESTONIAN("et"),

  /** Finnish. */
  FINNISH("fi"),

  /** French. */
  FRENCH("fr"),

  /** Galician. */
  GALICIAN("gl"),

  /** German. */
  GERMAN("de"),

  /** Greek. */
  GREEK("el"),

  /** Gujarati. */
  GUJARATI("gu"),

  /** Hebrew. */
  HEBREW("he"),

  /** Hindi. */
  HINDI("hi"),

  /** Hungarian. */
  HUNGARIAN("hu"),

  /** Icelandic. */
  ICELANDIC("is"),

  /** Italian. */
  ITALIAN("it"),

  /** Japanese. */
  JAPANESE("jp"),

  /** Kannada. */
  KANNADA("kn"),

  /** Korean. */
  KOREAN("ko"),

  /** Latvian. */
  LATVIAN("lv"),

  /** Lithuanian. */
  LITHUANIAN("lt"),

  /** Malay. */
  MALAY("ms"),

  /** Malayalam. */
  MALAYALAM("ml"),

  /** Marathi. */
  MARATHI("mr"),

  /** Norwegian (Bokmål). */
  NORWEGIAN_BOKMAL("nb"),

  /** Polish. */
  POLISH("pl"),

  /** Portuguese (Brazil). */
  PORTUGUESE_BRAZIL("pt-br"),

  /** Portuguese (Portugal). */
  PORTUGUESE_PORTUGAL("pt-pt"),

  /** Punjabi. */
  PUNJABI("pa"),

  /** Romanian. */
  ROMANIAN("ro"),

  /** Russian. */
  RUSSIAN("ru"),

  /** Serbian (Cyrillic). */
  SERBIAN_CYRILIC("sr"),

  /** Slovak. */
  SLOVAK("sk"),

  /** Slovenian. */
  SLOVENIAN("sl"),

  /** Spanish. */
  SPANISH("es"),

  /** Swedish. */
  SWEDISH("sv"),

  /** Tamil. */
  TAMIL("ta"),

  /** Telugu. */
  TELUGU("te"),

  /** Thai. */
  THAI("th"),

  /** Turkish. */
  TURKISH("tr"),

  /** Ukrainian. */
  UKRAINIAN("uk"),

  /** Vietnamese. */
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
