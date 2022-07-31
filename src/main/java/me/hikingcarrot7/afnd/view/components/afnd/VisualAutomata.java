package me.hikingcarrot7.afnd.view.components.afnd;

import lombok.RequiredArgsConstructor;
import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.graphs.Node;
import me.hikingcarrot7.afnd.view.components.afnd.states.AFNDStateFactory;

import java.awt.*;
import java.util.UUID;

@RequiredArgsConstructor
public class VisualAutomata extends AFNDGraph<String> {
  private final AFNDStateFactory stateFactory;
  private int stateId;

  public boolean insertElement(String element, Point pos) {
    boolean elementInserted = insertElement(element);
    if (elementInserted) {
      VisualNode node = getVisualNode(element);
      node.setPos(pos);
    }
    return elementInserted;
  }

  public void insertPreviewNode(int stateId, Point pos) {
    this.stateId = stateId;
    String element = UUID.randomUUID().toString();
    boolean elementInserted = insertElement(element, pos);
    if (elementInserted) {
      VisualNode visualNode = getVisualNode(element);
      visualNode.setPreviewNode(true);
    }
  }

  public void removePreviewNode() {
    getNodes().removeIf(node -> {
      VisualNode visualNode = (VisualNode) node;
      return visualNode.isPreviewNode();
    });
  }

  @Override
  protected Node<String> createNode(String element) {
    return stateFactory.createState(stateId, element, new Point());
  }

  public VisualNode getVisualNode(Object element) {
    return (VisualNode) getNode(element);
  }

}
