package net.ygbstudio.jbrave.api.builders;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.ygbstudio.jbrave.api.filters.ResultFilter;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.options.MarketLocale;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.api.response.ErrorResponse;
import net.ygbstudio.jbrave.api.response.WebSearchApiResponse;
import net.ygbstudio.jbrave.core.builders.SearchOperatorBuilder;
import net.ygbstudio.jbrave.core.domain.dto.result.NewsResult;
import net.ygbstudio.jbrave.core.domain.dto.result.SearchResult;
import net.ygbstudio.jbrave.core.domain.dto.result.VideoData;
import net.ygbstudio.jbrave.core.domain.dto.result.VideoResult;
import net.ygbstudio.jbrave.core.domain.dto.web.ButtonResult;
import net.ygbstudio.jbrave.core.domain.dto.web.DeepResult;
import net.ygbstudio.jbrave.core.exceptions.AbsentSearchQueryException;
import net.ygbstudio.jbrave.core.exceptions.BraveGogglesIdentifierException;
import net.ygbstudio.jbrave.core.exceptions.InvalidFreshnessInterval;
import net.ygbstudio.jbrave.core.exceptions.InvalidQueryTermException;
import net.ygbstudio.jbrave.core.exceptions.MissingSubscriptionTokenException;
import net.ygbstudio.jbrave.core.local.ClientInfo;
import net.ygbstudio.jbrave.core.model.BraveErrorCode;
import net.ygbstudio.jbrave.core.model.BraveHeaders;
import org.junit.jupiter.api.Test;

class BraveWebQueryTest {

  private final String sampleQuery = "test term";
  private final BraveWebQuery builder = BraveWebQuery.builder().query(sampleQuery);

  private final ClientInfo sampleClientInfo =
      ClientInfo.fromProperties("sampleClientInfo.properties");

  private final WebSearchApiResponse webApiResponseJava =
      WebSearchApiResponse.from(new File("src/test/resources/JavaProgrammingWebApiResponse.json"));

  private final WebSearchApiResponse webApiResponseNYTimes =
      WebSearchApiResponse.from(new File("src/test/resources/NewYorkTimesResponse.json"));

  private final ErrorResponse errorResponseObject =
      ErrorResponse.from(new File("src/test/resources/ErrorResponseSample.json"));

  @Test
  void testSpellCheck() {
    builder.spellCheck(true);
    assertThat(builder.toURI().toString().contains("spellcheck=true"), is(true));
  }

  @Test
  void testQuery() {
    BraveWebQuery anotherBuilder = BraveWebQuery.builder();
    String term = "something and everything & all";
    anotherBuilder.query(term);
    assertThat(
        anotherBuilder.toURI().toString().contains(URLEncoder.encode(term, StandardCharsets.UTF_8)),
        is(true));
  }

  @Test
  void testQueryTermfourHundredCharThrow() {
    String fourHundredChars = "a".repeat(401);
    builder.reset();
    assertThatException()
        .isThrownBy(() -> builder.query(fourHundredChars))
        .isInstanceOf(InvalidQueryTermException.class);
  }

  @Test
  void testQueryTermFiftyWordsThrow() {
    String fiftyWords = "word ".repeat(51);
    builder.reset();
    assertThatException()
        .isThrownBy(() -> builder.query(fiftyWords))
        .isInstanceOf(InvalidQueryTermException.class);
  }

  @Test
  void testCount() {
    int count = 34;
    builder.count(count);
    assertThat(builder.toURI().toString().contains("count=" + count), is(true));
  }

  @Test
  void testOffset() {
    int offset = 34;
    builder.offset(offset);
    assertThat(builder.toURI().toString().contains("offset=" + offset), is(true));
  }

  @Test
  void testTextDecorations() {
    builder.textDecorations(true);
    assertThat(builder.toURI().toString().contains("text_decorations=true"), is(true));
  }

  @Test
  void testExtraSnippets() {
    builder.extraSnippets(true);
    assertThat(builder.toURI().toString().contains("extra_snippets=true"), is(true));
  }

