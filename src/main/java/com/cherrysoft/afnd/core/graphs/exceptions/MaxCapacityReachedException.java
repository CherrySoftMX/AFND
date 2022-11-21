package com.cherrysoft.afnd.core.graphs.exceptions;

import com.cherrysoft.afnd.core.graphs.AdjacentListGraph;

public class MaxCapacityReachedException extends RuntimeException {

  public MaxCapacityReachedException() {
    super("Max capacity reached: " + AdjacentListGraph.MAX_NODES);
  }

}
