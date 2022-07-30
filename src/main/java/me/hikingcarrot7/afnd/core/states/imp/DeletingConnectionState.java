package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.afnd.ConditionNode;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAFND;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.List;

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
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      MouseEvent e = (MouseEvent) event;
      if (origin != null) {
        removeArch(afndGraph, visualAFND, afndStateDispatcher, e);
        if (e.getButton() != MouseEvent.BUTTON1) {
          clearState(afndGraph, visualAFND, afndStateDispatcher);
        }
      } else {
        clearState(afndGraph, visualAFND, afndStateDispatcher);
        selectNode(afndGraph, visualAFND, afndStateDispatcher, (MouseEvent) event);
      }
    }
  }

  private void removeArch(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, MouseEvent e) {
    int pressedNode = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
    if (pressedNode >= 0) {
      VisualNode destination = visualAFND.getVNode(pressedNode);
      afndGraph.removeConnection(origin.element(), destination.element());
      visualAFND.removeVArch(visualAFND.getVArch(origin, destination));
      clearState(afndGraph, visualAFND, afndStateDispatcher);
    }
  }

  private void selectNode(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, MouseEvent e) {
    int nVerticePresionado = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
    if (nVerticePresionado >= 0) {
      VisualNode pressedNode = visualAFND.getVNode(nVerticePresionado);
      List<VisualConnection> adjacentArchs = GraphUtils.getAdjacentVArchs(pressedNode, visualAFND);
      if (!adjacentArchs.isEmpty()) {
        origin = pressedNode;
        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);
        markAdjacentConnections(origin, visualAFND);
        visualAFND.getDefaultTextBox().setTitle("Presione click izquierdo sobre algún estado adyacente para borrar la conexión");
        visualAFND.repaint();
      } else {
        clearState(afndGraph, visualAFND, afndStateDispatcher);
      }
    }
  }

  private void markAdjacentConnections(VisualNode vnode, VisualAFND vgraph) {
    List<VisualConnection> adjacentArchs = GraphUtils.getAdjacentVArchs(vnode, vgraph);
    adjacentArchs.forEach(varch -> {
      varch.setColorPalette(VisualConnection.RED_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.RED_TRIANGLE_COLOR_PALETTE);
      vgraph.setVArchZIndex(varch, VisualAFND.MAX_LAYER);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    visualAFND.getVisualConnections().forEach(varch -> {
      varch.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      varch.getConditionNode().setColorPalette(ConditionNode.DEFAULT_CONDITION_NODE_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      visualAFND.setVArchZIndex(varch, VisualAFND.MIN_LAYER);
    });
    if (origin != null) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }
    origin = null;
    AFNDState.super.clearState(afndGraph, visualAFND, afndStateDispatcher);
  }

}
