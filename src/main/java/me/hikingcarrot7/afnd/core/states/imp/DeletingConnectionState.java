package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.states.AutomataState;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.ConditionNode;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import static java.util.Objects.isNull;

public class DeletingConnectionState extends AutomataState {
  private static DeletingConnectionState instance;

  public synchronized static DeletingConnectionState getInstance() {
    if (instance == null) {
      instance = new DeletingConnectionState();
    }
    return instance;
  }

  private DeletingConnectionState() {
  }

  private VisualNode origin;

  @Override
  public void updateGraphState() {
    if (isMousePressed()) {
      if (!isNull(origin)) {
        removeConnection();
        if (!isLeftClick()) {
          clearState();
        }
      } else {
        clearState();
        selectOriginNode();
      }
    }
  }

  private void selectOriginNode() {
    VisualNode pressedNode = visualAutomata.getVisualNodeBellow(getMousePos());
    if (!isNull(pressedNode)) {
      if (visualAutomata.hasAdjacentConnections(pressedNode.element())) {
        origin = pressedNode;
        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);
        markAdjacentConnections();
        panel.textBox().setTitle("Presione click izquierdo sobre algún estado adyacente para borrar la conexión");
        panel.repaint();
      } else {
        clearState();
      }
    }
  }

  private void markAdjacentConnections() {
    visualAutomata.forEachVisualAdjacentConnectionFor(origin.element(), (conn -> {
      conn.setColorPalette(VisualConnection.RED_CONNECTION_COLOR_PALETTE);
      conn.getTriangle().setColorPalette(Triangle.RED_TRIANGLE_COLOR_PALETTE);
      conn.setLayer(AFNDPanel.MAX_LAYER);
    }));
  }

  private void removeConnection() {
    VisualNode destination = visualAutomata.getVisualNodeBellow(getMousePos());
    if (!isNull(destination)) {
      visualAutomata.removeConnection(origin.element(), destination.element());
      clearState();
    }
  }

  @Override
  public void clearState() {
    visualAutomata.forEachVisualConnection(conn -> {
      conn.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      conn.getConditionNode().setColorPalette(ConditionNode.DEFAULT_CONDITION_NODE_COLOR_PALETTE);
      conn.getTriangle().setColorPalette(Triangle.CONNECTION_TRIANGLE_COLOR_PALETTE);
      conn.setLayer(AFNDPanel.MIN_LAYER);
    });
    if (!isNull(origin)) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }
    origin = null;
    super.clearState();
  }

}
