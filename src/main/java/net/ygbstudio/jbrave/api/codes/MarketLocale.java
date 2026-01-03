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

import net.ygbstudio.jbrave.core.domain.provided.RegionLocaleIdentifier;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import net.ygbstudio.jbrave.core.model.BraveSearchOption;
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
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public enum MarketLocale implements RegionLocaleIdentifier {
  ARGENTINA("es-AR"),
  AUSTRALIA("en-AU"),
  AUSTRIA("de-AT"),
  BELGIUM_DUTCH("nl-BE"),
  BELGIUM_FRENCH("fr-BE"),
  BRAZIL("pt-BR"),
  CANADA_ENGLISH("en-CA"),
  CANADA_FRENCH("fr-CA"),
  CHILE("es-CL"),
  DENMARK("da-DK"),
  FINLAND("fi-FI"),
  FRANCE("fr-FR"),
  GERMANY("de-DE"),
  GREECE("el-GR"),
  HONG_KONG("zh-HK"),
  INDIA("en-IN"),
  INDONESIA("en-ID"),
  ITALY("it-IT"),
  JAPAN("ja-JP"),
  KOREA("ko-KR"),
  MALAYSIA("en-MY"),
  MEXICO("es-MX"),
  NETHERLANDS("nl-NL"),
  NEW_ZEALAND("en-NZ"),
  NORWAY("no-NO"),
  CHINA("zh-CN"),
  POLAND("pl-PL"),
  PHILIPPINES("en-PH"),
  RUSSIA("ru-RU"),
  SOUTH_AFRICA("en-ZA"),
  SPAIN("es-ES"),
  SWEDEN("sv-SE"),
  SWITZERLAND_FRENCH("fr-CH"),
  SWITZERLAND_GERMAN("de-CH"),
  TAIWAN("zh-TW"),
  TURKEY("tr-TR"),
  UNITED_KINGDOM("en-GB"),
  UNITED_STATES_ENGLISH("en-US"),
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
