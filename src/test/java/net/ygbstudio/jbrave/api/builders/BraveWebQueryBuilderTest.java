package net.ygbstudio.jbrave.api.builders;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Set;
import net.ygbstudio.jbrave.api.codes.Country;
import net.ygbstudio.jbrave.api.codes.MarketLocale;
import net.ygbstudio.jbrave.api.codes.SearchLanguage;
import net.ygbstudio.jbrave.api.exceptions.AbsentSearchQueryException;
import net.ygbstudio.jbrave.api.exceptions.BraveGogglesIdentifierException;
import net.ygbstudio.jbrave.api.exceptions.InvalidFreshnessInterval;
import net.ygbstudio.jbrave.api.exceptions.InvalidQueryTermException;
import net.ygbstudio.jbrave.api.filters.ResultFilter;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import org.junit.jupiter.api.Test;

class BraveWebQueryBuilderTest {

  private final String sampleQuery = "test term";
  private final BraveWebQueryBuilder builder = BraveWebQueryBuilder.builder().query(sampleQuery);

  @Test
  void testSpellCheck() {
    builder.spellCheck(true);
    assertThat(builder.build().contains("spellcheck=true"), is(true));
  }

  @Test
  void testQuery() {
    BraveWebQueryBuilder anotherBuilder = BraveWebQueryBuilder.builder();
    String term = "something and everything & all";
    anotherBuilder.query(term);
    assertThat(
        anotherBuilder.build().contains(URLEncoder.encode(term, StandardCharsets.UTF_8)), is(true));
  }

  @Test
  void testQueryTermfourHundredCharThrow() {
    String fourHundredChars =
        """
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaaaaaaaaaaaaaa
         aaaaaaaaaaaaaaaa
        """;
    builder.clear();
    assertThatException()
        .isThrownBy(() -> builder.query(fourHundredChars))
        .isInstanceOf(InvalidQueryTermException.class);
  }

  @Test
  void testQueryTermFiftyWordsThrow() {
    String fiftyWords =
        """
          one two three four five six seven eight nine
          ten eleven twelve thirteen fourteen fifteen
          sixteen seventeen eighteen nineteen twenty
          twentyone twentytwo twentythree twentyfour
          twentyfive twentysix twentyseven twentyeight
          twentynine thirty thirtyone thirtytwo thirtythree
          thirtyfour thirtyfive thirtysix thirtyseven thirtyeight
          thirtynine forty fortyone fortytwo fortythree fortyfour
          fortyfive fortysix fortyseven fortyeight fortynine fifty
        """;
    builder.clear();
    assertThatException()
        .isThrownBy(() -> builder.query(fiftyWords))
        .isInstanceOf(InvalidQueryTermException.class);
  }

  @Test
  void testCount() {
    int count = 34;
    builder.count(count);
    assertThat(builder.build().contains("count=" + count), is(true));
  }

  @Test
  void testOffset() {
    int offset = 34;
    builder.offset(offset);
    assertThat(builder.build().contains("offset=" + offset), is(true));
  }

  @Test
  void testTextDecorations() {
    builder.textDecorations(true);
    assertThat(builder.build().contains("text_decorations=true"), is(true));
  }

  @Test
  void testExtraSnippets() {
    builder.extraSnippets(true);
    assertThat(builder.build().contains("extra_snippets=true"), is(true));
  }

  @Test
  void testSummary() {
    builder.summary(true);
    assertThat(builder.build().contains("summary=true"), is(true));
  }

  @Test
  void testOperators() {
    builder.operators(true);
    assertThat(builder.build().contains("operators=true"), is(true));
  }

  @Test
  void testQueryMultipleTimes() {
    String another = "anotherTerm";
    builder.query(another);
    // Only one term per search is supported
    assertThat(
        builder.build().contains(URLEncoder.encode(sampleQuery, StandardCharsets.UTF_8)), is(true));
    assertThat(
        builder.build().contains(URLEncoder.encode(another, StandardCharsets.UTF_8)), is(false));
  }

  @Test
  void testCountMultipleTimes() {
    int count1 = 34;
    int count2 = 90;
    builder.count(count1).count(count2);
    // Only one count per search is supported
    assertThat(builder.build().contains("count=" + count1), is(true));
    assertThat(builder.build().contains("count=" + count2), is(false));
  }

  @Test
  void testCountry() {
    builder.country(Country.UNITED_STATES);
    assertThat(builder.build().contains(Country.UNITED_STATES.urlParam()), is(true));
  }

  @Test
  void testLanguage() {
    builder.language(SearchLanguage.SPANISH);
    assertThat(builder.build().contains(SearchLanguage.SPANISH.urlParam()), is(true));
  }

  @Test
  void testMarket() {
    builder.market(MarketLocale.UNITED_STATES_SPANISH);
    assertThat(builder.build().contains(MarketLocale.UNITED_STATES_SPANISH.urlParam()), is(true));
  }

  @Test
  void testEnableRichCallback() {
    builder.enableRichCallback(true);
    assertThat(builder.build().contains("enable_rich_callback=true"), is(true));
  }

  @Test
  void testSafeSearch() {
    builder.safeSearch(SafeSearch.MODERATE);
    builder.build();
    assertThat(builder.build().contains("safesearch=moderate"), is(true));
  }

  @Test
  void testResultFilters() {
    builder.resultFilters(Set.of(ResultFilter.WEB, ResultFilter.SUMMARIZER));
    assertThat(
        builder.build().contains("result_filter=summarizer,web")
            || builder.build().contains("result_filter=web,summarizer"),
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
    BraveWebQueryBuilder anotherBuilder = BraveWebQueryBuilder.builder();
    String term = "something and everything & all";
    String encodedTerm = URLEncoder.encode(term, StandardCharsets.UTF_8);
    assertThat(encodedTerm, is("something+and+everything+%26+all"));
    assertThat(anotherBuilder.query(term).build().contains(encodedTerm), is(true));
  }

  @Test
  void testGoggles() {
    builder.goggles(URI.create("http://example.com"));
    assertThat(builder.build().contains("goggles=http%3A%2F%2Fexample.com"), is(true));
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
    builder.clear();
    assertThatException().isThrownBy(builder::build).isInstanceOf(AbsentSearchQueryException.class);
  }

  @Test
  void testMultipleBuildInvocation() {
    builder.build();
    builder.build();
    assertThat(builder.build(), is("https://api.search.brave.com/res/v1/web/search?q=test+term"));
  }

  @Test
  void testOneVerticalPerBuilderFactoryCall() {
    assertThat(
        BraveWebQueryBuilder.builder().builder().query("another query").build(),
        is("https://api.search.brave.com/res/v1/web/search?q=another+query"));
  }
}
