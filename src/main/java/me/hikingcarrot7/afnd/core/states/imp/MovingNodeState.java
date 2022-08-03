package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAutomata;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import static java.util.Objects.isNull;

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
  private Point posStateToMove = null;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_RELEASED) {
      posStateToMove = null;
      panel.textBox().clearTextBox();
      panel.repaint();
      return;
    }
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      calculateOffsets(panel, event);
    }
    moveState(event);
    panel.repaint();
  }

  private void calculateOffsets(AFNDPanel panel, InputEvent event) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    if (event instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) event;
      posStateToMove = visualAutomata.getPosOfNodeBellow(e.getPoint());
      if (nodeSelected()) {
        offsetX = e.getX() - posStateToMove.x;
        offsetY = e.getY() - posStateToMove.y;
        panel.textBox().setTitle("Moviendo estado");
      }
    }
  }

  private void moveState(InputEvent event) {
    if (nodeSelected() && event instanceof MouseEvent) {
      MouseEvent e = (MouseEvent) event;
      int newCenterX = e.getX() - offsetX;
      int newCenterY = e.getY() - offsetY;
      posStateToMove.x = newCenterX;
      posStateToMove.y = newCenterY;
    }
  }

  private boolean nodeSelected() {
    return !isNull(posStateToMove);
  }

}
