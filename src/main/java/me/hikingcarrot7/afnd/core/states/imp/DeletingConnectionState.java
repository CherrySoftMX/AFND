package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.afnd.*;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import static java.util.Objects.isNull;

public class DeletingConnectionState implements AFNDState {
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
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      MouseEvent e = (MouseEvent) event;
      if (!isNull(origin)) {
        removeConnections(afndGraph, panel, afndStateDispatcher, e);
        if (e.getButton() != MouseEvent.BUTTON1) {
          clearState(afndGraph, panel, afndStateDispatcher);
        }
      } else {
        clearState(afndGraph, panel, afndStateDispatcher);
        selectOriginNode(afndGraph, panel, afndStateDispatcher, (MouseEvent) event);
      }
    }
  }

  private void selectOriginNode(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, MouseEvent e) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    VisualNode pressedNode = visualAutomata.getVisualNodeBellow(e.getPoint());
    if (!isNull(pressedNode)) {
      if (visualAutomata.hasAdjacentConnections(pressedNode.element())) {
        origin = pressedNode;
        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);
        markAdjacentConnections(panel);
        panel.textBox().setTitle("Presione click izquierdo sobre algún estado adyacente para borrar la conexión");
        panel.repaint();
      } else {
        clearState(afndGraph, panel, afndStateDispatcher);
      }
    }
  }

  private void markAdjacentConnections(AFNDPanel panel) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    visualAutomata.forEachVisualAdjacentConnectionFor(origin.element(), (conn -> {
      conn.setColorPalette(VisualConnection.RED_CONNECTION_COLOR_PALETTE);
      conn.getTriangle().setColorPalette(Triangle.RED_TRIANGLE_COLOR_PALETTE);
      conn.setLayer(AFNDPanel.MAX_LAYER);
    }));
  }

  private void removeConnections(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, MouseEvent e) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    VisualNode destination = visualAutomata.getVisualNodeBellow(e.getPoint());
    if (!isNull(destination)) {
      visualAutomata.removeConnection(origin.element(), destination.element());
      clearState(afndGraph, panel, afndStateDispatcher);
    }
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    visualAutomata.forEachVisualConnection(conn -> {
      conn.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      conn.getConditionNode().setColorPalette(ConditionNode.DEFAULT_CONDITION_NODE_COLOR_PALETTE);
      conn.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      conn.setLayer(AFNDPanel.MIN_LAYER);
    });
    if (!isNull(origin)) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }
    origin = null;
    AFNDState.super.clearState(afndGraph, panel, afndStateDispatcher);
  }

}
