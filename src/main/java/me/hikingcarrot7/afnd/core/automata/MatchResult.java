package me.hikingcarrot7.afnd.core.automata;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.utils.Pair;

import java.util.Collections;
import java.util.List;

@Getter
public class MatchResult {
  @Accessors(fluent = true)
  private final boolean matches;
  private List<Pair<Connection<?>, String>> path;

  public MatchResult(boolean matches, List<Pair<Connection<?>, String>> path) {
    this.matches = matches;
    this.path = path;
    if (!matches()) {
      this.path = Collections.emptyList();
    }
  }

}
