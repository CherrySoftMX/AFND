package me.hikingcarrot7.afnd.model.automata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AFNDString {

  private String afndString;

  public AFNDString(String cadena) {
    this(cadena.toCharArray());
  }

  public AFNDString(char[] afndString) {
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

  public AFNDString getCopy() {
    return new AFNDString(afndString);
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
