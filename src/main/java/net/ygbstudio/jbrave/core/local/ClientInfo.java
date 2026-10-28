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
import java.net.http.HttpClient;
import java.util.Properties;
import net.ygbstudio.jbrave.core.exceptions.BraveLocalEnvironmentException;
import net.ygbstudio.jbrave.core.executors.BraveExecutionGate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Identifies a Brave API client and owns the execution state associated with its subscription
 * token.
 *
 * <p>A {@code ClientInfo} couples the token used to authenticate requests with a {@link
 * BraveExecutionGate}, which serializes and paces requests for that token identity. Each instance
 * therefore represents an independent admission-control context.
 *
 * <p>Instances are normally created from a classpath properties resource or an environment
 * variable. The public constructor accepts a token directly for tests and one-off use. A custom
 * {@link HttpClient} can be supplied through {@link #withHttpClient(HttpClient)}.
 */
public final class ClientInfo {

  private final String subscriptionToken;
  private final BraveExecutionGate requestGate;

  /**
   * Creates a new client from a subscription token supplied directly.
   *
   * <p>This constructor is intended for one-off quick tests and other cases where the token is
   * already available as a runtime value. Prefer the static factory methods {@link
   * #fromProperties(String)} or {@link #fromEnvironment()} in application code: they keep
   * credentials out of source code and separate configuration from request logic. Use {@link
   * #fromProperties(String, String)} or {@link #fromEnvironment(String)} when the property key or
   * environment variable name is customized.
   *
   * <p>This constructor verifies that a token is present, but it does not verify that the token is
   * valid. Authentication failures are reported by the Brave API when a request is executed.
   *
   * @param subscriptionToken the Brave API subscription token used to authenticate requests
   * @throws IllegalArgumentException if {@code subscriptionToken} is {@code null} or blank
   */
  public ClientInfo(@NotNull String subscriptionToken) {
    if (subscriptionToken == null || subscriptionToken.isBlank())
      throw new IllegalArgumentException("Subscription token cannot be `null` or blank.");
    this.subscriptionToken = subscriptionToken;
    requestGate = new BraveExecutionGate();
  }

  /**
   * Creates a new {@code ClientInfo} from a properties resource on the classpath.
   *
   * @param propertiesFileName the name of the classpath properties resource
   * @param customPropertyName the property name containing the subscription token
   * @return a new instance of {@code ClientInfo}
   * @throws BraveLocalEnvironmentException if the resource cannot be found or read, or if the
   *     requested property is missing
   * @throws IllegalArgumentException if the property value is blank
   */
  @Contract("_,_ -> new")
  public static @NotNull ClientInfo fromProperties(
      String propertiesFileName, String customPropertyName) {
    Properties props = new Properties();

    try (InputStream propStream =
        ClientInfo.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
      if (propStream == null) {
        throw new BraveLocalEnvironmentException(
            () -> "File " + propertiesFileName + " not found in resources folder");
      }

      props.load(propStream);

      if (props.getProperty(customPropertyName) == null) {
        throw new BraveLocalEnvironmentException(
            () ->
                "Property "
                    + customPropertyName
                    + " was not found in property file "
                    + propertiesFileName);
      }
    } catch (IOException ex) {
      throw new BraveLocalEnvironmentException(ex.getMessage());
    }
    return new ClientInfo(props.getProperty(customPropertyName));
  }

  /**
   * Creates a new {@code ClientInfo} from a classpath properties resource using the preconfigured
   * property name {@code brave.subscriptionToken}.
   *
   * @param propertiesFileName the name of the classpath properties resource
   * @return a new instance of {@code ClientInfo}
   * @throws BraveLocalEnvironmentException if the resource cannot be found or read, or if {@code
   *     brave.subscriptionToken} is missing
   * @throws IllegalArgumentException if the property value is blank
   */
  public static @NotNull ClientInfo fromProperties(String propertiesFileName) {
    return fromProperties(propertiesFileName, LocalEnvironment.BRAVE_SUBSCRIPTION_PROPERTY);
  }

  /**
   * Creates a new {@code ClientInfo} from an environment variable.
   *
   * @param envVarName the name of the environment variable containing the subscription token
   * @return a new instance of {@code ClientInfo}
   * @throws BraveLocalEnvironmentException if the environment variable is not set
   * @throws IllegalArgumentException if the environment variable value is blank
   */
  @Contract("_ -> new")
  public static @NotNull ClientInfo fromEnvironment(String envVarName) {
    String envVarValue = System.getenv(envVarName);
    if (envVarValue == null)
      throw new BraveLocalEnvironmentException(() -> "Variable " + envVarName + " not set");
    return new ClientInfo(envVarValue);
  }

  /**
   * Creates a new {@code ClientInfo} from the preconfigured environment variable {@code
   * BRAVE_SUBSCRIPTION_TOKEN}.
   *
   * @return a new instance of {@code ClientInfo}
   * @throws BraveLocalEnvironmentException if {@code BRAVE_SUBSCRIPTION_TOKEN} is not set
   * @throws IllegalArgumentException if {@code BRAVE_SUBSCRIPTION_TOKEN} is blank
   */
  public static @NotNull ClientInfo fromEnvironment() {
    return fromEnvironment(LocalEnvironment.BRAVE_SUBSCRIPTION_TOKEN);
  }

  /**
   * Configures the {@link HttpClient} used to execute requests for this client.
   *
   * <p>This method is optional. If it is not called, the executor creates and reuses a default
   * HTTP/2 client on first use.
   *
   * <p>The supplied client is used for all requests submitted through this client's {@link
   * #requestGate() execution gate}. Because request builders obtain their gate from the {@code
   * ClientInfo} passed to them (for example, through {@code withToken(ClientInfo)}), configuring
   * the client here affects every builder that uses this instance. The client should be configured
   * before requests are executed.
   *
   * <p>The supplied client remains caller-owned; JBrave reuses it without taking over its
   * lifecycle. Normal JDK {@link HttpClient} lifecycle rules apply.
   *
   * @param client the HTTP client to use; must not be {@code null}
   * @return this instance, to allow configuration in a single expression
   * @throws IllegalArgumentException if {@code client} is {@code null}
   */
  @Contract("_ -> this")
  public @NotNull ClientInfo withHttpClient(@NotNull HttpClient client) {
    if (client == null) throw new IllegalArgumentException("Unable to use a `null` http client.");
    requestGate.overrideHttpClient(client);
    return this;
  }

  /**
   * Returns the subscription token associated with this client.
   *
   * @return the subscription token
   */
  public String subscriptionToken() {
    return subscriptionToken;
  }

  /**
   * Returns the execution gate that serializes and rate-limits requests for this client's token.
   *
   * @return the execution gate bound to this client
   */
  public BraveExecutionGate requestGate() {
    return requestGate;
  }
}
