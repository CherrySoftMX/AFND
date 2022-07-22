package me.hikingcarrot7.afnd.model.graph;

import me.hikingcarrot7.afnd.model.graph.exceptions.ArcoNoExistenteException;
import me.hikingcarrot7.afnd.model.graph.exceptions.GrafoLlenoException;
import me.hikingcarrot7.afnd.model.graph.exceptions.NodoNoExistenteException;
import me.hikingcarrot7.afnd.model.graph.exceptions.NodoYaExisteException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GrafoListaAdy<E> extends Graph<E> {
  private final List<Node<E>> tablaAdy;

  public GrafoListaAdy() {
    tablaAdy = new ArrayList<>();
  }

  @Override
  public void addNode(E name) throws GrafoLlenoException, NodoYaExisteException {
    addNode(new Node<>(name));
  }

  public void addNode(Node<E> node) throws GrafoLlenoException, NodoYaExisteException {
    if (getNumeroVertices() == MAX_NUMERO_VERTICES) {
      throw new GrafoLlenoException();
    }
    if (numNode(node.getName()) >= 0) {
      throw new NodoYaExisteException();
    }
    tablaAdy.add(node);
    numeroVertices++;
  }

  @Override
  public void removeNode(E name) throws NodoNoExistenteException {
    Node<E> node = getNode(name);
    if (node == null) {
      throw new NodoNoExistenteException("El vértice no existe!");
    }
    tablaAdy.remove(node);
    numeroVertices--;
    for (int i = 0; i < getNumeroVertices(); i++) {
      tablaAdy.get(i).getArchs().removeIf(arch -> arch.getDestino() == node);
    }
  }

  @Override
  public void addArch(E origen, E destino, Object dato) throws NodoNoExistenteException {
    if (!existeConexion(origen, destino)) {
      Node<E> nodoOrigen = getNode(origen);
      Node<E> nodoDestino = getNode(destino);
      nodoOrigen.addArch(new Arch<>(nodoOrigen, nodoDestino, dato));
    }
  }

  @Override
  public void removeArch(E origen, E destino) throws ArcoNoExistenteException, NodoNoExistenteException {
    if (!existeConexion(origen, destino)) {
      throw new ArcoNoExistenteException();
    }
    Node<E> nodoOrigen = getNode(origen);
    Node<E> nodoDestino = getNode(destino);
    nodoOrigen.getArchs().removeIf(arch -> arch.getDestino() == nodoDestino);
  }

  @Override
  public boolean existeConexion(E origen, E destino) throws NodoNoExistenteException {
    Node<E> nodoOrigen = getNode(origen);
    Node<E> nodoDestino = getNode(destino);
    if (nodoOrigen == null) {
      throw new NodoNoExistenteException("El vértice " + origen + " no existe!");
    }
    if (nodoDestino == null) {
      throw new NodoNoExistenteException("El vértice " + destino + " no existe!");
    }
    return nodoOrigen.getArchs()
      .stream()
      .anyMatch(arch -> arch.getDestino() == nodoDestino);
  }

  @Override
  public int numNode(E name) {
    for (int i = 0; i < getNumeroVertices(); i++) {
      if (tablaAdy.get(i).getName().equals(name)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public Node<E> getNode(E name) {
    int numNode = numNode(name);
    if (numNode >= 0) {
      return tablaAdy.get(numNode);
    }
    return null;
  }

  public void mostrarListaAdy() {
    for (int i = 0; i < getNumeroVertices(); i++) {
      System.out.print(String.format("%15s:[%d]>", tablaAdy.get(i).getName(), i));
      if (!tablaAdy.get(i).getArchs().isEmpty()) {
        System.out.print(String.format("[%s]", tablaAdy.get(i).getArchs().get(0).getDestino().getName()));
      }
      for (int j = 1; j < tablaAdy.get(i).getArchs().size(); j++) {
        System.out.print(String.format("->[%s]", tablaAdy.get(i).getArchs().get(j).getDestino().getName()));
      }
      System.out.println("");
    }
  }

  @Override
  public List<Node<E>> getVertices() {
    return tablaAdy;
  }

  @Override
  public List<Arch> getArcos() {
    List<Arch> arcos = new ArrayList<>();
    for (int i = 0; i < getNumeroVertices(); i++) {
      tablaAdy.get(i).getArchs().forEach(arcos::add);
    }
    return arcos;
  }

}
