package net.ygbstudio.jbrave.api.builders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import java.io.File;
import java.net.http.HttpHeaders;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.core.domain.dto.MetaUrl;
import net.ygbstudio.jbrave.core.domain.dto.image.ImageResult;
import net.ygbstudio.jbrave.core.domain.dto.image.ImageThumbnail;
import net.ygbstudio.jbrave.core.domain.dto.image.Properties;
import net.ygbstudio.jbrave.api.response.ImageSearchApiResponse;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

class BraveImageQueryTest extends AbstractBraveQueryTest<BraveImageQuery> {

  private final ImageSearchApiResponse cancunBeachesImageQuery =
      ImageSearchApiResponse.from(new File("src/test/resources/CancunBeachesImageQuery.json"));

  @Override
  protected BraveImageQuery createBuilder() {
    return BraveImageQuery.builder();
  }

  @Override
  protected BraveImageQuery setQuery(@NotNull BraveImageQuery builder, String query) {
    return builder.query(query);
  }

  @Override
  protected BraveImageQuery setToken(@NotNull BraveImageQuery builder, ClientInfo clientInfo) {
    return builder.withToken(clientInfo);
  }

  @Override
  protected HttpHeaders getHeaders(@NotNull BraveImageQuery builder) {
    return builder.toHttpRequest().headers();
  }

  @Override
  protected void invokeWithUserAgent(@NotNull BraveImageQuery builder, String userAgent) {
    builder.withHeaders(h -> h.withUserAgent(userAgent));
  }

  @Override
  protected void invokeWithCacheControl(@NotNull BraveImageQuery builder, String cacheControl) {
    builder.withHeaders(h -> h.withCacheControl(cacheControl));
  }

  @Override
  protected void invokeWithApiVersion(@NotNull BraveImageQuery builder, String apiVersion) {
    builder.withHeaders(h -> h.withApiVersion(apiVersion));
  }

  @Override
  protected void clearBuilderInstance(@NotNull BraveImageQuery builder) {
    builder.reset();
  }

  @Override
  protected void triggerToHttpRequest(@NotNull BraveImageQuery builder) {
    builder.toHttpRequest();
  }

  @Test
  void testLanguage() {
    setQuery(builder, sampleQuery);
    builder.language(SearchLanguage.GERMAN);
    assertThat(builder.toURI().toString().contains(SearchLanguage.GERMAN.urlParam()), is(true));
  }

  @Test
  void testCountry() {
    setQuery(builder, sampleQuery);
    builder.country(Country.GERMANY);
    assertThat(builder.toURI().toString().contains(Country.GERMANY.urlParam()), is(true));
  }

  @Test
  void testSafeSearch() {
    setQuery(builder, sampleQuery);
    builder.safeSearch(SafeSearch.STRICT);
    assertThat(builder.toURI().toString().contains("safesearch=strict"), is(true));
  }

  @Test
  void testCount() {
    setQuery(builder, sampleQuery);
    int count = 10;
    builder.count(count);
    assertThat(builder.toURI().toString().contains("count=" + count), is(true));
  }

  @Test
  void testSpellcheck() {
    setQuery(builder, sampleQuery);
    builder.spellcheck(true);
    assertThat(builder.toURI().toString().contains("spellcheck=true"), is(true));
  }

  @Test
  void testImageQueryPOJO() {
    assertThat(cancunBeachesImageQuery.query().original(), is("cancun beaches"));
    assertThat(cancunBeachesImageQuery.query().spellcheckOff(), is(false));
    assertThat(cancunBeachesImageQuery.query().showStrictWarning(), is(false));
  }

  @Test
  void testImageQueryFirstResultPOJO() {
    ImageResult cancunFirstResult = cancunBeachesImageQuery.results().getFirst();
    assertThat(cancunFirstResult.type(), is("image_result"));
    assertThat(cancunFirstResult.title(), is("best beach cancun"));
    assertThat(cancunFirstResult.pageFetched(), is("2025-07-22T01:24:53Z"));
    assertThat(cancunFirstResult.url(), containsString("top-5-of-the-best-beaches-in-cancun"));
    assertThat(cancunFirstResult.confidence(), is("high"));
    assertThat(cancunFirstResult.properties(), is(notNullValue()));
    assertThat(cancunFirstResult.thumbnail(), is(notNullValue()));
  }

  @Test
  void testFirstResultPropertiesPOJO() {
    Properties properties = cancunBeachesImageQuery.results().getFirst().properties();
    assertThat(properties.url(), containsString("cancun-best-beaches.jpg"));
    assertThat(properties.placeholder(), startsWith("https://imgs.search.brave.com/"));
    assertThat(properties.height(), is(600));
    assertThat(properties.width(), is(900));
  }

  @Test
  void testFirstResultThumbnailPOJO() {
    ImageThumbnail thumbnail = cancunBeachesImageQuery.results().getFirst().thumbnail();
    assertThat(thumbnail.src(), startsWith("https://imgs.search.brave.com"));
    assertThat(thumbnail.height(), is(333));
    assertThat(thumbnail.width(), is(500));
  }

  @Test
  void testFirstResultMetaUrlPOJO() {
    MetaUrl metaUrl = cancunBeachesImageQuery.results().getFirst().metaUrl();
    assertThat(metaUrl.scheme(), is("https"));
    assertThat(metaUrl.path(), containsString("top-5-of-the-best-beaches-in-cancun"));
    assertThat(metaUrl.favicon(), startsWith("https://imgs.search.brave.com"));
    assertThat(metaUrl.netloc(), endsWith(".com"));
    assertThat(metaUrl.hostname(), endsWith(".com"));
  }

  @Test
  void testImageQueryExtraPOJO() {
    assertThat(cancunBeachesImageQuery.extra().mightBeOffensive(), is(false));
  }
}
