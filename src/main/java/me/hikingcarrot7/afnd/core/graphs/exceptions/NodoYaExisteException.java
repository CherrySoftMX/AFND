package me.hikingcarrot7.afnd.core.graphs.exceptions;

public class NodoYaExisteException extends RuntimeException {
  /**
   * Creates a new instance of <code>NodoYaExiste</code> without detail message.
   */
  public NodoYaExisteException() {
    this("El vértice ya existe!");
  }

  /**
   * Constructs an instance of <code>NodoYaExiste</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public NodoYaExisteException(String msg) {
    super(msg);
  }
}
