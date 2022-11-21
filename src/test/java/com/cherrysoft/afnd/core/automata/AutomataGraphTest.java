package com.cherrysoft.afnd.core.automata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutomataGraphTest {
  private AutomataGraph<Integer> automataGraph;

  @BeforeEach
  void setUp() {
    automataGraph = new AutomataGraph<>();
    automataGraph.insertElement(5);
  }

  @Test
  void givenElementToRemove_whenIsFinalState_thenIsRemovedFromFinalStatesSet() {
    automataGraph.insertAsFinalState(10);

    automataGraph.removeElement(10);

    assertFalse(automataGraph.hasAtLeastOneFinalState());
  }

  @Test
  void whenInsertingAnElementAsFinalState_thenIsAddedToFinalStateSet_andCardinalityIncrements() {
    automataGraph.insertAsFinalState(10);

    assertTrue(automataGraph.hasAtLeastOneFinalState());
    assertEquals(2, automataGraph.cardinality());
  }

  @Test
  void whenElementAlreadyExistsAsFinalState_thenReturnsFalse() {
    automataGraph.insertAsFinalState(10);

    boolean shouldBeFalse = automataGraph.insertAsFinalState(10);

    assertFalse(shouldBeFalse);
  }

}
