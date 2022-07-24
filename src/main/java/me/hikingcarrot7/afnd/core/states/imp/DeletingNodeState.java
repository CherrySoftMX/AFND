package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.automata.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateManager;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
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
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      eliminarVertice(afndGraph, vafnd, event);
      vafnd.repaint();
    }
  }

  private void eliminarVertice(AFNDGraph<String> afndGraph, VAFND vafnd, InputEvent event) {
    MouseEvent e = (MouseEvent) event;
    int nVerticeSeleccionado = GraphUtils.getVerticePresionado(afndGraph, vafnd.getVNodes(), e.getPoint());
    if (nVerticeSeleccionado >= 0) {
      VNode vnode = vafnd.getVNode(nVerticeSeleccionado);
      afndGraph.removeElement(vnode.getName());
      vafnd.removeVNode(vnode);
      removeAllAdjacentVArchs(vnode, vafnd);
    }
  }

  private void removeAllAdjacentVArchs(VNode vnode, VAFND vafnd) {
    List<VArch> varchs = vafnd.getVArchs();
    List<VArch> adjacentVArchs = varchs.stream()
        .filter(varch -> ((VNode) varch.getOrigen()).getName().equals(vnode.getName())
            || ((VNode) varch.getDestino()).getName().equals(vnode.getName()))
        .collect(Collectors.toList());
    for (int i = varchs.size() - 1; i >= 0; i--) {
      VArch varch = varchs.get(i);
      if (adjacentVArchs.contains(varch)) {
        vafnd.removeVArch(varch);
      }
    }
  }

}
