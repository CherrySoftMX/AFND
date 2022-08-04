package me.hikingcarrot7.afnd.core.graphs;

import me.hikingcarrot7.afnd.core.graphs.exceptions.ConnectionNotFoundException;
import me.hikingcarrot7.afnd.core.graphs.exceptions.ElementNotFoundException;
import me.hikingcarrot7.afnd.core.graphs.exceptions.MaxCapacityReachedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    Node<T> newNode = createNode(element);
    adjTable.add(newNode);
    elementCount++;
    return true;
  }

  protected Node<T> createNode(T element) {
    return new GraphNode<>(element);
  }

  @Override
  public boolean removeElement(T element) {
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
      return originNode.addConnection(createConnection(originNode, destinationNode, element));
    }
    return false;
  }

  protected Connection<?> createConnection(Node<T> origin, Node<T> destination, Object element) {
    return new GraphConnection<>(origin, destination, element);
  }

  @Override
  public boolean removeConnection(T origin, T destination) {
    if (existConnection(origin, destination)) {
      Node<T> originNode = getNode(origin);
      Node<T> destinationNode = getNode(destination);
      return originNode.removeConnection(destinationNode);
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
      if (adjTable.get(i).element().equals(element)) {
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

  protected List<Node<T>> getNodes() {
    return adjTable;
  }

  protected List<Connection<?>> getConnections() {
    List<Connection<?>> connections = new ArrayList<>();
    for (int i = 0; i < cardinality(); i++) {
      connections.addAll(adjTable.get(i).getConnections());
    }
    return connections;
  }

  protected List<Connection<?>> getAdjacentConnectionsFor(String origin) {
    List<Connection<?>> allConnections = getConnections();
    return allConnections.stream()
        .filter(conn -> origin.equals(conn.getOrigin().element()))
        .collect(Collectors.toList());
  }

  public boolean hasAdjacentConnections(String origin) {
    return !getAdjacentConnectionsFor(origin).isEmpty();
  }

  protected Connection<?> getConnection(T origin, T destination) {
    Node<T> originNode = getNode(origin);
    return originNode.getConnections().stream()
        .filter(connection -> connection.getDestination().element().equals(destination))
        .findAny()
        .orElseThrow(() -> new ConnectionNotFoundException(origin.toString(), destination.toString()));
  }

  public boolean hasAtLeastOneNode() {
    return cardinality() > 1;
  }

  private void throwIfMaxCapacityReached() {
    if (cardinality() == MAX_NODES) {
      throw new MaxCapacityReachedException();
    }
  }

}
