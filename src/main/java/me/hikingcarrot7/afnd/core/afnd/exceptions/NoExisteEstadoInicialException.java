package me.hikingcarrot7.afnd.core.afnd.exceptions;

public class NoExisteEstadoInicialException extends RuntimeException {
  /**
   * Creates a new instance of <code>NoExisteEstadoInicialException</code> without detail message.
   */
  public NoExisteEstadoInicialException() {
  }

  /**
   * Constructs an instance of <code>NoExisteEstadoInicialException</code> with the specified detail message.
   *
   * @param msg the detail message.
   */
  public NoExisteEstadoInicialException(String msg) {
    super(msg);
  }
}
