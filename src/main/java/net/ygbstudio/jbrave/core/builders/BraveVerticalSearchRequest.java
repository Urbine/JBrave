package net.ygbstudio.jbrave.core.builders;

import java.net.URI;
import net.ygbstudio.jbrave.core.model.BraveHeaders;

public final class BraveVerticalSearchRequest
    extends AbstractBraveRequestBuilder<BraveVerticalSearchRequest>
    implements BraveVerticalRequest {

  private BraveVerticalSearchRequest() {}

  /**
   * Creates a new instance of {@link BraveVerticalSearchRequest}.
   *
   * @return a new instance of {@link BraveVerticalSearchRequest}
   */
  public static BraveVerticalSearchRequest builder() {
    return new BraveVerticalSearchRequest().clear();
  }

  /**
   * Sets the URI for the request.
   *
   * @param query the URI for the request
   */
  public BraveVerticalSearchRequest queryAddress(URI query) {
    return queryURI(query);
  }

  /**
   * Adds a user agent header to the request.
   *
   * @param userAgent the user agent value to set
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest withUserAgent(String userAgent) {
    return addHeader(BraveHeaders.USER_AGENT, userAgent);
  }

  /**
   * Adds a cache control header to the request.
   *
   * @param cacheControl the cache control value to set
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest withCacheControl(String cacheControl) {
    return addHeader(BraveHeaders.CACHE_CONTROL, cacheControl);
  }

  /**
   * Adds an API version header to the request.
   *
   * @param apiVersion the API version value to set
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest withApiVersion(String apiVersion) {
    return addHeader(BraveHeaders.API_VERSION, apiVersion);
  }

  /**
   * Adds a brave subscription token header to the request.
   *
   * @param subscriptionToken the subscription token to set
   * @return the current instance of {@link BraveVerticalSearchRequest}
   */
  public BraveVerticalSearchRequest withToken(String subscriptionToken) {
    return addHeader(BraveHeaders.SUBSCRIPTION_TOKEN, subscriptionToken);
  }
}