  @Test
  void testSummary() {
    builder.summary(true);
    assertThat(builder.toURI().toString().contains("summary=true"), is(true));
  }

  @Test
  void testEnableOperators() {
    builder.enableOperators();
    assertThat(builder.toURI().toString().contains("operators=true"), is(true));
  }

  @Test
  void testOperatorQuery() {
    URI expectedURI =
        URI.create(
            "https://api.search.brave.com/res/v1/web/search?q=test+term+filetype%3Apdf+%2Bjava+-windows+%22linux%22+thisANDthat&operators=true");
    builder.enableOperators();
    builder.withOperators(
        op ->
            op.filetype("pdf")
                .exactMatch("linux")
                .exclude("windows")
                .include("java")
                .and("this", "that"));
    assertThat(builder.toURI().compareTo(expectedURI), is(0));
  }

  @Test
  void testOperatorFirstQueryLast() {
    URI expectedURI =
        URI.create(
            "https://api.search.brave.com/res/v1/web/search?q=test+term+filetype%3Apdf+%2Bjava+-windows+%22linux%22+thisANDthat&operators=true");
    Consumer<SearchOperatorBuilder> operatorBuilder =
        op ->
            op.filetype("pdf")
                .exactMatch("linux")
                .exclude("windows")
                .include("java")
                .and("this", "that");

    // Test class builder has a query in memory already
    builder.enableOperators();
    builder.withOperators(operatorBuilder);

    // Builder must work, even if query is added after the operators
    BraveWebQuery altBuilder = BraveWebQuery.builder();
    altBuilder.enableOperators();
    altBuilder.withOperators(operatorBuilder);
    altBuilder.query("test term");

    // Testing builder idempotency
    assertThat(builder.toURI().compareTo(expectedURI), is(0));
    assertThat(altBuilder.toURI().compareTo(expectedURI), is(0));
    assertThat(altBuilder.toURI().equals(builder.toURI()), is(true));
  }

  @Test
  void testQueryMultipleTimes() {
    String another = "anotherTerm";
    builder.query(another);
    // Only one term per search is supported
    assertThat(
        builder.toURI().toString().contains(URLEncoder.encode(sampleQuery, StandardCharsets.UTF_8)),
        is(true));
    assertThat(
        builder.toURI().toString().contains(URLEncoder.encode(another, StandardCharsets.UTF_8)),
        is(false));
  }

  @Test
  void testCountMultipleTimes() {
    int count1 = 34;
    int count2 = 90;
    builder.count(count1).count(count2);
    // Only one count per search is supported
    assertThat(builder.toURI().toString().contains("count=" + count1), is(true));
    assertThat(builder.toURI().toString().contains("count=" + count2), is(false));
  }

  @Test
  void testCountry() {
    builder.country(Country.UNITED_STATES);
    assertThat(builder.toURI().toString().contains(Country.UNITED_STATES.urlParam()), is(true));
  }

  @Test
  void testLanguage() {
    builder.language(SearchLanguage.SPANISH);
    assertThat(builder.toURI().toString().contains(SearchLanguage.SPANISH.urlParam()), is(true));
  }

  @Test
  void testMarket() {
    builder.market(MarketLocale.UNITED_STATES_SPANISH);
    assertThat(
        builder.toURI().toString().contains(MarketLocale.UNITED_STATES_SPANISH.urlParam()),
        is(true));
  }

  @Test
  void testEnableRichCallback() {
    builder.enableRichCallback(true);
    assertThat(builder.toURI().toString().contains("enable_rich_callback=true"), is(true));
  }

  @Test
  void testSafeSearch() {
    builder.safeSearch(SafeSearch.MODERATE);
    builder.toURI().toString();
    assertThat(builder.toURI().toString().contains("safesearch=moderate"), is(true));
  }

