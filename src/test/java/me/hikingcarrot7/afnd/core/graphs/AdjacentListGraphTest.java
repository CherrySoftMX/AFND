package me.hikingcarrot7.afnd.core.graphs;

import me.hikingcarrot7.afnd.core.graphs.exceptions.MaxCapacityReachedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class AdjacentListGraphTest {
  private Graph<Integer> graph;

  @BeforeEach
  public void init() {
    graph = new AdjacentListGraph<>();
  }

  @Test
  void whenAddingElements_thenElementExists_andGraphIsNotBeEmpty() {
    graph.insertElement(1);
    graph.insertElement(2);
    graph.insertElement(3);

    assertTrue(graph.existElement(3));
    assertTrue(graph.existElement(1));
    assertTrue(graph.existElement(2));
    assertFalse(graph.isEmpty());
  }

  @Test
  void whenAddingAnExistingElement_thenReturnsFalse() {
    graph.insertElement(5);

    boolean shouldBeFalse = graph.insertElement(5);

    assertFalse(shouldBeFalse);
  }

  @Test
  void whenMaxCapacityReached_thenThrowsAnException() {
    IntStream.range(0, 50).forEach(i -> graph.insertElement(i));

    assertThrows(MaxCapacityReachedException.class, () -> {
      graph.insertElement(5);
    });
  }

  @Test
  void whenElementRemoved_thenAllRefsAreRemovedToo() {
    graph.insertElement(5);
    graph.insertElement(10);

    graph.insertConnection(5, 10, "a");

    graph.removeElement(5);

    assertFalse(graph.existConnection(5, 10));
  }

  @Test
  void whenConnectionAdded_thenInsertConnectionReturnsTrue_andExistConnectionReturnsTrue() {
    graph.insertElement(5);
    graph.insertElement(10);

    boolean shouldBeTrue = graph.insertConnection(5, 10, "a");

    assertTrue(shouldBeTrue);
    assertTrue(graph.existConnection(5, 10));
  }

  @Test
  void whenConnectionAlreadyExist_thenInsertConnectionReturnsFalse() {
    graph.insertElement(5);
    graph.insertElement(10);
    graph.insertConnection(5, 10, "a");

    boolean shouldBeFalse = graph.insertConnection(5, 10, "b");

    assertFalse(shouldBeFalse);
  }

  @Test
  void whenElementsDoesNotExists_thenInsertConnectionReturnsFalse() {
    boolean shouldBeFalse = graph.insertConnection(5, 10, "a");

    assertFalse(shouldBeFalse);
  }

  @Test
  void whenConnectionRemoved_thenExistConnectionReturnsFalse() {
    graph.insertElement(5);
    graph.insertElement(10);
    graph.insertConnection(5, 10, "a");

    graph.removeConnection(5, 10);

    assertFalse(graph.existConnection(5, 10));
  }

  @Test
  void whenConnectionDoesNotExist_thenRemoveConnectionReturnsFalse() {
    graph.insertElement(5);
    graph.insertElement(10);

    boolean shouldBeFalse = graph.removeConnection(5, 10);

    assertFalse(shouldBeFalse);
  }

}