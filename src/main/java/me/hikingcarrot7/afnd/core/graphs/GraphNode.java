package me.hikingcarrot7.afnd.core.graphs;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class GraphNode<T> implements Node<T> {
  @Accessors(fluent = true)
  private T element;
  private final List<Connection<?>> connections;

  public GraphNode(T element) {
    this.element = element;
    this.connections = new ArrayList<>();
  }

  @Override
  public void setElement(T element) {
    this.element = element;
  }

  public boolean addConnection(Connection<?> connection) {
    if (!connection.getOrigin().equals(this)) {
      return false;
    }
    connections.add(connection);
    return true;
  }

  public boolean removeConnection(Node<T> destination) {
    return connections.removeIf(conn -> conn.getDestination().equals(destination));
  }

}
