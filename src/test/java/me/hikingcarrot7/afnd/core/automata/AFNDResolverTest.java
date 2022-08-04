package me.hikingcarrot7.afnd.core.automata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static me.hikingcarrot7.afnd.core.matchers.MatchesPath.matchesPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AFNDResolverTest {
  private AutomataGraph<Integer> automataGraph;
  private AutomataResolver resolver;

  @BeforeEach
  void setUp() {
    automataGraph = new AutomataGraph<>();
    resolver = new AutomataResolver(automataGraph);
    automataGraph.insertAsInitialState(5);
    automataGraph.insertAsFinalState(10);
    automataGraph.insertConnection(5, 10, "c");
    automataGraph.insertConnection(5, 5, "a");
    automataGraph.insertConnection(10, 10, "b");
  }

  @ParameterizedTest
  @ValueSource(strings = {"acb", "aaaaacbb", "cbb"})
  void whenInputMatches_thenReturnsTrue(String input) {
    MatchResult result = resolver.matches(new AutomataInput(input));

    assertTrue(result.matches());
    assertThat(result, matchesPath(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"ab", "bbbb", "aaaccbbb", "ccbb"})
  void whenNotInputMatches_thenReturnsTrue(String input) {
    MatchResult result = resolver.matches(new AutomataInput(input));

    assertFalse(result.matches());
    assertThat(result.getPath(), is(empty()));
  }

}