package net.ygbstudio.jbrave.api.builders;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import java.io.File;
import java.net.http.HttpHeaders;
import java.time.LocalDate;
import net.ygbstudio.jbrave.api.filters.Freshness;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.MarketLocale;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.api.response.VideoSearchApiResponse;
import net.ygbstudio.jbrave.core.domain.dto.MetaUrl;
import net.ygbstudio.jbrave.core.domain.dto.Query;
import net.ygbstudio.jbrave.core.domain.dto.Thumbnail;
import net.ygbstudio.jbrave.core.domain.dto.result.VideoData;
import net.ygbstudio.jbrave.core.domain.dto.result.VideoResult;
import net.ygbstudio.jbrave.core.domain.dto.result.profile.Profile;
import net.ygbstudio.jbrave.core.exceptions.InvalidFreshnessInterval;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

class BraveVideoQueryTest extends AbstractBraveQueryTest<BraveVideoQuery> {

  private final VideoSearchApiResponse braveSearchVideoSearch =
      VideoSearchApiResponse.from(new File("src/test/resources/BraveSearchVideoSearch.json"));

  @Override
  protected BraveVideoQuery createBuilder() {
    return BraveVideoQuery.builder();
  }

  @Override
  protected BraveVideoQuery setQuery(@NotNull BraveVideoQuery builder, String query) {
    return builder.query(query);
  }

  @Override
  protected BraveVideoQuery setToken(@NotNull BraveVideoQuery builder, ClientInfo clientInfo) {
    return builder.withToken(clientInfo);
  }

  @Override
  protected HttpHeaders getHeaders(@NotNull BraveVideoQuery builder) {
    return builder.toHttpRequest().headers();
  }

  @Override
  protected void invokeWithUserAgent(@NotNull BraveVideoQuery builder, String userAgent) {
    builder.withHeaders(h -> h.withUserAgent(userAgent));
  }

  @Override
  protected void invokeWithCacheControl(@NotNull BraveVideoQuery builder, String cacheControl) {
    builder.withHeaders(h -> h.withCacheControl(cacheControl));
  }

  @Override
  protected void invokeWithApiVersion(@NotNull BraveVideoQuery builder, String apiVersion) {
    builder.withHeaders(h -> h.withApiVersion(apiVersion));
  }

  @Override
  protected void clearBuilderInstance(@NotNull BraveVideoQuery builder) {
    builder.reset();
  }

  @Override
  protected void triggerToHttpRequest(@NotNull BraveVideoQuery builder) {
    builder.toHttpRequest();
  }

  @Test
  void testLanguage() {
    setQuery(builder, sampleQuery);
    builder.language(SearchLanguage.JAPANESE);
    assertThat(builder.toURI().toString().contains(SearchLanguage.JAPANESE.urlParam()), is(true));
  }

  @Test
  void testMarket() {
    setQuery(builder, sampleQuery);
    builder.market(MarketLocale.JAPAN);
    assertThat(builder.toURI().toString().contains(MarketLocale.JAPAN.urlParam()), is(true));
  }

  @Test
  void testSafeSearch() {
    setQuery(builder, sampleQuery);
    builder.safeSearch(SafeSearch.MODERATE);
    assertThat(builder.toURI().toString().contains("safesearch=moderate"), is(true));
  }

  @Test
  void testCount() {
    setQuery(builder, sampleQuery);
    int count = 20;
    builder.count(count);
    assertThat(builder.toURI().toString().contains("count=" + count), is(true));
  }

  @Test
  void testOffset() {
    setQuery(builder, sampleQuery);
    int offset = 1;
    builder.offset(offset);
    assertThat(builder.toURI().toString().contains("offset=" + offset), is(true));
  }

  @Test
  void testSpellcheck() {
    setQuery(builder, sampleQuery);
    builder.spellcheck(true);
    assertThat(builder.toURI().toString().contains("spellcheck=true"), is(true));
  }

  @Test
  void testFreshnessEnum() {
    setQuery(builder, sampleQuery);
    builder.freshness(Freshness.WITHIN_7D);
    assertThat(builder.toURI().toString().contains("freshness=pw"), is(true));
  }

  @Test
  void testFreshnessDateRange() {
    setQuery(builder, sampleQuery);
    LocalDate start = LocalDate.of(2023, 5, 1);
    LocalDate end = LocalDate.of(2023, 5, 7);
    builder.freshness(start, end);
    assertThat(builder.toURI().toString().contains("freshness=2023-05-01to2023-05-07"), is(true));
  }

  @Test
  void testFreshnessThrow() {
    assertThatException()
        .isThrownBy(() -> builder.freshness(LocalDate.now(), LocalDate.now().minusDays(1)))
        .isInstanceOf(InvalidFreshnessInterval.class);
  }

  @Test
  void testVideoPOJOType() {
    assertThat(braveSearchVideoSearch.type(), is("videos"));
  }

  @Test
  void testVideoPOJOQuery() {
    Query videoQuery = braveSearchVideoSearch.query();
    assertThat(videoQuery.original(), containsString("brave search +tutorial"));
    assertThat(videoQuery.altered(), containsString("brave search tutorial"));
    assertThat(videoQuery.spellcheckOff(), is(false));
    assertThat(videoQuery.showStrictWarning(), is(false));
  }

  @Test
  void testVideoPOJOFirstResult() {
    VideoResult videoResult = braveSearchVideoSearch.results().getFirst();
    assertThat(videoResult.type(), is("video_result"));
    assertThat(videoResult.url(), startsWith("https://www.youtube.com"));
    assertThat(videoResult.title(), containsString("Step by Step Brave Tutorial"));
    assertThat(videoResult.age(), is("August 18, 2025"));
    assertThat(videoResult.pageAge(), is("2025-08-18T13:01:53"));
    assertThat(videoResult.fetchedContentTimestamp(), is(1758765166));
  }

  @Test
  void testVideoDataPOJOFirstResult() {
    VideoData videoData = braveSearchVideoSearch.results().getFirst().video();
    assertThat(videoData.duration(), is("01:40"));
    assertThat(videoData.creator(), containsString("Guide"));
    assertThat(videoData.publisher(), is("YouTube"));
    assertThat(videoData.requiresSubscription(), is(false));
    assertThat(videoData.tags().getFirst(), is("switch search engine brave"));
  }

  @Test
  void testProfilePOJOFirstResultAuthor() {
    Profile profile = braveSearchVideoSearch.results().getFirst().video().author();
    assertThat(profile.name(), containsString("Money"));
    assertThat(profile.url(), startsWith("http://www.youtube.com"));
  }

  @Test
  void testVideoPOJOThumbnailFirstResult() {
    Thumbnail thumbnail = braveSearchVideoSearch.results().getFirst().thumbnail();
    assertThat(thumbnail.src(), startsWith("https://imgs.search.brave.com/"));
    assertThat(thumbnail.original(), startsWith("https://i.ytimg.com"));
  }

  @Test
  void testMetaURLPOJOFirstResult() {
    MetaUrl metaUrl = braveSearchVideoSearch.results().getFirst().metaUrl();
    assertThat(metaUrl.scheme(), is("https"));
    assertThat(metaUrl.path(), is("› watch"));
    assertThat(metaUrl.favicon(), startsWith("https://imgs.search.brave.com"));
    assertThat(metaUrl.netloc(), is("youtube.com"));
    assertThat(metaUrl.hostname(), is("www.youtube.com"));
  }

  @Test
  void testExtraPOJOFirstResult() {
    assertThat(braveSearchVideoSearch.extra().mightBeOffensive(), is(false));
  }
}
