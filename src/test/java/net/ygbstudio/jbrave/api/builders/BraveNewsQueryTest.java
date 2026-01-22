package net.ygbstudio.jbrave.api.builders;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import java.io.File;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.time.LocalDate;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.MarketLocale;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.core.domain.dto.MetaUrl;
import net.ygbstudio.jbrave.core.domain.dto.Query;
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import net.ygbstudio.jbrave.api.response.NewsSearchApiResponse;
import net.ygbstudio.jbrave.core.domain.dto.result.NewsResult;
import net.ygbstudio.jbrave.core.exceptions.InvalidFreshnessInterval;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

class BraveNewsQueryTest extends AbstractBraveQueryTest<BraveNewsQuery> {

  private final NewsSearchApiResponse javaLanguageNews =
      NewsSearchApiResponse.from(new File("src/test/resources/JavaLanguageNews.json"));

  @Override
  protected BraveNewsQuery createBuilder() {
    return BraveNewsQuery.builder();
  }

  @Override
  protected BraveNewsQuery setQuery(@NotNull BraveNewsQuery builder, String query) {
    return builder.query(query);
  }

  @Override
  protected BraveNewsQuery setToken(@NotNull BraveNewsQuery builder, ClientInfo clientInfo) {
    return builder.withToken(clientInfo);
  }

  @Override
  protected HttpHeaders getHeaders(@NotNull BraveNewsQuery builder) {
    return builder.toHttpRequest().headers();
  }

  @Override
  protected void invokeWithUserAgent(@NotNull BraveNewsQuery builder, String userAgent) {
    builder.withHeaders(h -> h.withUserAgent(userAgent));
  }

  @Override
  protected void invokeWithCacheControl(@NotNull BraveNewsQuery builder, String cacheControl) {
    builder.withHeaders(h -> h.withCacheControl(cacheControl));
  }

  @Override
  protected void invokeWithApiVersion(@NotNull BraveNewsQuery builder, String apiVersion) {
    builder.withHeaders(h -> h.withApiVersion(apiVersion));
  }

  @Override
  protected void clearBuilderInstance(@NotNull BraveNewsQuery builder) {
    builder.reset();
  }

  @Override
  protected void triggerToHttpRequest(@NotNull BraveNewsQuery builder) {
    builder.toHttpRequest();
  }

  @Test
  void testLanguage() {
    setQuery(builder, sampleQuery);
    builder.language(SearchLanguage.FRENCH);
    assertThat(builder.toURI().toString().contains(SearchLanguage.FRENCH.urlParam()), is(true));
  }

  @Test
  void testMarket() {
    setQuery(builder, sampleQuery);
    builder.market(MarketLocale.FRANCE);
    assertThat(builder.toURI().toString().contains(MarketLocale.FRANCE.urlParam()), is(true));
  }

  @Test
  void testSafeSearch() {
    setQuery(builder, sampleQuery);
    builder.safeSearch(SafeSearch.OFF);
    assertThat(builder.toURI().toString().contains("safesearch=off"), is(true));
  }

  @Test
  void testCount() {
    setQuery(builder, sampleQuery);
    int count = 5;
    builder.count(count);
    assertThat(builder.toURI().toString().contains("count=" + count), is(true));
  }

  @Test
  void testOffset() {
    setQuery(builder, sampleQuery);
    int offset = 2;
    builder.offset(offset);
    assertThat(builder.toURI().toString().contains("offset=" + offset), is(true));
  }

  @Test
  void testSpellcheck() {
    setQuery(builder, sampleQuery);
    builder.spellcheck(false);
    assertThat(builder.toURI().toString().contains("spellcheck=false"), is(true));
  }

  @Test
  void testFreshnessEnum() {
    setQuery(builder, sampleQuery);
    builder.freshness(Freshness.WITHIN_24H);
    assertThat(builder.toURI().toString().contains("freshness=pd"), is(true));
  }

  @Test
  void testFreshnessDateRange() {
    setQuery(builder, sampleQuery);
    LocalDate start = LocalDate.of(2023, 1, 1);
    LocalDate end = LocalDate.of(2023, 1, 31);
    builder.freshness(start, end);
    assertThat(builder.toURI().toString().contains("freshness=2023-01-01to2023-01-31"), is(true));
  }

  @Test
  void testFreshnessThrow() {
    assertThatException()
        .isThrownBy(() -> builder.freshness(LocalDate.now(), LocalDate.now().minusDays(1)))
        .isInstanceOf(InvalidFreshnessInterval.class);
  }

  @Test
  void testExtraSnippets() {
    setQuery(builder, sampleQuery);
    builder.extraSnippets(true);
    assertThat(builder.toURI().toString().contains("extra_snippets=true"), is(true));
  }

  @Test
  void testGoggles() {
    setQuery(builder, sampleQuery);
    String goggleUrl = "http://example.com/goggle";
    builder.goggles(URI.create(goggleUrl));
    assertThat(
        builder.toURI().toString().contains("goggles=http%3A%2F%2Fexample.com%2Fgoggle"), is(true));
  }

  @Test
  void testNewsPOJOResultSize() {
    assertThat(javaLanguageNews.results().size(), is(20));
  }

  @Test
  void testNewsPOJOType() {
    assertThat(javaLanguageNews.type(), is("news"));
  }

  @Test
  void testNewsPOJOQuery() {
    Query newsQuery = javaLanguageNews.query();
    assertThat(newsQuery.original(), is("java language"));
    assertThat(newsQuery.showStrictWarning(), is(false));
    assertThat(newsQuery.spellcheckOff(), is(false));
  }

  @Test
  void testNewsPOJOFirstResult() {
    NewsResult newsResult = javaLanguageNews.results().getFirst();
    assertThat(newsResult, is(notNullValue()));
    assertThat(newsResult.age(), is("5 days ago"));
    assertThat(newsResult.pageAge(), is("2026-01-16T05:57:14"));
    assertThat(newsResult.fetchedContentTimestamp(), is(1768624618));
    assertThat(newsResult.description(), containsString("sealed interfaces"));
  }

  @Test
  void testNewsPOJOMetaURL() {
    MetaUrl metaUrl = javaLanguageNews.results().getFirst().metaUrl();
    assertThat(metaUrl.scheme(), is("https"));
    assertThat(metaUrl.hostname(), endsWith(".rs"));
    assertThat(metaUrl.netloc(), endsWith(".rs"));
    assertThat(metaUrl.favicon(), startsWith("https://imgs.search.brave.com"));
    assertThat(metaUrl.path(), containsString("modern_java"));
  }

  @Test
  void testNewsPOJOThumbnail() {
    Thumbnail thumbnail = javaLanguageNews.results().getFirst().thumbnail();
    assertThat(thumbnail.src(), startsWith("https://imgs.search.brave.com"));
  }
}
