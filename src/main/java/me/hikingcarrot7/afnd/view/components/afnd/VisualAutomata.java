package me.hikingcarrot7.afnd.view.components.afnd;

import lombok.RequiredArgsConstructor;
import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.graphs.Node;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.afnd.connections.LoopConnection;
import me.hikingcarrot7.afnd.view.components.afnd.connections.NormalConnection;
import me.hikingcarrot7.afnd.view.components.afnd.states.VisualStateFactory;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

import static me.hikingcarrot7.afnd.core.utils.MathHelper.distanceBetweenTwoPoints;

@RequiredArgsConstructor
public class VisualAutomata extends AFNDGraph<String> implements Drawable {
  private final VisualStateFactory stateFactory;
  private int stateId;
  private int connectionId;

  public boolean insertElement(String element, Point pos) {
    boolean elementInserted = insertElement(element);
    if (elementInserted) {
      setNodePos(element, pos);
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

  public Point previewNodePos() {
    return previewNode().getPos();
  }

  public VisualNode previewNode() {
    return (VisualNode) getNodes().stream()
        .filter(node -> {
          VisualNode visualNode = (VisualNode) node;
          return visualNode.isPreviewNode();
        })
        .findAny()
        .orElseThrow(IllegalStateException::new);
  }

  public void removePreviewNode() {
    getNodes().removeIf(node -> {
      VisualNode visualNode = (VisualNode) node;
      return visualNode.isPreviewNode();
    });
  }

  public boolean insertAsInitialState(String element, Point pos) {
    this.stateId = Menu.INITIAL_STATE_ID;
    boolean elementInserted = insertAsInitialState(element);
    if (elementInserted) {
      setNodePos(element, pos);
    }
    return elementInserted;
  }

  public boolean insertAsInitialAndFinalState(String element, Point pos) {
    this.stateId = Menu.INITIAL_FINAL_STATE_ID;
    boolean elementInserted = insertAsInitialAndFinalState(element);
    if (elementInserted) {
      setNodePos(element, pos);
    }
    return elementInserted;
  }

  public boolean insertAsFinalState(String element, Point pos) {
    this.stateId = Menu.FINAL_STATE_ID;
    boolean elementInserted = insertAsFinalState(element);
    if (elementInserted) {
      setNodePos(element, pos);
    }
    return elementInserted;
  }

  public Point getPosOfNodeBellow(Point point) {
    return getVisualNodeBellow(point)
        .map(VisualNode::getPos)
        .orElse(null);
  }

  public String getElementOfNodeBellow(Point point) {
    return getVisualNodeBellow(point)
        .map(Node::element)
        .orElse(null);
  }

  protected Optional<VisualNode> getVisualNodeBellow(Point point) {
    return getNodes().stream()
        .filter(node -> {
          VisualNode visualNode = (VisualNode) node;
          return distanceBetweenTwoPoints(visualNode.getPos(), point) <= VisualNode.NODE_RADIUS;
        })
        .findAny()
        .map(node -> (VisualNode) node);
  }

  public void setColorPaletteOf(String element, ColorPalette colorPalette) {
    VisualNode visualNode = getVisualNode(element);
    visualNode.setColorPalette(colorPalette);
  }

  public boolean insertNormalConnection(String origin, String destination, String condition) {
    this.connectionId = 1;
    return insertConnection(origin, destination, condition);
  }

  public boolean insertLoopConnection(String origin, String destination, String condition) {
    this.connectionId = 2;
    return insertConnection(origin, destination, condition);
  }

  @Override
  protected Node<String> createNode(String element) {
    return stateFactory.createState(stateId, element, new Point());
  }

  @Override
  protected Connection<?> createConnection(Node<String> origin, Node<String> destination, Object element) {
    VisualNode visualOriginNode = getVisualNode(origin);
    VisualNode visualDestinationNode = getVisualNode(destination);
    switch (connectionId) {
      case 1:
        return new NormalConnection(visualOriginNode, visualDestinationNode, element.toString());
      case 2:
        return new LoopConnection(visualOriginNode, visualDestinationNode, element.toString());
    }
    throw new RuntimeException();
  }

  private VisualNode getVisualNode(Object element) {
    return (VisualNode) getNode(element);
  }

  private void setNodePos(String element, Point pos) {
    VisualNode visualNode = getVisualNode(element);
    visualNode.setPos(pos);
  }

  @Override
  public void draw(Graphics2D g) {
    getNodes().forEach(node -> {
      VisualNode visualNode = (VisualNode) node;
      visualNode.draw(g);
    });
  }

  @Override
  public int getLayer() {
    return AFNDPanel.MAX_LAYER;
  }

}
