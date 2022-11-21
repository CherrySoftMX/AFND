package com.cherrysoft.afnd.core.graphs;

import com.cherrysoft.afnd.core.graphs.exceptions.MaxCapacityReachedException;

import java.util.stream.Stream;

public abstract class Graph<E> {
  protected int elementCount;

  public abstract boolean insertElement(E element) throws MaxCapacityReachedException;

  public abstract boolean removeElement(E element);

  public abstract boolean insertConnection(E origin, E destination, Object element);

  public abstract boolean removeConnection(E origin, E destination);

  public abstract boolean existConnection(E origin, E destination);

  public abstract int indexOf(Object element);

  public boolean existElement(Object element) {
    return indexOf(element) >= 0;
  }

  public boolean existElements(Object... elements) {
    return Stream.of(elements).allMatch(this::existElement);
  }

  public boolean isEmpty() {
    return elementCount == 0;
  }

  public int cardinality() {
    return elementCount;
  }

}
