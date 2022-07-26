package me.hikingcarrot7.afnd.core.automata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AFNDGraphTest {
  private AFNDGraph<Integer> afndGraph;

  @BeforeEach
  void setUp() {
    afndGraph = new AFNDGraph<>();
    afndGraph.insertElement(5);
  }

  @Test
  void givenElementToRemove_whenIsInitialState_thenInitialStateIsSetToNull() {
    afndGraph.setInitialState(5);

    afndGraph.removeElement(5);

    assertFalse(afndGraph.hasInitialState());
  }

  @Test
  void givenElementToRemove_whenIsFinalState_thenIsRemovedFromFinalStatesSet() {
    afndGraph.insertAsFinalState(10);

    afndGraph.removeElement(10);

    assertFalse(afndGraph.hasAtLeastOneFinalState());
  }

  @Test
  void whenInsertingAnElementAsFinalState_thenIsAddedToFinalStateSet_andCardinalityIncrements() {
    afndGraph.insertAsFinalState(10);

    assertTrue(afndGraph.hasAtLeastOneFinalState());
    assertEquals(2, afndGraph.cardinality());
  }

  @Test
  void whenElementAlreadyExistsAsFinalState_thenReturnsFalse() {
    afndGraph.insertAsFinalState(10);

    boolean shouldBeFalse = afndGraph.insertAsFinalState(10);

    assertFalse(shouldBeFalse);
  }

}