package me.hikingcarrot7.afnd.core.graphs;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Node<T> {
  private final T element;
  private final List<Connection<?>> connections;

  public Node(T element) {
    this.element = element;
    this.connections = new ArrayList<>();
  }

  public void addConnection(Node<T> destination, Object element) {
    connections.add(new Connection<>(this, destination, element));
  }

}
