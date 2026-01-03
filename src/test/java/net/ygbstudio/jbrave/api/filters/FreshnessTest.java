package net.ygbstudio.jbrave.api.filters;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesRegex;

import java.time.LocalDate;
import net.ygbstudio.jbrave.core.exceptions.InvalidFreshnessInterval;
import net.ygbstudio.jbrave.core.domain.modes.SearchFilterMode;
import org.junit.jupiter.api.Test;

class FreshnessTest {
  @Test
  void betweenTest() {
    String freshness = Freshness.between(LocalDate.now(), LocalDate.now().plusDays(1)).value();
    assertThat(freshness, matchesRegex("^\\d{4}-\\d{2}-\\d{2}to\\d{4}-\\d{2}-\\d{2}$"));
  }

  @Test
  void betweenAsParamTest() {
    String freshness = Freshness.between(LocalDate.now(), LocalDate.now().plusDays(1)).buildParam();
    assertThat(
        freshness,
        matchesRegex(
            "^"
                + SearchFilterMode.FRESHNESS.value()
                + "="
                + "\\d{4}-\\d{2}-\\d{2}to\\d{4}-\\d{2}-\\d{2}$"));
  }

  @Test
  void betweenThrowTest() {
    assertThatException()
        .isThrownBy(() -> Freshness.between(LocalDate.now(), LocalDate.now().minusDays(1)))
        .isInstanceOf(InvalidFreshnessInterval.class);
  }
}
