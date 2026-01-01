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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

/**
 * Represents the client information.
 *
 * @param subscriptionToken the subscription token for the client.
 * @author Yoham Gabriel Barboza B. (YGBStudio)
 */
public record ClientInfo(@NonNull String subscriptionToken) {

  /**
   * Create a new instance of ClientInfo from properties file.
   *
   * @param propertiesFileName the name of the properties file.
   * @return a new instance of ClientInfo.
   */
  @Contract("_ -> new")
  public static @NotNull ClientInfo fromProperties(String propertiesFileName) {
    String braveSubTokenProp = LocalEnvironment.BRAVE_SUBSCRIPTION_PROPERTY;
    Properties props = new Properties();
    try (InputStream propStream =
        ClientInfo.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
      if (propStream == null) throw new IOException();

      props.load(propStream);

      if (props.getProperty(braveSubTokenProp) == null) throw new IOException();
    } catch (IOException ioEx) {
      throw new BraveLocalEnvironmentException(
          () -> "File " + propertiesFileName + " not found in resources folder");
    }
    return new ClientInfo(props.getProperty(braveSubTokenProp));
  }

  /**
   * Create a new instance of ClientInfo from environment variable.
   *
   * @return a new instance of ClientInfo.
   * @throws BraveLocalEnvironmentException if the environment variable is not set.
   */
  @Contract(" -> new")
  public static @NotNull ClientInfo fromEnvironment() {
    String envVarName = System.getenv(LocalEnvironment.BRAVE_SUBSCRIPTION_TOKEN.toString());
    if (envVarName == null)
      throw new BraveLocalEnvironmentException(
          () -> "Variable " + LocalEnvironment.BRAVE_SUBSCRIPTION_TOKEN + " not set");
    return new ClientInfo(envVarName);
  }
}
