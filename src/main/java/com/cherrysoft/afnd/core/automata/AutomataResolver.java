package com.cherrysoft.afnd.core.automata;

import com.cherrysoft.afnd.core.graphs.Connection;
import com.cherrysoft.afnd.core.graphs.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutomataResolver {
  private final AutomataGraph<?> afnd;
  private List<MatchResultStep> path;
  private MatchResult latestMatchResult;

  public AutomataResolver(AutomataGraph<?> afnd) {
    this.afnd = afnd;
    this.path = new ArrayList<>();
  }

  public MatchResult matches(String input) {
    return matches(new AutomataInput(input));
  }

  public MatchResult matches(AutomataInput input) {
    ensureInputIsValid(input);
    ensureAFNDIsInValidState();
    boolean matches = matches(afnd.getInitialState(), input);
    Collections.reverse(path);
    latestMatchResult = new MatchResult(matches, path);
    path = new ArrayList<>();
    return latestMatchResult;
  }

  private void ensureInputIsValid(AutomataInput input) {
    if (input.isEmpty()) {
      throw new IllegalStateException("La cadena está vacía");
    }
  }

  private void ensureAFNDIsInValidState() {
    if (!afnd.hasInitialState()) {
      throw new IllegalStateException("Aún no has establecido el estado inicial");
    }
  }

  private boolean matches(Node<?> destination, AutomataInput input) {
    if (afnd.isFinalState(destination.element()) && input.isEmpty()) {
      return true;
    }
    if (input.isEmpty()) {
      return false;
    }
    for (int i = 0; i < destination.getConnections().size(); i++) {
      Connection<?> connection = destination.getConnections().get(i);
      if (connection.getCondition().equals(input.getFirstChar())) {
        AutomataInput inputCopy = input.makeCopy();
        inputCopy.removeFirstChar();
        if (matches(connection.getDestination(), inputCopy)) {
          path.add(new MatchResultStep(connection, inputCopy.toString()));
          return true;
        }
      }
    }
    return false;
  }

  public MatchResult getLatestMatchResult() {
    return latestMatchResult;
  }

}
