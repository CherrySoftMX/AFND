package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.states.AutomataState;

import java.awt.*;

import static java.util.Objects.isNull;

public class MovingNodeState extends AutomataState {
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
  private Point posNodeToMove = null;

  @Override
  public void updateGraphState() {
    if (isMouseReleased()) {
      posNodeToMove = null;
      panel.textBox().clear();
      panel.repaint();
      return;
    }
    if (isMousePressed()) {
      calculateOffsets();
    }
    moveNode();
    panel.repaint();
  }

  private void calculateOffsets() {
    if (isMouseEvent()) {
      Point mousePos = getMousePos();
      posNodeToMove = visualAutomata.getPosOfNodeBellow(mousePos);
      if (nodeSelected()) {
        offsetX = mousePos.x - posNodeToMove.x;
        offsetY = mousePos.y - posNodeToMove.y;
        panel.textBox().setTitle("Moviendo estado");
      }
    }
  }

  private void moveNode() {
    if (nodeSelected() && isMouseEvent()) {
      Point mousePos = getMousePos();
      int newCenterX = mousePos.x - offsetX;
      int newCenterY = mousePos.y - offsetY;
      posNodeToMove.x = newCenterX;
      posNodeToMove.y = newCenterY;
    }
  }

  private boolean nodeSelected() {
    return !isNull(posNodeToMove);
  }

}
