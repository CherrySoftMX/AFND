package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.Blob;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.automata.VisualConnection;
import me.hikingcarrot7.afnd.view.components.automata.VisualNode;
import me.hikingcarrot7.afnd.view.components.automata.VisualAFND;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;

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

  private VisualNode origen;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      MouseEvent e = (MouseEvent) event;
      if (origen != null) {
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
    int nVerticePresionado = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
    if (nVerticePresionado >= 0) {
      VisualNode destino = visualAFND.getVNode(nVerticePresionado);
      afndGraph.removeConnection(origen.getElement(), destino.getElement());
      visualAFND.removeVArch(visualAFND.getVArch(origen, destino));
      clearState(afndGraph, visualAFND, afndStateDispatcher);
    }
  }

  private void selectNode(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, MouseEvent e) {
    int nVerticePresionado = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
    if (nVerticePresionado >= 0) {
      VisualNode pressedNode = visualAFND.getVNode(nVerticePresionado);
      List<VisualConnection> adjacentArchs = GraphUtils.getAdjacentVArchs(pressedNode, visualAFND);
      if (!adjacentArchs.isEmpty()) {
        origen = pressedNode;
        origen.setColorPalette(VisualNode.SELECTED_VNODE_COLOR_PALETTE);
        markAdjacentArchs(origen, visualAFND);
        visualAFND.getDefaultTextBox().setTitle("Presione click izquierdo sobre algún estado adyacente para borrar la conexión");
        visualAFND.repaint();
      } else {
        clearState(afndGraph, visualAFND, afndStateDispatcher);
      }
    }
  }

  private void markAdjacentArchs(VisualNode vnode, VisualAFND vgraph) {
    List<VisualConnection> adjacentArchs = GraphUtils.getAdjacentVArchs(vnode, vgraph);
    adjacentArchs.forEach(varch -> {
      varch.setColorPalette(VisualConnection.RED_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.RED_TRIANGLE_COLOR_PALETTE);
      vgraph.setVArchZIndex(varch, VisualAFND.MAX_LAYER);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    visualAFND.getVisualConnections().forEach(varch -> {
      varch.setColorPalette(VisualConnection.DEFAULT_VARCH_COLOR_PALETTE);
      varch.getBlob().setColorPalette(Blob.DEFAULT_BLOB_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      visualAFND.setVArchZIndex(varch, VisualAFND.MIN_LAYER);
    });
    if (origen != null) {
      origen.setColorPalette(VisualNode.DEFAULT_VNODE_COLOR_PALETTE);
    }
    origen = null;
    AFNDState.super.clearState(afndGraph, visualAFND, afndStateDispatcher);
  }

}
