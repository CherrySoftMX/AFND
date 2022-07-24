package me.hikingcarrot7.afnd.core.automata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AFNDInput {

  private String afndString;

  public AFNDInput(String cadena) {
    this(cadena.toCharArray());
  }

  public AFNDInput(char[] afndString) {
    this.afndString = new String(afndString);
  }

  public boolean consumirCharacter() {
    if (isEmpty()) {
      return false;
    }
    afndString = afndString.substring(1);
    return true;
  }

  public boolean isEmpty() {
    return afndString.isEmpty();
  }

  public AFNDInput getCopy() {
    return new AFNDInput(afndString);
  }

  public Iterator<Character> getIterator() {
    List<Character> list = new ArrayList<>();
    for (int i = 0; i < afndString.length(); i++) {
      list.add(afndString.charAt(i));
    }
    return list.iterator();
  }

  public String getCadena() {
    return afndString;
  }

  public String getFirstCharacter() {
    if (!isEmpty()) {
      return String.valueOf(afndString.charAt(0));
    }
    return null;
  }

  @Override
  public String toString() {
    return afndString;
  }

}
