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
 * @author Yoham Gabriel B. (YGBStudio)
 */
public enum Country implements CountryIdentifier {
  ARGENTINA("AR"),
  AUSTRALIA("AU"),
  AUSTRIA("AT"),
  BELGIUM("BE"),
  BRAZIL("BR"),
  CANADA("CA"),
  CHILE("CL"),
  DENMARK("DK"),
  FINLAND("FI"),
  FRANCE("FR"),
  GERMANY("DE"),
  GREECE("GR"),
  HONG_KONG("HK"),
  INDIA("IN"),
  INDONESIA("ID"),
  ITALY("IT"),
  JAPAN("JP"),
  KOREA("KR"),
  MALAYSIA("MY"),
  MEXICO("MX"),
  NETHERLANDS("NL"),
  NEW_ZEALAND("NZ"),
  NORWAY("NO"),
  CHINA("CN"),
  POLAND("PL"),
  PORTUGAL("PT"),
  PHILIPPINES("PH"),
  RUSSIA("RU"),
  SAUDI_ARABIA("SA"),
  SOUTH_AFRICA("ZA"),
  SPAIN("ES"),
  SWEDEN("SE"),
  SWITZERLAND("CH"),
  TAIWAN("TW"),
  TURKEY("TR"),
  UNITED_KINGDOM("GB"),
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
