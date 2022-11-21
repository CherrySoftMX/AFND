package com.cherrysoft.afnd.core.states.imp;

import com.cherrysoft.afnd.core.states.AutomataState;
import com.cherrysoft.afnd.view.components.Triangle;
import com.cherrysoft.afnd.view.components.afnd.AutomataPanel;
import com.cherrysoft.afnd.view.components.afnd.ConditionNode;
import com.cherrysoft.afnd.view.components.afnd.VisualConnection;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;

import static java.util.Objects.isNull;

public class DeletingConnectionState extends AutomataState {
  private static DeletingConnectionState instance;
  private VisualNode origin;

  private DeletingConnectionState() {
  }

  public synchronized static DeletingConnectionState getInstance() {
    if (instance == null) {
      instance = new DeletingConnectionState();
    }
    return instance;
  }

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
        panel.textBox().setTitle("Left click on any adjacent state to delete the connection");
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
      conn.setLayer(AutomataPanel.MAX_LAYER);
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
      conn.setLayer(AutomataPanel.MIN_LAYER);
    });
    if (!isNull(origin)) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }
    origin = null;
    super.clearState();
  }

}
