package me.hikingcarrot7.afnd.core.graphs.exceptions;

import me.hikingcarrot7.afnd.core.graphs.AdjacentListGraph;

public class MaxCapacityReachedException extends RuntimeException {

  public MaxCapacityReachedException() {
    super("Max capacity reached: " + AdjacentListGraph.MAX_NODES);
  }

}
