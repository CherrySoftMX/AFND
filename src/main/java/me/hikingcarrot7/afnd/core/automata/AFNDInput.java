package me.hikingcarrot7.afnd.core.automata;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AFNDInput implements Iterable<Character> {
  private String input;

  public AFNDInput(String input) {
    this.input = input;
  }

  public String getFirstChar() {
    if (!isEmpty()) {
      return String.valueOf(input.charAt(0));
    }
    throw new IllegalStateException("Input is empty!");
  }

  public boolean removeFirstChar() {
    if (isEmpty()) {
      return false;
    }
    input = input.substring(1);
    return true;
  }

  public boolean isEmpty() {
    return input.isEmpty();
  }

  public AFNDInput makeCopy() {
    return new AFNDInput(input);
  }

  public String getAsString() {
    return toString();
  }

  @Override
  public String toString() {
    return input;
  }

  @Override
  public Iterator<Character> iterator() {
    return input
        .chars()
        .mapToObj(c -> (char) c)
        .collect(Collectors.toUnmodifiableList())
        .iterator();
  }

}
