package com.cherrysoft.afnd.core.graphs.exceptions;

public class ConnectionNotFoundException extends RuntimeException {

  public ConnectionNotFoundException(String origin, String destination) {
    super(String.format("Connection between %s and %s doesn't exist", origin, destination));
  }

}
