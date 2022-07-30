package me.hikingcarrot7.afnd.core.graphs;

import java.util.List;

public interface Node<T> {

  T element();

  void setElement(T element);

  List<Connection<?>> getConnections();

  boolean addConnection(Connection<?> connection);

  boolean removeConnection(Node<T> destination);

}
