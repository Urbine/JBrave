package net.ygbstudio.jbrave.api.builders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.net.http.HttpHeaders;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.core.domain.dto.response.SuggestSearchApiResponse;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

class BraveSuggestQueryTest extends AbstractBraveQueryTest<BraveSuggestQuery> {

  private final SuggestSearchApiResponse vacationTripsSuggest =
      SuggestSearchApiResponse.from(new File("src/test/resources/VacationTripsSuggest.json"));

  @Override
  protected BraveSuggestQuery createBuilder() {
    return BraveSuggestQuery.builder();
  }

  @Override
  protected BraveSuggestQuery setQuery(@NotNull BraveSuggestQuery builder, String query) {
    return builder.query(query);
  }

  @Override
  protected BraveSuggestQuery setToken(@NotNull BraveSuggestQuery builder, ClientInfo clientInfo) {
    return builder.withToken(clientInfo);
  }

  @Override
  protected HttpHeaders getHeaders(@NotNull BraveSuggestQuery builder) {
    return builder.toHttpRequest().headers();
  }

  @Override
  protected void invokeWithUserAgent(@NotNull BraveSuggestQuery builder, String userAgent) {
    builder.withHeaders(h -> h.withUserAgent(userAgent));
  }

  @Override
  protected void invokeWithCacheControl(@NotNull BraveSuggestQuery builder, String cacheControl) {
    builder.withHeaders(h -> h.withCacheControl(cacheControl));
  }

  @Override
  protected void invokeWithApiVersion(@NotNull BraveSuggestQuery builder, String apiVersion) {
    builder.withHeaders(h -> h.withApiVersion(apiVersion));
  }

  @Override
  protected void clearBuilderInstance(@NotNull BraveSuggestQuery builder) {
    builder.reset();
  }

  @Override
  protected void triggerToHttpRequest(@NotNull BraveSuggestQuery builder) {
    builder.toHttpRequest();
  }

  @Test
  void testLanguage() {
    setQuery(builder, sampleQuery);
    builder.language(SearchLanguage.ENGLISH);
    assertThat(builder.toURI().toString().contains(SearchLanguage.ENGLISH.urlParam()), is(true));
  }

  @Test
  void testCountry() {
    setQuery(builder, sampleQuery);
    builder.country(Country.UNITED_KINGDOM);
    assertThat(builder.toURI().toString().contains(Country.UNITED_KINGDOM.urlParam()), is(true));
  }

  @Test
  void testRich() {
    setQuery(builder, sampleQuery);
    builder.rich(true);
    assertThat(builder.toURI().toString().contains("rich=true"), is(true));
  }

  @Test
  void testCount() {
    setQuery(builder, sampleQuery);
    int count = 8;
    builder.count(count);
    assertThat(builder.toURI().toString().contains("count=" + count), is(true));
  }

  @Test
  void testSuggestPOJOType() {
    assertThat(vacationTripsSuggest.type(), is("suggest"));
  }

  @Test
  void testSuggestPOJOQuery() {
    assertThat(vacationTripsSuggest.query().original(), is("vacation trips"));
  }

  @Test
  void testSuggestPOJOResults() {
    vacationTripsSuggest
        .results()
        .forEach(res -> assertThat(res.query(), containsString("vacation")));
  }

  @Test
  void testSuggestPOJOResultCount() {
    assertThat(vacationTripsSuggest.results().size(), is(5));
  }
}
