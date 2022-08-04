package me.hikingcarrot7.afnd.core.graphs;

import me.hikingcarrot7.afnd.core.graphs.exceptions.ArcoNoExistenteException;
import me.hikingcarrot7.afnd.core.graphs.exceptions.GrafoLlenoException;
import me.hikingcarrot7.afnd.core.graphs.exceptions.NodoNoExistenteException;
import me.hikingcarrot7.afnd.core.graphs.exceptions.NodoYaExisteException;

import java.util.stream.Stream;

public abstract class Graph<E> {
  protected int elementCount;

  public abstract boolean insertElement(E element) throws GrafoLlenoException, NodoYaExisteException;

  public abstract boolean removeElement(E element);

  public abstract boolean insertConnection(E origin, E destination, Object element) throws NodoNoExistenteException;

  public abstract boolean removeConnection(E origin, E destination) throws ArcoNoExistenteException, NodoNoExistenteException;

  public abstract boolean existConnection(E origin, E destination) throws NodoNoExistenteException;

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
