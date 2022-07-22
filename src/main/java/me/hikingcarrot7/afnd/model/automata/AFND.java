package me.hikingcarrot7.afnd.model.automata;

import me.hikingcarrot7.afnd.model.automata.exceptions.CadenaVaciaException;
import me.hikingcarrot7.afnd.model.automata.exceptions.NoExisteEstadoInicialException;
import me.hikingcarrot7.afnd.model.graph.Arch;
import me.hikingcarrot7.afnd.model.graph.GrafoListaAdy;
import me.hikingcarrot7.afnd.model.graph.Node;
import me.hikingcarrot7.afnd.model.graph.exceptions.NodoNoExistenteException;
import me.hikingcarrot7.afnd.model.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AFND<E> extends GrafoListaAdy<E> {

  private Node<E> estadoInicial;
  private final List<Pair<Arch<?>, String>> recorrido;
  private List<Node<?>> estadosFinales;

  public AFND() {
    estadosFinales = new ArrayList<>();
    recorrido = new ArrayList<>();
  }

  public boolean matches(char[] cadena) throws NoExisteEstadoInicialException, CadenaVaciaException {
    return matches(new String(cadena));
  }

  public boolean matches(String cadena) throws NoExisteEstadoInicialException, CadenaVaciaException {
    if (!estadoInicialEstablecido()) {
      throw new NoExisteEstadoInicialException("Aún no has establecido el estado inicial");
    }
    if (cadena.isEmpty()) {
      throw new CadenaVaciaException("La cadena está vacía");
    }
    return matches(estadoInicial, new AFNDString(cadena));
  }

  private boolean matches(Node<?> destino, AFNDString string) {
    if (estadosFinales.contains(destino) && string.isEmpty()) {
      return true;
    }
    if (string.isEmpty()) {
      return false;
    }
    for (int i = 0; i < destino.getArchs().size(); i++) {
      Arch<?> arch = destino.getArchs().get(i);
      if (arch.getCondicion().toString().equals(string.getFirstCharacter())) {
        AFNDString copiaCadena = string.getCopy();
        copiaCadena.consumirCharacter();
        if (matches(arch.getDestino(), copiaCadena)) {
          recorrido.add(new Pair<>(arch, copiaCadena.getCadena()));
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void removeNode(E name) throws NodoNoExistenteException {
    Node<E> node = getNode(name);
    if (node == estadoInicial) {
      setEstadoInicial(null);
    }
    estadosFinales.remove(node);
    super.removeNode(name);
  }

  public Node<E> getEstadoInicial() {
    return estadoInicial;
  }

  public void setEstadoInicial(Node<E> estadoInicial) {
    this.estadoInicial = estadoInicial;
  }

  public boolean estadoInicialEstablecido() {
    return estadoInicial != null;
  }

  public void addEstadoFinal(Node<E> estadoFinal) {
    if (!estadosFinales.contains(estadoFinal)) {
      estadosFinales.add(estadoFinal);
    }
  }

  public void removeEstadoFinal(Node<E> estadoFinal) {
    estadosFinales.remove(estadoFinal);
  }

  public List<Node<?>> getEstadosFinales() {
    return estadosFinales;
  }

  public void setEstadosFinales(List<Node<?>> estadosFinales) {
    this.estadosFinales = estadosFinales;
  }

  public void clearRecorrido() {
    recorrido.clear();
  }

  public List<Pair<Arch<?>, String>> getRecorrido() {
    List<Pair<Arch<?>, String>> reversed = new ArrayList<>(recorrido);
    Collections.reverse(reversed);
    return reversed;
  }
}
