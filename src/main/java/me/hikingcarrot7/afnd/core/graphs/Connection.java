package me.hikingcarrot7.afnd.core.graphs;

public interface Connection<T> {

  Node<?> getOrigin();

  Node<?> getDestination();

  T getCondition();

}
