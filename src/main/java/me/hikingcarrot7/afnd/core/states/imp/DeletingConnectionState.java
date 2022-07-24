package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.automata.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateManager;
import me.hikingcarrot7.afnd.view.components.Blob;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
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

  private VNode origen;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      MouseEvent e = (MouseEvent) event;
      if (origen != null) {
        removeArch(afndGraph, vafnd, afndStateManager, e);
        if (e.getButton() != MouseEvent.BUTTON1) {
          clearState(afndGraph, vafnd, afndStateManager);
        }
      } else {
        clearState(afndGraph, vafnd, afndStateManager);
        selectNode(afndGraph, vafnd, afndStateManager, (MouseEvent) event);
      }
    }
  }

  private void removeArch(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, MouseEvent e) {
    int nVerticePresionado = GraphUtils.getVerticePresionado(afndGraph, vafnd.getVNodes(), e.getPoint());
    if (nVerticePresionado >= 0) {
      VNode destino = vafnd.getVNode(nVerticePresionado);
      afndGraph.removeConnection(origen.getName(), destino.getName());
      vafnd.removeVArch(vafnd.getVArch(origen, destino));
      clearState(afndGraph, vafnd, afndStateManager);
    }
  }

  private void selectNode(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, MouseEvent e) {
    int nVerticePresionado = GraphUtils.getVerticePresionado(afndGraph, vafnd.getVNodes(), e.getPoint());
    if (nVerticePresionado >= 0) {
      VNode pressedNode = vafnd.getVNode(nVerticePresionado);
      List<VArch> adjacentArchs = GraphUtils.getAdjacentVArchs(pressedNode, vafnd);
      if (!adjacentArchs.isEmpty()) {
        origen = pressedNode;
        origen.setColorPalette(VNode.SELECTED_VNODE_COLOR_PALETTE);
        markAdjacentArchs(origen, vafnd);
        vafnd.getDefaultTextBox().setTitle("Presione click izquierdo sobre algún estado adyacente para borrar la conexión");
        vafnd.repaint();
      } else {
        clearState(afndGraph, vafnd, afndStateManager);
      }
    }
  }

  private void markAdjacentArchs(VNode vnode, VAFND vgraph) {
    List<VArch> adjacentArchs = GraphUtils.getAdjacentVArchs(vnode, vgraph);
    adjacentArchs.forEach(varch -> {
      varch.setColorPalette(VArch.RED_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.RED_TRIANGLE_COLOR_PALETTE);
      vgraph.setVArchZIndex(varch, VAFND.MAX_LAYER);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    vafnd.getVArchs().forEach(varch -> {
      varch.setColorPalette(VArch.DEFAULT_VARCH_COLOR_PALETTE);
      varch.getBlob().setColorPalette(Blob.DEFAULT_BLOB_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      vafnd.setVArchZIndex(varch, VAFND.MIN_LAYER);
    });
    if (origen != null) {
      origen.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE);
    }
    origen = null;
    AFNDState.super.clearState(afndGraph, vafnd, afndStateManager);
  }

}
