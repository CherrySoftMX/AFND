package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.automata.VisualAFND;
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
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_RELEASED) {
      nEstadoMover = -1;
      visualAFND.getDefaultTextBox().clearTextBox();
      visualAFND.repaint();
      return;
    }
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      calcularOffsets(afndGraph, visualAFND, event);
    }
    moverEstado(visualAFND, event);
    visualAFND.repaint();
  }

  private void calcularOffsets(AFNDGraph<String> afndGraph, VisualAFND visualAFND, InputEvent event) {
    if (event instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) event;
      nEstadoMover = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
      if (esEstadoValido()) {
        offsetX = e.getX() - visualAFND.getVNode(nEstadoMover).xCenter();
        offsetY = e.getY() - visualAFND.getVNode(nEstadoMover).yCenter();
        visualAFND.getDefaultTextBox().setTitle("Moviendo estado");
      }
    }
  }

  private void moverEstado(VisualAFND visualAFND, InputEvent event) {
    if (esEstadoValido() && event instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) event;
      int nuevaCoordenadaX = e.getX() - offsetX;
      int nuevaCoordenadaY = e.getY() - offsetY;
      visualAFND.getVNode(nEstadoMover).setXCenter(nuevaCoordenadaX);
      visualAFND.getVNode(nEstadoMover).setYCenter(nuevaCoordenadaY);
    }
  }

  private boolean esEstadoValido() {
    return nEstadoMover >= 0;
  }

}
