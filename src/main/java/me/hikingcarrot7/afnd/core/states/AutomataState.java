package me.hikingcarrot7.afnd.core.states;

import lombok.Setter;
import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAutomata;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static java.util.Objects.isNull;

@Setter
public abstract class AutomataState {
  protected AFNDPanel panel;
  protected VisualAutomata visualAutomata;
  protected AutomataStateDispatcher dispatcher;
  protected InputEvent event;
  protected int stateId;

  public abstract void updateGraphState();

  public void clearState() {
    if (!isNull(panel)) {
      panel.textBox().clear();
      panel.repaint();
    }
  }

  public void exitState() {
    clearState();
    if (!isNull(dispatcher)) {
      dispatcher.setCurrentState(IdleState.getInstance());
    }
  }

  public boolean isMouseEvent() {
    return event instanceof MouseEvent;
  }

  public boolean isMouseClicked() {
    return event.getID() == MouseEvent.MOUSE_CLICKED;
  }

  public boolean isMousePressed() {
    return event.getID() == MouseEvent.MOUSE_PRESSED;
  }

  public boolean isMouseReleased() {
    return event.getID() == MouseEvent.MOUSE_RELEASED;
  }

  public boolean isMouseMoved() {
    return event.getID() == MouseEvent.MOUSE_MOVED;
  }

  public boolean isLeftClick() {
    MouseEvent mouseEvent = getAsMouseEvent();
    return mouseEvent.getButton() == MouseEvent.BUTTON1;
  }

  public MouseEvent getAsMouseEvent() {
    return (MouseEvent) event;
  }

  public Point getMousePos() {
    MouseEvent mouseEvent = getAsMouseEvent();
    return mouseEvent.getPoint();
  }

  public boolean isKeyPressed() {
    return event.getID() == KeyEvent.KEY_PRESSED;
  }

  public boolean isEnterPressed() {
    KeyEvent keyEvent = getAsKeyEvent();
    return keyEvent.getKeyCode() == KeyEvent.VK_ENTER;
  }

  public KeyEvent getAsKeyEvent() {
    return (KeyEvent) event;
  }

  public void setPanel(AFNDPanel panel) {
    this.panel = panel;
    this.visualAutomata = panel.getVisualAutomata();
  }

}
