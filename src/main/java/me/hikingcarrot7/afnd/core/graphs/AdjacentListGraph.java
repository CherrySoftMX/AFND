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
  public boolean insertElement(T element) {
    throwIfMaxCapacityReached();
    if (existElement(element)) {
      return false;
    }
    Node<T> newNode = new Node<>(element);
    adjTable.add(newNode);
    elementCount++;
    return true;
  }

  @Override
  public boolean removeElement(T element) throws NodoNoExistenteException {
    if (existElement(element)) {
      Node<T> nodeToRemove = getNode(element);
      adjTable.remove(nodeToRemove);
      elementCount--;
      removeAllRefsTo(nodeToRemove);
      return true;
    }
    return false;
  }

  private void removeAllRefsTo(Node<T> nodeToRemove) {
    adjTable.forEach(node -> {
      node.getConnections()
          .removeIf(conn -> conn.getDestination().equals(nodeToRemove));
    });
  }

  @Override
  public boolean insertConnection(T origin, T destination, Object element) {
    boolean noConnection = !existConnection(origin, destination);
    boolean bothElementsExist = existElements(origin, destination);
    if (noConnection && bothElementsExist) {
      Node<T> originNode = getNode(origin);
      Node<T> destinationNode = getNode(destination);
      originNode.addConnection(destinationNode, element);
      return true;
    }
    return false;
  }

  @Override
  public boolean removeConnection(T origin, T destination) {
    if (existConnection(origin, destination)) {
      Node<T> originNode = getNode(origin);
      Node<T> destinationNode = getNode(destination);
      originNode.getConnections()
          .removeIf(conn -> conn.getDestination().equals(destinationNode));
      return true;
    }
    return false;
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

  protected Node<T> getNode(Object element) {
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
