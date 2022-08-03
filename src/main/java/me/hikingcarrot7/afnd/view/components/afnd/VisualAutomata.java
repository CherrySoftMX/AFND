package me.hikingcarrot7.afnd.view.components.afnd;

import lombok.RequiredArgsConstructor;
import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.graphs.Node;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.afnd.connections.LoopConnection;
import me.hikingcarrot7.afnd.view.components.afnd.connections.NormalConnection;
import me.hikingcarrot7.afnd.view.components.afnd.states.NormalState;
import me.hikingcarrot7.afnd.view.components.afnd.states.VisualStateFactory;
import me.hikingcarrot7.afnd.view.graphics.Drawable;

import java.awt.*;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.Objects.isNull;
import static me.hikingcarrot7.afnd.core.utils.MathHelper.distanceBetweenTwoPoints;

@RequiredArgsConstructor
public class VisualAutomata extends AFNDGraph<String> implements Drawable {
  private final VisualStateFactory stateFactory;
  private VisualConnection previewConnection;
  private int stateId = NormalState.NORMAL_STATE_ID;
  private int connectionId;

  public boolean insertElement(String element, Point pos) {
    boolean elementInserted = insertElement(element);
    if (elementInserted) {
      setNodePos(element, pos);
      this.stateId = NormalState.NORMAL_STATE_ID;
    }
    return elementInserted;
  }

  @Override
  protected Node<String> createNode(String element) {
    return stateFactory.createState(stateId, element, new Point());
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
        .orElse(null);
  }

  public void removePreviewNode() {
    VisualNode previewNode = previewNode();
    if (!isNull(previewNode)) {
      removeElement(previewNode.element());
    }
  }

  public void insertCursorPreview(Point pos) {
    this.stateId = NormalState.NORMAL_STATE_ID;
    String element = UUID.randomUUID().toString();
    boolean elementInserted = insertElement(element, pos);
    if (elementInserted) {
      VisualNode visualNode = getVisualNode(element);
      visualNode.setCursorPreview(true);
    }
  }

  public Point cursorPreviewPos() {
    return cursorPreview().getPos();
  }

  private VisualNode cursorPreview() {
    return (VisualNode) getNodes().stream()
        .filter(node -> {
          VisualNode visualNode = (VisualNode) node;
          return visualNode.isCursorPreview();
        })
        .findAny()
        .orElse(null);
  }

  public void removeCursorPreview() {
    VisualNode cursorPreview = cursorPreview();
    if (!isNull(cursorPreview)) {
      removeElement(cursorPreview.element());
    }
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
    VisualNode visualNodeBellow = getVisualNodeBellow(point);
    if (!isNull(visualNodeBellow)) {
      return visualNodeBellow.getPos();
    }
    return null;
  }

  public VisualNode getVisualNodeBellow(Point point) {
    return (VisualNode) getNodes().stream()
        .filter(node -> {
          VisualNode visualNode = (VisualNode) node;
          return distanceBetweenTwoPoints(visualNode.getPos(), point) <= VisualNode.NODE_RADIUS;
        })
        .findAny()
        .orElse(null);
  }

  public boolean insertPreviewConnection(String origin) {
    VisualNode cursorPreview = cursorPreview();
    if (isNull(cursorPreview)) {
      VisualNode visualNode = getVisualNode(origin);
      insertCursorPreview(new Point(visualNode.getPos().x, visualNode.getPos().y));
      insertNormalConnection(origin, cursorPreview().element(), "");
      previewConnection = getVisualConnection(origin, cursorPreview().element());
      previewConnection.setPreviewMode(true);
      return true;
    }
    return false;
  }

  public void removePreviewConnection() {
    removeConnection(
        previewConnection.getOrigin().element(),
        previewConnection.getDestination().element()
    );
    removeCursorPreview();
    previewConnection = null;
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
  protected Connection<?> createConnection(Node<String> origin, Node<String> destination, Object element) {
    VisualNode visualOriginNode = getVisualNode(origin.element());
    VisualNode visualDestinationNode = getVisualNode(destination.element());
    switch (connectionId) {
      case 1:
        return new NormalConnection(visualOriginNode, visualDestinationNode, element.toString());
      case 2:
        return new LoopConnection(visualOriginNode, visualDestinationNode, element.toString());
    }
    throw new RuntimeException();
  }

  public VisualConnection getVisualConnection(String origin, String destination) {
    return (VisualConnection) getConnection(origin, destination);
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
    // Sort by layer
    getConnections().forEach(conn -> {
      VisualConnection visualConnection = (VisualConnection) conn;
      visualConnection.draw(g);
    });
    getNodes().forEach(node -> {
      VisualNode visualNode = (VisualNode) node;
      visualNode.draw(g);
    });
  }

  public void forEachVisualNode(Consumer<VisualNode> consumer) {
    getNodes().forEach(node -> {
      VisualNode visualNode = (VisualNode) node;
      consumer.accept(visualNode);
    });
  }

  public void forEachVisualAdjacentConnectionFor(String origin, Consumer<VisualConnection> consumer) {
    getAdjacentConnectionsFor(origin).forEach(conn -> {
      VisualConnection visualConnection = (VisualConnection) conn;
      consumer.accept(visualConnection);
    });
  }

  public void forEachVisualConnection(Consumer<VisualConnection> consumer) {
    getConnections().forEach(conn -> {
      VisualConnection visualConnection = (VisualConnection) conn;
      consumer.accept(visualConnection);
    });
  }

  @Override
  public int getLayer() {
    return AFNDPanel.MAX_LAYER;
  }

  public VisualConnection getPreviewConnection() {
    return previewConnection;
  }

}
