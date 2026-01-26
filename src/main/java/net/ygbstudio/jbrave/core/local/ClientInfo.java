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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.ygbstudio.jbrave.core.exceptions.BraveLocalEnvironmentException;
import net.ygbstudio.jbrave.core.executors.BraveExecutionGate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the client information.
 *
 * <p>This class represents a source of truth for admission control/pacing semantics tied to a token
 * identity.
 */
public class ClientInfo {

  private final String subscriptionToken;
  private final BraveExecutionGate requestGate;

  private ClientInfo(String subscriptionToken) {
    this.subscriptionToken = subscriptionToken;
    requestGate = new BraveExecutionGate();
  }

  /**
   * Create a new instance of ClientInfo from properties file.
   *
   * @param propertiesFileName the name of the properties file.
   * @param customPropertyName property name specified by the caller.
   * @return a new instance of ClientInfo.
   */
  @Contract("_,_ -> new")
  public static @NotNull ClientInfo fromProperties(
      String propertiesFileName, String customPropertyName) {
    Properties props = new Properties();
    try (InputStream propStream =
        ClientInfo.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
      if (propStream == null) throw new IOException();

      props.load(propStream);

      if (props.getProperty(customPropertyName) == null) throw new IOException();
    } catch (IOException ioEx) {
      throw new BraveLocalEnvironmentException(
          () -> "File " + propertiesFileName + " not found in resources folder");
    }
    return new ClientInfo(props.getProperty(customPropertyName));
  }

  /**
   * Create a new instance of ClientInfo from properties file with the preconfigured property name
   * {@code brave.subscriptionToken}
   *
   * @param propertiesFileName the name of the properties file.
   * @return a new instance of ClientInfo.
   */
  public static @NotNull ClientInfo fromProperties(String propertiesFileName) {
    return fromProperties(propertiesFileName, LocalEnvironment.BRAVE_SUBSCRIPTION_PROPERTY);
  }

  /**
   * Create a new instance of ClientInfo from environment variable.
   *
   * @param customVariable Environment variable specified by the caller.
   * @return a new instance of ClientInfo.
   * @throws BraveLocalEnvironmentException if the environment variable is not set.
   */
  @Contract("_ -> new")
  public static @NotNull ClientInfo fromEnvironment(String customVariable) {
    String envVarName = System.getenv(customVariable);
    if (envVarName == null)
      throw new BraveLocalEnvironmentException(
          () -> "Variable " + LocalEnvironment.BRAVE_SUBSCRIPTION_TOKEN + " not set");
    return new ClientInfo(envVarName);
  }

  /**
   * Create a new instance of ClientInfo from the preconfigured environment variable {@code
   * BRAVE_SUBSCRIPTION_TOKEN}
   *
   * @return a new instance of ClientInfo.
   * @throws BraveLocalEnvironmentException if the environment variable is not set.
   */
  public static @NotNull ClientInfo fromEnvironment() {
    return fromEnvironment(LocalEnvironment.BRAVE_SUBSCRIPTION_TOKEN);
  }

  public String subscriptionToken() {
    return subscriptionToken;
  }

  public BraveExecutionGate requestGate() {
    return requestGate;
  }
}
