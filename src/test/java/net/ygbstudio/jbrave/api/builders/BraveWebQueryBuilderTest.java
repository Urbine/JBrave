package net.ygbstudio.jbrave.api.builders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import net.ygbstudio.jbrave.api.codes.Country;
import net.ygbstudio.jbrave.api.codes.MarketLocale;
import net.ygbstudio.jbrave.api.codes.SearchLanguage;
import org.junit.jupiter.api.Test;

class BraveWebQueryBuilderTest {

  private final BraveWebQueryBuilder builder = BraveWebQueryBuilder.builder();

  @Test
  void testSpellCheck() {
    builder.spellCheck(true);
    assertThat(builder.build().contains("spellcheck=true"), is(true));
  }

  @Test
  void testTerm() {
    String term = "something and everything & all";
    builder.term(term);
    assertThat(builder.build().contains(URLEncoder.encode(term, StandardCharsets.UTF_8)), is(true));
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
  void testTermMultipleTimes() {

    String term1 = "something and everything & all";
    String term2 = "anotherTerm";
    builder.term(term1).term(term2);
    // Only one term per search is supported
    assertThat(
        builder.build().contains(URLEncoder.encode(term1, StandardCharsets.UTF_8)), is(true));
    assertThat(
        builder.build().contains(URLEncoder.encode(term2, StandardCharsets.UTF_8)), is(false));
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
  void testQueryTermEncoding() {
    String term = "something and everything & all";
    String encodedTerm = URLEncoder.encode(term, StandardCharsets.UTF_8);
    assertThat(encodedTerm, is("something+and+everything+%26+all"));
    assertThat(builder.term(term).build().contains(encodedTerm), is(true));
  }

  @Test
  void testEmptyBuilder() {
    assertThat(builder.build(), is(""));
  }

  @Test
  void testClearBuilder() {
    builder.term("something");
    builder.clear();
    assertThat(builder.build(), is(""));
  }
}
