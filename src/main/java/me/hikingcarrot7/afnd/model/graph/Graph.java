package me.hikingcarrot7.afnd.model.graph;

import me.hikingcarrot7.afnd.model.graph.exceptions.ArcoNoExistenteException;
import me.hikingcarrot7.afnd.model.graph.exceptions.GrafoLlenoException;
import me.hikingcarrot7.afnd.model.graph.exceptions.NodoNoExistenteException;
import me.hikingcarrot7.afnd.model.graph.exceptions.NodoYaExisteException;

import java.util.List;

public abstract class Graph<E> {

  public final static int MAX_NUMERO_VERTICES = 50;

  protected int numeroVertices;

  public abstract void addNode(E datoVertice) throws GrafoLlenoException, NodoYaExisteException;

  public abstract void removeNode(E datoVertice) throws NodoNoExistenteException;

  public abstract void addArch(E origen, E destino, Object dato) throws NodoNoExistenteException;

  public abstract void removeArch(E origen, E destino) throws ArcoNoExistenteException, NodoNoExistenteException;

  public abstract boolean existeConexion(E origen, E destino) throws NodoNoExistenteException;

  public abstract int numNode(E name);

  public abstract Node<E> getNode(E name);

  public boolean existeVertice(E name) {
    return numNode(name) >= 0;
  }

  public abstract List<Node<E>> getVertices();

  public Arch<?> getArco(E origen, E destino) throws NodoNoExistenteException, ArcoNoExistenteException {
    if (existeConexion(origen, destino)) {
      Node<E> nodoOrigen = getNode(origen);
      Node<E> nodoDestino = getNode(destino);
      for (Arch<?> arch : nodoOrigen.getArchs()) {
        if (arch.getDestino() == nodoDestino) {
          return arch;
        }
      }
    }
    throw new ArcoNoExistenteException();
  }

  public abstract List<Arch> getArcos();

  public boolean isEmpty() {
    return numeroVertices == 0;
  }

  public int getNumeroVertices() {
    return numeroVertices;
  }

}
