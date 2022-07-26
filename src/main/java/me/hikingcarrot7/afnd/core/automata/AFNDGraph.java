package me.hikingcarrot7.afnd.core.automata;

import me.hikingcarrot7.afnd.core.graphs.AdjacentListGraph;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.graphs.Node;
import me.hikingcarrot7.afnd.core.graphs.exceptions.ElementNotFoundException;
import me.hikingcarrot7.afnd.core.utils.Pair;

import java.util.*;

public class AFNDGraph<T> extends AdjacentListGraph<T> {
  private Node<T> initialState;
  private final List<Pair<Connection<?>, String>> path;
  private final Set<Node<?>> finalStates;

  public AFNDGraph() {
    finalStates = new HashSet<>();
    path = new ArrayList<>();
  }

  public boolean matches(String input) {
    if (!hasInitialState()) {
      throw new IllegalStateException("Aún no has establecido el estado inicial");
    }
    if (input.isEmpty()) {
      throw new IllegalStateException("La cadena está vacía");
    }
    return matches(initialState, new AFNDInput(input));
  }

  private boolean matches(Node<?> destination, AFNDInput input) {
    if (finalStates.contains(destination) && input.isEmpty()) {
      return true;
    }
    if (input.isEmpty()) {
      return false;
    }
    for (int i = 0; i < destination.getConnections().size(); i++) {
      Connection<?> connection = destination.getConnections().get(i);
      if (connection.getCondition().toString().equals(input.getFirstChar())) {
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

  @Override
  public boolean removeElement(T element) {
    Node<T> elementNode = getNode(element);
    if (elementNode == initialState) {
      initialState = null;
    }
    finalStates.remove(elementNode);
    return super.removeElement(element);
  }

  public boolean setInitialState(T element) {
    try {
      tryToSetInitialState(element);
      return true;
    } catch (ElementNotFoundException e) {
      return false;
    }
  }

  private void tryToSetInitialState(T element) throws ElementNotFoundException {
    initialState = getNode(element);
  }

  public boolean insertAsInitialState(T element) {
    boolean elementInserted = insertElement(element);
    if (elementInserted) {
      initialState = getNode(element);
    }
    return elementInserted;
  }

  public boolean insertAsFinalState(T element) {
    boolean elementInserted = insertElement(element);
    if (elementInserted) {
      Node<T> elementNode = getNode(element);
      finalStates.add(elementNode);
    }
    return elementInserted;
  }

  public boolean insertAsInitialAndFinalState(T element) {
    boolean elementInserted = insertElement(element);
    if (elementInserted) {
      Node<T> elementNode = getNode(element);
      initialState = elementNode;
      finalStates.add(elementNode);
    }
    return elementInserted;
  }

  public boolean hasInitialState() {
    return initialState != null;
  }

  public boolean hasAtLeastOneFinalState() {
    return finalStates.size() > 0;
  }

  public boolean isFinalState(Object element) {
    Node<T> elementNode = getNode(element);
    return finalStates.contains(elementNode);
  }

  Node<T> getInitialState() {
    return initialState;
  }

  public void clearPath() {
    path.clear();
  }

  public List<Pair<Connection<?>, String>> getPath() {
    List<Pair<Connection<?>, String>> reversed = new ArrayList<>(path);
    Collections.reverse(reversed);
    return reversed;
  }

}
