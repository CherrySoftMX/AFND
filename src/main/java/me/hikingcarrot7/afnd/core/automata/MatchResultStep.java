package me.hikingcarrot7.afnd.core.automata;

import lombok.ToString;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.utils.Pair;

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
