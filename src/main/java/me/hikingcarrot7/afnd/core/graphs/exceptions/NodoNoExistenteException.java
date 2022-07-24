package me.hikingcarrot7.afnd.core.graphs.exceptions;

public class NodoNoExistenteException extends RuntimeException {
  /**
   * Creates a new instance of <code>NodoNoExistente</code> without detail message.
   */
  public NodoNoExistenteException() {
    this("El vértice no existe!");
  }

  /**
   * Constructs an instance of <code>NodoNoExistente</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public NodoNoExistenteException(String msg) {
    super(msg);
  }
}
