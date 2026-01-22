package net.ygbstudio.jbrave.api.builders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.http.HttpHeaders;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

class BraveSpellcheckQueryTest extends AbstractBraveQueryTest<BraveSpellcheckQuery> {

  @Override
  protected BraveSpellcheckQuery createBuilder() {
    return BraveSpellcheckQuery.builder();
  }

  @Override
  protected BraveSpellcheckQuery setQuery(@NotNull BraveSpellcheckQuery builder, String query) {
    return builder.query(query);
  }

  @Override
  protected BraveSpellcheckQuery setToken(
      @NotNull BraveSpellcheckQuery builder, ClientInfo clientInfo) {
    return builder.withToken(clientInfo);
  }

  @Override
  protected HttpHeaders getHeaders(@NotNull BraveSpellcheckQuery builder) {
    return builder.toHttpRequest().headers();
  }

  @Override
  protected void invokeWithUserAgent(@NotNull BraveSpellcheckQuery builder, String userAgent) {
    builder.withHeaders(h -> h.withUserAgent(userAgent));
  }

  @Override
  protected void invokeWithCacheControl(
      @NotNull BraveSpellcheckQuery builder, String cacheControl) {
    builder.withHeaders(h -> h.withCacheControl(cacheControl));
  }

  @Override
  protected void invokeWithApiVersion(@NotNull BraveSpellcheckQuery builder, String apiVersion) {
    builder.withHeaders(h -> h.withApiVersion(apiVersion));
  }

  @Override
  protected void clearBuilderInstance(@NotNull BraveSpellcheckQuery builder) {
    builder.reset();
  }

  @Override
  protected void triggerToHttpRequest(@NotNull BraveSpellcheckQuery builder) {
    builder.toHttpRequest();
  }

  @Test
  void testLanguage() {
    setQuery(builder, sampleQuery);
    builder.language(SearchLanguage.SPANISH);
    assertThat(builder.toURI().toString().contains(SearchLanguage.SPANISH.urlParam()), is(true));
  }

  @Test
  void testCountry() {
    setQuery(builder, sampleQuery);
    builder.country(Country.SPAIN);
    assertThat(builder.toURI().toString().contains(Country.SPAIN.urlParam()), is(true));
  }
}
