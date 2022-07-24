package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.automata.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateManager;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

public class MovingNodeState implements AFNDState {
  private static MovingNodeState instance;

  public synchronized static MovingNodeState getInstance() {
    if (instance == null) {
      instance = new MovingNodeState();
    }
    return instance;
  }

  private MovingNodeState() {
  }

  private int offsetX;
  private int offsetY;
  private int nEstadoMover = -1;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_RELEASED) {
      nEstadoMover = -1;
      vafnd.getDefaultTextBox().clearTextBox();
      vafnd.repaint();
      return;
    }
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      calcularOffsets(afndGraph, vafnd, event);
    }
    moverEstado(vafnd, event);
    vafnd.repaint();
  }

  private void calcularOffsets(AFNDGraph<String> afndGraph, VAFND vafnd, InputEvent event) {
    if (event instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) event;
      nEstadoMover = GraphUtils.getVerticePresionado(afndGraph, vafnd.getVNodes(), e.getPoint());
      if (esEstadoValido()) {
        offsetX = e.getX() - vafnd.getVNode(nEstadoMover).getXCenter();
        offsetY = e.getY() - vafnd.getVNode(nEstadoMover).getYCenter();
        vafnd.getDefaultTextBox().setTitle("Moviendo estado");
      }
    }
  }

  private void moverEstado(VAFND vafnd, InputEvent event) {
    if (esEstadoValido() && event instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) event;
      int nuevaCoordenadaX = e.getX() - offsetX;
      int nuevaCoordenadaY = e.getY() - offsetY;
      vafnd.getVNode(nEstadoMover).setXCenter(nuevaCoordenadaX);
      vafnd.getVNode(nEstadoMover).setYCenter(nuevaCoordenadaY);
    }
  }

  private boolean esEstadoValido() {
    return nEstadoMover >= 0;
  }

}
