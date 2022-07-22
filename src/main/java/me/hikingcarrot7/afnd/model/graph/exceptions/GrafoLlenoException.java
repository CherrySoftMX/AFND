package me.hikingcarrot7.afnd.model.graph.exceptions;

public class GrafoLlenoException extends RuntimeException {
  /**
   * Creates a new instance of <code>GrafoLlenoException</code> without detail message.
   */
  public GrafoLlenoException() {
    this("El grafo está lleno!");
  }

  /**
   * Constructs an instance of <code>GrafoLlenoException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public GrafoLlenoException(String msg) {
    super(msg);
  }
}
