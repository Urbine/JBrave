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

package net.ygbstudio.jbrave.core.local;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import net.ygbstudio.jbrave.core.exceptions.BraveLocalEnvironmentException;
import org.junit.jupiter.api.Test;

class ClientInfoTest {

  @Test
  void testThrowIOExceptionNotFound() {
    assertThatException()
        .isThrownBy(() -> ClientInfo.fromProperties("nonExistent.properties"))
        .isInstanceOf(BraveLocalEnvironmentException.class);
  }

  @Test
  void testInvalidPropertyThrow() {
    String invalidProperty = "invalidClientInfo.properties";
    assertThatException()
        .isThrownBy(() -> ClientInfo.fromProperties(invalidProperty))
        .isInstanceOf(BraveLocalEnvironmentException.class);
  }

  @Test
  void testClientInfoFromProperties() {
    String sampleProperties = "sampleClientInfo.properties";
    ClientInfo clientInfo = ClientInfo.fromProperties(sampleProperties);
    assertThat(clientInfo.subscriptionToken()).isEqualTo("BS00000000000000000000000000000");
  }

  @Test
  void testClientInfoFromEnvironmentThrow() {
    assertThatException()
        .isThrownBy(ClientInfo::fromEnvironment)
        .isInstanceOf(BraveLocalEnvironmentException.class);
  }
}
