package com.cherrysoft.afnd.controller.events;

import java.awt.event.*;
import java.util.Observable;

public class EventHandler extends Observable implements MouseListener, MouseMotionListener, KeyListener {
  private static EventHandler instance;

  private EventHandler() {
  }

  public static EventHandler getInstance() {
    if (instance == null) {
      instance = new EventHandler();
    }
    return instance;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    notifyEvent(e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    notifyEvent(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    notifyEvent(e);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    notifyEvent(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    notifyEvent(e);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    notifyEvent(e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    notifyEvent(e);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    notifyEvent(e);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    notifyEvent(e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    notifyEvent(e);
  }

  private void notifyEvent(InputEvent event) {
    setChanged();
    notifyObservers(event);
  }

}
