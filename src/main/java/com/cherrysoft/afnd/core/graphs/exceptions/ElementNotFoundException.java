package com.cherrysoft.afnd.core.graphs.exceptions;

public class ElementNotFoundException extends RuntimeException {

  public ElementNotFoundException(Object element) {
    super("Element: " + element + " doesn't exist!");
  }

}
