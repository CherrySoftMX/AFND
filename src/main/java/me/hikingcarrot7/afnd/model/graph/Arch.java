package me.hikingcarrot7.afnd.model.graph;

import java.util.Objects;

public class Arch<E> {
  private Node<?> origen;
  private Node<?> destino;
  private E condicion;

  public Arch(Node<?> origen, Node<?> destino, E condicion) {
    this.origen = origen;
    this.destino = destino;
    this.condicion = condicion;
  }

  public Node<?> getDestino() {
    return destino;
  }

  public void setDestino(Node<?> destino) {
    this.destino = destino;
  }

  public Node<?> getOrigen() {
    return origen;
  }

  public void setOrigen(Node<?> origen) {
    this.origen = origen;
  }

  public E getCondicion() {
    return condicion;
  }

  public void setCondicion(E condicion) {
    this.condicion = condicion;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + Objects.hashCode(this.destino);
    hash = 59 * hash + Objects.hashCode(this.origen);
    hash = 59 * hash + Objects.hashCode(this.condicion);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Arch<?> other = (Arch<?>) obj;
    if (!Objects.equals(this.destino, other.destino)) {
      return false;
    }
    if (!Objects.equals(this.origen, other.origen)) {
      return false;
    }
    return Objects.equals(this.condicion, other.condicion);
  }

  @Override
  public String toString() {
    return "Arch{" + "origen=" + origen + ", destino=" + destino + ", dato=" + condicion + '}';
  }

}
