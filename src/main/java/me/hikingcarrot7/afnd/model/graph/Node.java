package me.hikingcarrot7.afnd.model.graph;

import java.util.ArrayList;
import java.util.List;

public class Node<E> {
  private E name;
  private final List<Arch<?>> archs;

  public Node(E name) {
    this.name = name;
    this.archs = new ArrayList<>();
  }

  public void addArch(Arch<?> arch) {
    archs.add(arch);
  }

  public void removeArch(Arch<?> arch) {
    archs.remove(arch);
  }

  public List<Arch<?>> getArchs() {
    return archs;
  }

  public E getName() {
    return name;
  }

  public void setName(E name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Node{" + "name=" + name + '}';
  }
}
