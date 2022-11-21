package com.cherrysoft.afnd.core.automata;

import com.cherrysoft.afnd.core.graphs.Connection;
import com.cherrysoft.afnd.core.utils.Pair;
import lombok.ToString;

@ToString
public class MatchResultStep extends Pair<Connection<?>, String> {

  public MatchResultStep(Connection<?> connection, String inputSnapshot) {
    super(connection, inputSnapshot);
  }

  public Connection<?> getConnection() {
    return getLeft();
  }

  public String inputSnapshot() {
    return getRight();
  }

}
