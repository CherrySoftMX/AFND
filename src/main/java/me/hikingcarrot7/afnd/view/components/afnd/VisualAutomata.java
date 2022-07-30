package me.hikingcarrot7.afnd.view.components.afnd;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.graphs.Node;

import java.awt.*;

public class VisualAutomata extends AFNDGraph<String> {

  public boolean insertElement(String element, Point pos) {
    boolean elementInserted = insertElement(element);
    if (elementInserted) {
      VisualNode node = getVisualNode(element);
      node.setPos(pos);
    }
    return elementInserted;
  }

  @Override
  protected Node<String> createNode(String element) {
    return new VisualNode(element);
  }

  public VisualNode getVisualNode(Object element) {
    return (VisualNode) getNode(element);
  }

}
