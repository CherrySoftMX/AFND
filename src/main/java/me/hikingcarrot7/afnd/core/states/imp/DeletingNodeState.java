package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class DeletingNodeState implements AFNDState {
  private static DeletingNodeState instance;

  public synchronized static DeletingNodeState getInstance() {
    if (instance == null) {
      instance = new DeletingNodeState();
    }
    return instance;
  }

  private DeletingNodeState() {
  }

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      deleteNode(afndGraph, AFNDPanel, event);
      AFNDPanel.repaint();
    }
  }

  private void deleteNode(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, InputEvent event) {
    MouseEvent e = (MouseEvent) event;
    int nVerticeSeleccionado = GraphUtils.getPressedNode(afndGraph, AFNDPanel.getVNodes(), e.getPoint());
    if (nVerticeSeleccionado >= 0) {
      VisualNode vnode = AFNDPanel.getVNode(nVerticeSeleccionado);
      afndGraph.removeElement(vnode.element());
      AFNDPanel.removeVNode(vnode);
      removeAllAdjacentVArchs(vnode, AFNDPanel);
    }
  }

  private void removeAllAdjacentVArchs(VisualNode vnode, AFNDPanel AFNDPanel) {
    List<VisualConnection> varches = AFNDPanel.getVisualConnections();
    List<VisualConnection> adjacentVisualConnections = varches.stream()
        .filter(varch -> ((VisualNode) varch.getOrigin()).element().equals(vnode.element())
            || ((VisualNode) varch.getDestination()).element().equals(vnode.element()))
        .collect(Collectors.toList());
    for (int i = varches.size() - 1; i >= 0; i--) {
      VisualConnection varch = varches.get(i);
      if (adjacentVisualConnections.contains(varch)) {
        AFNDPanel.removeVArch(varch);
      }
    }
  }

}