  @Test
  void testResultFilters() {
    builder.resultFilters(Set.of(ResultFilter.WEB, ResultFilter.SUMMARIZER));
    assertThat(
        builder.toURI().toString().contains("result_filter=summarizer%2Cweb")
            || builder.toURI().toString().contains("result_filter=web%2Csummarizer"),
        is(true));
  }

  @Test
  void testFreshnessThrow() {
    assertThatException()
        .isThrownBy(() -> builder.freshness(LocalDate.now(), LocalDate.now().minusDays(1)))
        .isInstanceOf(InvalidFreshnessInterval.class);
  }

  @Test
  void testQueryQueryEncoding() {
    BraveWebQuery anotherBuilder = BraveWebQuery.builder();
    String term = "something and everything & all";
    String encodedTerm = URLEncoder.encode(term, StandardCharsets.UTF_8);
    assertThat(encodedTerm, is("something+and+everything+%26+all"));
    assertThat(anotherBuilder.query(term).toURI().toString().contains(encodedTerm), is(true));
  }

  @Test
  void testGoggles() {
    builder.goggles(URI.create("http://example.com"));
    assertThat(builder.toURI().toString().contains("goggles=http%3A%2F%2Fexample.com"), is(true));
  }

  @Test
  void testGogglesThrow() {
    // Not a realistic example, but it illustrates a possible edge case
    assertThatException()
        .isThrownBy(() -> builder.goggles(URI.create("foo://user@example.com")))
        .isInstanceOf(BraveGogglesIdentifierException.class);
  }

  @Test
  void testClearBuilder() {
    builder.reset();
    assertThatException().isThrownBy(builder::toURI).isInstanceOf(AbsentSearchQueryException.class);
  }

  @Test
  void testMultipleBuildInvocation() {
    builder.toURI();
    builder.toURI();
    assertThat(
        builder.toURI().toString(),
        is("https://api.search.brave.com/res/v1/web/search?q=test+term"));
  }

  @Test
  void testOneVerticalPerBuilderFactoryCall() {
    assertThat(
        BraveWebQuery.builder().builder().query("another query").toURI().toString(),
        is("https://api.search.brave.com/res/v1/web/search?q=another+query"));
  }

  @Test
  void testMissingTokenThrow() {
    assertThatException()
        .isThrownBy(builder::toHttpRequest)
        .isInstanceOf(MissingSubscriptionTokenException.class);
  }

  @Test
  void testDefaultHeaders() {
    builder.withToken(sampleClientInfo);
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(headers.allValues("Accept").getFirst(), is("application/json"));
    assertThat(headers.allValues("Accept-Encoding").getFirst(), is("gzip"));
  }

