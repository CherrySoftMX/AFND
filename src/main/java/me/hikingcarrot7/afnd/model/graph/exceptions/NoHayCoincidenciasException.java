package me.hikingcarrot7.afnd.model.graph.exceptions;

public class NoHayCoincidenciasException extends RuntimeException {
  /**
   * Creates a new instance of <code>NoHayCoincidenciasException</code> without detail message.
   */
  public NoHayCoincidenciasException() {
    this("No se encontraron coincidencias!");
  }

  /**
   * Constructs an instance of <code>NoHayCoincidenciasException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public NoHayCoincidenciasException(String msg) {
    super(msg);
  }
}
