package me.hikingcarrot7.afnd.core.automata;

import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.graphs.Node;
import me.hikingcarrot7.afnd.core.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AFNDResolver {
  private final AFNDGraph<?> afnd;
  private List<Pair<Connection<?>, String>> path;
  private MatchResult latestMatchResult;

  public AFNDResolver(AFNDGraph<?> afnd) {
    this.afnd = afnd;
    this.path = new ArrayList<>();
  }

  public MatchResult matches(AFNDInput input) {
    ensureInputIsValid(input);
    ensureAFNDIsInValidState();
    boolean matches = matches(afnd.getInitialState(), input);
    Collections.reverse(path);
    latestMatchResult = new MatchResult(matches, path);
    path = new ArrayList<>();
    return latestMatchResult;
  }

  private void ensureInputIsValid(AFNDInput input) {
    if (input.isEmpty()) {
      throw new IllegalStateException("La cadena está vacía");
    }
  }

  private void ensureAFNDIsInValidState() {
    if (!afnd.hasInitialState()) {
      throw new IllegalStateException("Aún no has establecido el estado inicial");
    }
  }

  private boolean matches(Node<?> destination, AFNDInput input) {
    if (afnd.isFinalState(destination.getElement()) && input.isEmpty()) {
      return true;
    }
    if (input.isEmpty()) {
      return false;
    }
    for (int i = 0; i < destination.getConnections().size(); i++) {
      Connection<?> connection = destination.getConnections().get(i);
      if (connection.getCondition().equals(input.getFirstChar())) {
        AFNDInput inputCopy = input.makeCopy();
        inputCopy.removeFirstChar();
        if (matches(connection.getDestination(), inputCopy)) {
          path.add(new Pair<>(connection, inputCopy.getAsString()));
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
