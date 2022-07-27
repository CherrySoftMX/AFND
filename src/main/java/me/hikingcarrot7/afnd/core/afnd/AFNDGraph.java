package me.hikingcarrot7.afnd.core.afnd;

import me.hikingcarrot7.afnd.core.graphs.AdjacentListGraph;
import me.hikingcarrot7.afnd.core.graphs.Node;

import java.util.HashSet;
import java.util.Set;

public class AFNDGraph<T> extends AdjacentListGraph<T> {
  private Node<T> initialState;
  private final Set<Node<?>> finalStates;
  private final AFNDResolver resolver;

  public AFNDGraph() {
    finalStates = new HashSet<>();
    resolver = new AFNDResolver(this);
  }

  public MatchResult matches(String input) {
    return resolver.matches(input);
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

}
