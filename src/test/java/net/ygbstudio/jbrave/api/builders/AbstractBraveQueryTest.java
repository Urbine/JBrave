package net.ygbstudio.jbrave.api.builders;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import net.ygbstudio.jbrave.core.builders.AbstractQueryUrlBuilder;
import net.ygbstudio.jbrave.core.exceptions.AbsentSearchQueryException;
import net.ygbstudio.jbrave.core.exceptions.InvalidQueryTermException;
import net.ygbstudio.jbrave.core.exceptions.MissingSubscriptionTokenException;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Base test class for Brave query builders.
 *
 * @param <B> The concrete builder type.
 */
public abstract class AbstractBraveQueryTest<B extends AbstractQueryUrlBuilder<B>> {

  protected B builder;
  protected final ClientInfo sampleClientInfo =
      ClientInfo.fromProperties("sampleClientInfo.properties");
  protected final String sampleQuery = "sample query";

  @BeforeEach
  void setUp() {
    builder = createBuilder();
  }

  protected abstract B createBuilder();

  protected abstract B setQuery(B builder, String query);

  protected abstract B setToken(B builder, ClientInfo clientInfo);

  protected abstract HttpHeaders getHeaders(B builder);

  protected abstract void invokeWithUserAgent(B builder, String userAgent);

  protected abstract void invokeWithCacheControl(B builder, String cacheControl);

  protected abstract void invokeWithApiVersion(B builder, String apiVersion);

  protected abstract void clearBuilderInstance(B builder);

  @Test
  void testQuery() {
    String term = "something and everything & all";
    setQuery(builder, term);
    assertThat(
        builder.toURI().toString().contains(URLEncoder.encode(term, StandardCharsets.UTF_8)),
        is(true));
  }

  @Test
  void testQueryTermFourHundredCharThrow() {
    String fourHundredChars = "a".repeat(401);
    clearBuilderInstance(builder);
    assertThatException()
        .isThrownBy(() -> setQuery(builder, fourHundredChars))
        .isInstanceOf(InvalidQueryTermException.class);
  }

  @Test
  void testQueryTermFiftyWordsThrow() {
    String fiftyWords = "word ".repeat(51);
    clearBuilderInstance(builder);
    assertThatException()
        .isThrownBy(() -> setQuery(builder, fiftyWords))
        .isInstanceOf(InvalidQueryTermException.class);
  }

  @Test
  void testMissingTokenThrow() {
    // Must set a query first, or toHttpRequest might fail on query missing before
    // token missing depending on impl.
    // Typically build() checks implicit dependencies. AbstractQueryUrlBuilder
    // checks query.
    // AbstractBraveRequestBuilder checks token.
    setQuery(builder, sampleQuery);

    // We need a way to call toHttpRequest() generically or trigger the build.
    // Since toHttpRequest is on concrete classes, we can delegate.
    assertThatException()
        .isThrownBy(() -> triggerToHttpRequest(builder))
        .isInstanceOf(MissingSubscriptionTokenException.class);
  }

  protected abstract void triggerToHttpRequest(B builder);

  @Test
  void testClearBuilder() {
    clearBuilderInstance(builder);
    assertThatException().isThrownBy(builder::toURI).isInstanceOf(AbsentSearchQueryException.class);
  }

  @Test
  void testUserAgentHeader() {
    final String userAgent = "Mozilla/5.0";
    setToken(builder, sampleClientInfo);
    setQuery(builder, sampleQuery);
    invokeWithUserAgent(builder, userAgent);

    HttpHeaders headers = getHeaders(builder);
    assertThat(headers.allValues(BraveHeaders.USER_AGENT.value()).getFirst(), is(userAgent));
  }

  @Test
  void testCacheControlHeader() {
    final String cacheControl = "no-cache";
    setToken(builder, sampleClientInfo);
    setQuery(builder, sampleQuery);
    invokeWithCacheControl(builder, cacheControl);

    HttpHeaders headers = getHeaders(builder);
    assertThat(headers.allValues(BraveHeaders.CACHE_CONTROL.value()).getFirst(), is(cacheControl));
  }

  @Test
  void testApiVersionHeader() {
    final String apiVersion = "2023-01-01";
    setToken(builder, sampleClientInfo);
    setQuery(builder, sampleQuery);
    invokeWithApiVersion(builder, apiVersion);

    HttpHeaders headers = getHeaders(builder);
    assertThat(headers.allValues(BraveHeaders.API_VERSION.value()).getFirst(), is(apiVersion));
  }

  @Test
  void testMultipleBuildInvocation() {
    setQuery(builder, sampleQuery);
    URI uri1 = builder.toURI();
    URI uri2 = builder.toURI();
    assertThat(uri1.toString(), is(uri2.toString()));
  }
}