  @Test
  void testCountryHeader() {
    final String countryCode = "us";
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withCountry(countryCode));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(headers.allValues(BraveHeaders.COUNTRY.value()).getFirst(), is(countryCode));
  }

  @Test
  void testStateHeader() {
    final String stateCode = "ca";
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withState(stateCode));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(headers.allValues(BraveHeaders.STATE.value()).getFirst(), is(stateCode));
  }

  @Test
  void testStateNameHeader() {
    final String stateName = "California";
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withStateName(stateName));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(headers.allValues(BraveHeaders.STATE_NAME.value()).getFirst(), is(stateName));
  }

  @Test
  void testCityHeader() {
    final String cityName = "sf";
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withCity(cityName));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(headers.allValues(BraveHeaders.CITY.value()).getFirst(), is(cityName));
  }

  @Test
  void testPostalCodeHeader() {
    final String postalCode = "94102";
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withPostalCode(postalCode));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(headers.allValues(BraveHeaders.POSTAL_CODE.value()).getFirst(), is(postalCode));
  }

  @Test
  void testTimezoneHeader() {
    final String timezone = "America/Los_Angeles";
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withTimezone(ZoneId.of(timezone)));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(headers.allValues(BraveHeaders.TIMEZONE.value()).getFirst(), is(timezone));
  }

  @Test
  void testUserAgentHeader() {
    final String userAgent =
        "Mozilla/5.0 (platform; rv:gecko-version) Gecko/gecko-trail Firefox/firefox-version";
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withUserAgent(userAgent));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(headers.allValues(BraveHeaders.USER_AGENT.value()).getFirst(), is(userAgent));
  }

  @Test
  void testLatitudeHeader() {
    final double latitude = 37.774929;
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withLatitude(latitude));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(
        headers.allValues(BraveHeaders.LATITUDE.value()).getFirst(), is(Double.toString(latitude)));
  }

  @Test
  void testLongitudeHeader() {
    final double longitude = -122.419416;
    builder.withToken(sampleClientInfo);
    builder.withHeaders(req -> req.withLongitude(longitude));
    HttpHeaders headers = builder.toHttpRequest().headers();
    assertThat(
        headers.allValues(BraveHeaders.LONGITUDE.value()).getFirst(),
        is(Double.toString(longitude)));
  }

  @Test
  void testWebApiResponseFromJsonNotNull() {
    assertThat(webApiResponseJava, is(notNullValue()));
  }

  @Test
  void testWebApiReponseWebResultList() {
    assertThat(webApiResponseJava.web(), is(notNullValue()));
    assertThat(webApiResponseJava.web().results().size(), is(10));
  }

  @Test
  void testWebApiResponseQueryFromJson() {
    assertThat(webApiResponseJava.query(), is(notNullValue()));
    assertThat(webApiResponseJava.query().original(), is("java programming language"));
    assertThat(webApiResponseJava.query().showStrictWarning(), is(false));
    assertThat(webApiResponseJava.query().isNavigational(), is(false));
    assertThat(webApiResponseJava.query().isNewsBreaking(), is(false));
    assertThat(webApiResponseJava.query().spellcheckOff(), is(true));
    assertThat(webApiResponseJava.query().country(), is("us"));
    assertThat(webApiResponseJava.query().badResults(), is(false));
    assertThat(webApiResponseJava.query().shouldFallback(), is(false));
    assertThat(webApiResponseJava.query().moreResultsAvailable(), is(true));
  }

  @Test
  void testWebApiResponseMixedResultReferences() {
    assertThat(webApiResponseJava.mixed(), is(notNullValue()));
    assertThat(webApiResponseJava.mixed().type(), is("mixed"));
    assertThat(webApiResponseJava.mixed().main().size(), is(21));
    assertThat(
        Math.toIntExact(
            webApiResponseJava.mixed().main().stream()
                .filter(s -> s.type().equals("videos"))
                .count()),
        is(1));
    assertThat(webApiResponseJava.mixed().top(), is(emptyIterable()));
    assertThat(webApiResponseJava.mixed().side(), is(emptyIterable()));
  }

  @Test
  void testWebApiResponseVideoResults() {
    assertThat(webApiResponseJava.videos(), notNullValue());
    assertThat(webApiResponseJava.videos().type(), is("videos"));
    assertThat(webApiResponseJava.videos().results().size(), is(6));
    assertThat(
        Math.toIntExact(
            webApiResponseJava.videos().results().stream()
                .filter(s -> s.type().equals("video_result"))
                .count()),
        is(6));
    assertThat(webApiResponseJava.videos().results().getFirst(), is(notNullValue()));

    VideoResult videoResult = webApiResponseJava.videos().results().getFirst();
    assertThat(videoResult.type(), is("video_result"));
    assertThat(videoResult.url(), is(notNullValue()));
    assertThat(videoResult.url(), containsString("www.youtube.com"));
    assertThat(videoResult.title(), is(notNullValue()));
    assertThat(videoResult.title(), containsString("Java Programming"));
    assertThat(videoResult.description(), is(notNullValue()));
    assertThat(videoResult.description(), containsString("Java programming"));
    assertThat(videoResult.age(), is(notNullValue()));
    assertThat(videoResult.pageAge(), is(notNullValue()));
    assertThat(videoResult.fetchedContentTimestamp(), is(notNullValue()));
    assertThat(videoResult.metaUrl(), is(notNullValue()));
    assertThat(videoResult.metaUrl().scheme(), is("https"));
    assertThat(videoResult.metaUrl().netloc(), is("youtube.com"));
    assertThat(videoResult.metaUrl().hostname(), is("www.youtube.com"));

    VideoData videoDataOne = videoResult.video();
    assertThat(videoDataOne, is(notNullValue()));
    assertThat(videoDataOne.creator(), is(notNullValue()));
    assertThat(videoDataOne.duration(), is(notNullValue()));
    assertThat(videoDataOne.publisher(), is(notNullValue()));
  }

  @Test
  void testWebApiResponseWebType() {
    assertThat(webApiResponseJava.web().type(), is("search"));
  }

  @Test
  void testWebApiResponseSearchResults() {
    SearchResult searchResult = webApiResponseJava.web().results().getFirst();
    assertThat(searchResult.type(), is("search_result"));
    assertThat(searchResult.url(), is(notNullValue()));
    assertThat(searchResult.isSourceBoth(), is(false));
    assertThat(searchResult.isSourceLocal(), is(false));
    assertThat(searchResult.description().length(), is(greaterThan(0)));
    assertThat(searchResult.description(), containsString("Java"));
    assertThat(searchResult.profile(), is(notNullValue()));
    assertThat(searchResult.language(), is("en"));
    assertThat(searchResult.familyFriendly(), is(true));
    assertThat(searchResult.isLive(), is(false));
    assertThat(searchResult.subtype(), is("generic"));
    assertThat(searchResult.metaUrl(), is(notNullValue()));
    assertThat(searchResult.thumbnail(), is(notNullValue()));
  }

  @Test
  void testWebApiResponseDeepResults() {
    DeepResult deepResult = webApiResponseJava.web().results().get(1).deepResults();
    assertThat(deepResult, is(notNullValue()));

    ButtonResult buttons = deepResult.buttons().getFirst();
    assertThat(buttons.type(), is("button_result"));
  }

  @Test
  void testWebApiResponseFamilyFriendly() {
    assertThat(webApiResponseJava.web().familyFriendly(), is(true));
  }

  @Test
  void testWebApiResponseNews() {
    assertThat(webApiResponseNYTimes.news(), is(notNullValue()));
    assertThat(webApiResponseNYTimes.news().type(), is("news"));

    List<NewsResult> newsResults = webApiResponseNYTimes.news().results();
    NewsResult firstResult = newsResults.getFirst();

    assertThat(newsResults.size(), is(10));
    assertThat(firstResult.title(), is(not(emptyString())));
    assertThat(firstResult.url(), is(not(emptyString())));
    assertThat(firstResult.description(), is(not(emptyString())));
    assertThat(firstResult.isSourceLocal(), is(false));
    assertThat(firstResult.isSourceBoth(), is(false));
    assertThat(firstResult.familyFriendly(), is(true));
    assertThat(firstResult.breaking(), is(false));
    assertThat(firstResult.metaUrl(), is(notNullValue()));
    assertThat(firstResult.profile(), is(notNullValue()));
    assertThat(firstResult.pageAge(), is(notNullValue()));
    assertThat(firstResult.fetchedContentTimestamp(), is(notNullValue()));
    assertThat(firstResult.age(), is(notNullValue()));
  }

  @Test
  void testWebApiResponseSearchResultCluster() {
    SearchResult searchResult = webApiResponseNYTimes.web().results().getFirst();
    assertThat(searchResult, is(notNullValue()));
    assertThat(searchResult.clusterType(), is("generic"));
    assertThat(searchResult.cluster().size(), is(4));
  }

  @Test
  void testErrorResponse() {
    ErrorResponse errorResponse = errorResponseObject;
    assertThat(errorResponse.type(), is("ErrorResponse"));
    assertThat(errorResponse.error().code() == BraveErrorCode.VALIDATION, is(true));
    assertThat(errorResponse.error().status(), is(422));
    assertThat(errorResponse.error().meta().component(), is(nullValue()));
    assertThat(errorResponse.time(), is(1768981917));
  }
}
