package me.hikingcarrot7.afnd.core.graphs.exceptions;

public class ElementNotFoundException extends RuntimeException {

  public ElementNotFoundException(Object element) {
    super("Element: " + element + " doesn't exist!");
  }

}
