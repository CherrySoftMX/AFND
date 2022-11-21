package com.cherrysoft.afnd.core.automata;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class MatchResult {
  @Accessors(fluent = true)
  private final boolean matches;
  private List<MatchResultStep> path;

  public MatchResult(boolean matches, List<MatchResultStep> path) {
    this.matches = matches;
    this.path = path;
    if (!matches()) {
      this.path = Collections.emptyList();
    }
  }

  public Iterator<MatchResultStep> pathIterator() {
    return path.iterator();
  }

}
