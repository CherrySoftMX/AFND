package me.hikingcarrot7.afnd.core.afnd;

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
  private AFNDGraph<Integer> afndGraph;
  private AFNDResolver resolver;

  @BeforeEach
  void setUp() {
    afndGraph = new AFNDGraph<>();
    resolver = new AFNDResolver(afndGraph);
    afndGraph.insertAsInitialState(5);
    afndGraph.insertAsFinalState(10);
    afndGraph.insertConnection(5, 10, "c");
    afndGraph.insertConnection(5, 5, "a");
    afndGraph.insertConnection(10, 10, "b");
  }

  @ParameterizedTest
  @ValueSource(strings = {"acb", "aaaaacbb", "cbb"})
  void whenInputMatches_thenReturnsTrue(String input) {
    MatchResult result = resolver.matches(new AFNDInput(input));

    assertTrue(result.matches());
    assertThat(result, matchesPath(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {"ab", "bbbb", "aaaccbbb", "ccbb"})
  void whenNotInputMatches_thenReturnsTrue(String input) {
    MatchResult result = resolver.matches(new AFNDInput(input));

    assertFalse(result.matches());
    assertThat(result.getPath(), is(empty()));
  }

}