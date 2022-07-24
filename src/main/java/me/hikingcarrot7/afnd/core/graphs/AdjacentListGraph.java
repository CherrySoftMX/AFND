package me.hikingcarrot7.afnd.core.graphs;

import me.hikingcarrot7.afnd.core.graphs.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class AdjacentListGraph<T> extends Graph<T> {
  public static final int MAX_NODES = 50;
  private final List<Node<T>> adjTable;

  public AdjacentListGraph() {
    adjTable = new ArrayList<>();
  }

  @Override
  public void insertElement(T element) throws GrafoLlenoException, NodoYaExisteException {
    throwIfMaxCapacityReached();
    if (existElement(element)) {
      return;
    }
    Node<T> newNode = new Node<>(element);
    adjTable.add(newNode);
    elementCount++;
  }

  public void addNode(Node<T> node) throws GrafoLlenoException, NodoYaExisteException {
    if (cardinality() == MAX_NODES) {
      throw new GrafoLlenoException();
    }
    if (indexOf(node.getElement()) >= 0) {
      throw new NodoYaExisteException();
    }
    adjTable.add(node);
    elementCount++;
  }

  @Override
  public void removeElement(T element) throws NodoNoExistenteException {
    if (existElement(element)) {
      Node<T> nodeToRemove = getNode(element);
      adjTable.remove(nodeToRemove);
      elementCount--;
      removeAllRefsTo(nodeToRemove);
    }
  }

  private void removeAllRefsTo(Node<T> nodeToRemove) {
    adjTable.forEach(node -> {
      node.getConnections()
          .removeIf(conn -> conn.getDestination().equals(nodeToRemove));
    });
  }

  @Override
  public void insertConnection(T origin, T destination, Object element) {
    boolean noConnection = !existConnection(origin, destination);
    boolean bothElementsExist = existElements(origin, destination);
    if (noConnection && bothElementsExist) {
      Node<T> originNode = getNode(origin);
      Node<T> destinationNode = getNode(destination);
      originNode.addConnection(destinationNode, element);
    }
  }

  @Override
  public void removeConnection(T origin, T destination) {
    if (existConnection(origin, destination)) {
      Node<T> originNode = getNode(origin);
      Node<T> destinationNode = getNode(destination);
      originNode.getConnections()
          .removeIf(conn -> conn.getDestination().equals(destinationNode));
    }
  }

  @Override
  public boolean existConnection(T origin, T destination) {
    if (existElements(origin, destination)) {
      Node<T> originNode = getNode(origin);
      Node<T> destinationNode = getNode(destination);
      return originNode.getConnections()
          .stream()
          .anyMatch(conn -> conn.getDestination() == destinationNode);
    }
    return false;
  }

  @Override
  public int indexOf(Object element) {
    for (int i = 0; i < cardinality(); i++) {
      if (adjTable.get(i).getElement().equals(element)) {
        return i;
      }
    }
    return -1;
  }

  protected Node<T> getNode(T element) {
    int numNode = indexOf(element);
    if (numNode >= 0) {
      return adjTable.get(numNode);
    }
    throw new ElementNotFoundException(element);
  }

  private void throwIfMaxCapacityReached() {
    if (cardinality() == MAX_NODES) {
      throw new MaxCapacityReachedException();
    }
  }

}
