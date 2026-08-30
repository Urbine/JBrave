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

import net.ygbstudio.jbrave.core.domain.provided.CountryIdentifier;
import net.ygbstudio.jbrave.core.model.BraveSearchOption;
import net.ygbstudio.jbrave.core.model.SearchOptionCarrier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Enumeration representing a country supported by the Brave Search API.
 *
 * <p>This enumeration implements the {@link CountryIdentifier} interface, providing methods for URL
 * parameter generation.
 *
 * @see CountryIdentifier
 */
public enum Country implements CountryIdentifier {
  /** Argentina. */
  ARGENTINA("AR"),

  /** Australia. */
  AUSTRALIA("AU"),

  /** Austria. */
  AUSTRIA("AT"),

  /** Belgium. */
  BELGIUM("BE"),

  /** Brazil. */
  BRAZIL("BR"),

  /** Canada. */
  CANADA("CA"),

  /** Chile. */
  CHILE("CL"),

  /** Denmark. */
  DENMARK("DK"),

  /** Finland. */
  FINLAND("FI"),

  /** France. */
  FRANCE("FR"),

  /** Germany. */
  GERMANY("DE"),

  /** Greece. */
  GREECE("GR"),

  /** Hong Kong. */
  HONG_KONG("HK"),

  /** India. */
  INDIA("IN"),

  /** Indonesia. */
  INDONESIA("ID"),

  /** Italy. */
  ITALY("IT"),

  /** Japan. */
  JAPAN("JP"),

  /** Korea. */
  KOREA("KR"),

  /** Malaysia. */
  MALAYSIA("MY"),

  /** Mexico. */
  MEXICO("MX"),

  /** Netherlands. */
  NETHERLANDS("NL"),

  /** New Zealand. */
  NEW_ZEALAND("NZ"),

  /** Norway. */
  NORWAY("NO"),

  /** China. */
  CHINA("CN"),

  /** Poland. */
  POLAND("PL"),

  /** Portugal. */
  PORTUGAL("PT"),

  /** Philippines. */
  PHILIPPINES("PH"),

  /** Russia. */
  RUSSIA("RU"),

  /** Saudi Arabia. */
  SAUDI_ARABIA("SA"),

  /** South Africa. */
  SOUTH_AFRICA("ZA"),

  /** Spain. */
  SPAIN("ES"),

  /** Sweden. */
  SWEDEN("SE"),

  /** Switzerland. */
  SWITZERLAND("CH"),

  /** Taiwan. */
  TAIWAN("TW"),

  /** Turkey. */
  TURKEY("TR"),

  /** United Kingdom. */
  UNITED_KINGDOM("GB"),

  /** United States. */
  UNITED_STATES("US");

  private final String value;

  Country(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  public @NotNull String urlParam() {
    return CountryIdentifier.urlParam(this);
  }

  @Override
  @Contract(" -> new")
  public @NotNull @Unmodifiable SearchOptionCarrier<String> toSearchOption() {
    return BraveSearchOption.of(this, value);
  }
}
