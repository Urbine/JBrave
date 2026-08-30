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

import net.ygbstudio.jbrave.core.domain.provided.RegionLocaleIdentifier;
import net.ygbstudio.jbrave.core.model.BraveSearchOption;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Enumeration representing a market locale supported by the Brave Search API.
 *
 * <p>This enumeration implements the {@link RegionLocaleIdentifier} interface, providing methods
 * for URL parameter generation.
 *
 * @see RegionLocaleIdentifier
 */
public enum MarketLocale implements RegionLocaleIdentifier {
  /** Argentina. */
  ARGENTINA("es-AR"),

  /** Australia. */
  AUSTRALIA("en-AU"),

  /** Austria. */
  AUSTRIA("de-AT"),

  /** Belgium (Dutch). */
  BELGIUM_DUTCH("nl-BE"),

  /** Belgium (French). */
  BELGIUM_FRENCH("fr-BE"),

  /** Brazil. */
  BRAZIL("pt-BR"),

  /** Canada (English). */
  CANADA_ENGLISH("en-CA"),

  /** Canada (French). */
  CANADA_FRENCH("fr-CA"),

  /** Chile. */
  CHILE("es-CL"),

  /** Denmark. */
  DENMARK("da-DK"),

  /** Finland. */
  FINLAND("fi-FI"),

  /** France. */
  FRANCE("fr-FR"),

  /** Germany. */
  GERMANY("de-DE"),

  /** Greece. */
  GREECE("el-GR"),

  /** Hong Kong. */
  HONG_KONG("zh-HK"),

  /** India. */
  INDIA("en-IN"),

  /** Indonesia. */
  INDONESIA("en-ID"),

  /** Italy. */
  ITALY("it-IT"),

  /** Japan. */
  JAPAN("ja-JP"),

  /** Korea. */
  KOREA("ko-KR"),

  /** Malaysia. */
  MALAYSIA("en-MY"),

  /** Mexico. */
  MEXICO("es-MX"),

  /** Netherlands. */
  NETHERLANDS("nl-NL"),

  /** New Zealand. */
  NEW_ZEALAND("en-NZ"),

  /** Norway. */
  NORWAY("no-NO"),

  /** China. */
  CHINA("zh-CN"),

  /** Poland. */
  POLAND("pl-PL"),

  /** Philippines. */
  PHILIPPINES("en-PH"),

  /** Russia. */
  RUSSIA("ru-RU"),

  /** South Africa. */
  SOUTH_AFRICA("en-ZA"),

  /** Spain. */
  SPAIN("es-ES"),

  /** Sweden. */
  SWEDEN("sv-SE"),

  /** Switzerland (French). */
  SWITZERLAND_FRENCH("fr-CH"),

  /** Switzerland (German). */
  SWITZERLAND_GERMAN("de-CH"),

  /** Taiwan. */
  TAIWAN("zh-TW"),

  /** Turkey. */
  TURKEY("tr-TR"),

  /** United Kingdom. */
  UNITED_KINGDOM("en-GB"),

  /** United States (English). */
  UNITED_STATES_ENGLISH("en-US"),

  /** United States (Spanish). */
  UNITED_STATES_SPANISH("es-US");

  private final String value;

  MarketLocale(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  public @NotNull String urlParam() {
    return RegionLocaleIdentifier.urlParam(this);
  }

  @Contract(" -> new")
  @Override
  public @NotNull @Unmodifiable SearchOptionCarrier<String> toSearchOption() {
    return BraveSearchOption.of(this, value);
  }
}
