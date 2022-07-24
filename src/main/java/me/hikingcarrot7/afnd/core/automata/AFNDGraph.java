package me.hikingcarrot7.afnd.core.automata;

import me.hikingcarrot7.afnd.core.automata.exceptions.CadenaVaciaException;
import me.hikingcarrot7.afnd.core.automata.exceptions.NoExisteEstadoInicialException;
import me.hikingcarrot7.afnd.core.graphs.AdjacentListGraph;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.graphs.Node;
import me.hikingcarrot7.afnd.core.graphs.exceptions.NodoNoExistenteException;
import me.hikingcarrot7.afnd.core.utils.Pair;

import java.util.*;

public class AFNDGraph<T> extends AdjacentListGraph<T> {
  private Node<T> initialState;
  private final List<Pair<Connection<?>, String>> recorrido;
  private final Set<Node<?>> finalStates;

  public AFNDGraph() {
    finalStates = new HashSet<>();
    recorrido = new ArrayList<>();
  }

  public boolean matches(String cadena) throws NoExisteEstadoInicialException, CadenaVaciaException {
    if (!hasInitialState()) {
      throw new NoExisteEstadoInicialException("Aún no has establecido el estado inicial");
    }
    if (cadena.isEmpty()) {
      throw new CadenaVaciaException("La cadena está vacía");
    }
    return matches(initialState, new AFNDInput(cadena));
  }

  private boolean matches(Node<?> destino, AFNDInput string) {
    if (finalStates.contains(destino) && string.isEmpty()) {
      return true;
    }
    if (string.isEmpty()) {
      return false;
    }
    for (int i = 0; i < destino.getConnections().size(); i++) {
      Connection<?> connection = destino.getConnections().get(i);
      if (connection.getCondition().toString().equals(string.getFirstCharacter())) {
        AFNDInput copiaCadena = string.getCopy();
        copiaCadena.consumirCharacter();
        if (matches(connection.getDestination(), copiaCadena)) {
          recorrido.add(new Pair<>(connection, copiaCadena.getCadena()));
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void removeElement(T element) throws NodoNoExistenteException {
    Node<T> node = getNode(element);
    if (node == initialState) {
      setInitialState(null);
    }
    removeFinalState(node);
    super.removeElement(element);
  }

  public void setInitialState(Node<T> initialState) {
    this.initialState = initialState;
  }

  public boolean hasInitialState() {
    return initialState != null;
  }

  public void addFinalState(Node<T> finalState) {
    finalStates.add(finalState);
  }

  public void removeFinalState(Node<T> finalState) {
    finalStates.remove(finalState);
  }

  public void clearRecorrido() {
    recorrido.clear();
  }

  public List<Pair<Connection<?>, String>> getRecorrido() {
    List<Pair<Connection<?>, String>> reversed = new ArrayList<>(recorrido);
    Collections.reverse(reversed);
    return reversed;
  }

}
