package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.automata.VisualConnection;
import me.hikingcarrot7.afnd.view.components.automata.VisualNode;
import me.hikingcarrot7.afnd.view.components.automata.VisualAFND;
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
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      eliminarVertice(afndGraph, visualAFND, event);
      visualAFND.repaint();
    }
  }

  private void eliminarVertice(AFNDGraph<String> afndGraph, VisualAFND visualAFND, InputEvent event) {
    MouseEvent e = (MouseEvent) event;
    int nVerticeSeleccionado = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
    if (nVerticeSeleccionado >= 0) {
      VisualNode vnode = visualAFND.getVNode(nVerticeSeleccionado);
      afndGraph.removeElement(vnode.getElement());
      visualAFND.removeVNode(vnode);
      removeAllAdjacentVArchs(vnode, visualAFND);
    }
  }

  private void removeAllAdjacentVArchs(VisualNode vnode, VisualAFND visualAFND) {
    List<VisualConnection> varches = visualAFND.getVisualConnections();
    List<VisualConnection> adjacentVisualConnections = varches.stream()
        .filter(varch -> ((VisualNode) varch.getOrigin()).getElement().equals(vnode.getElement())
            || ((VisualNode) varch.getDestination()).getElement().equals(vnode.getElement()))
        .collect(Collectors.toList());
    for (int i = varches.size() - 1; i >= 0; i--) {
      VisualConnection varch = varches.get(i);
      if (adjacentVisualConnections.contains(varch)) {
        visualAFND.removeVArch(varch);
      }
    }
  }

}
