package me.hikingcarrot7.afnd.controller;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.MainView;
import me.hikingcarrot7.afnd.view.components.AbstractButton;
import me.hikingcarrot7.afnd.view.components.Button;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.ToggleButton;
import me.hikingcarrot7.afnd.view.components.automata.VisualAFND;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;

public class AFNDController implements Observer {
  private final Map<AbstractButton, AFNDState> bindings;
  private final AFNDStateDispatcher afndStateDispatcher;
  private final MainView view;
  private final Menu menu;
  private final VisualAFND visualAFND;
  private AbstractButton latestPressedButton;

  public AFNDController(MainView view, AFNDGraph afndGraph, VisualAFND visualAFND, Menu menu) {
    this.view = view;
    this.latestPressedButton = null;
    this.menu = menu;
    this.visualAFND = visualAFND;
    this.afndStateDispatcher = new AFNDStateDispatcher(afndGraph);
    this.bindings = new HashMap<>();
  }

  @Override
  public void update(Observable o, Object arg) {
    InputEvent event = (InputEvent) arg;
    if (event instanceof KeyEvent) {
      handleKeyEvent((KeyEvent) event);
    }
    if (event instanceof MouseEvent) {
      handleMouseEvent((MouseEvent) event);
    }
  }

  private void handleKeyEvent(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      afndStateDispatcher.exitCurrentState();
      afndStateDispatcher.setCurrentState(IdleState.getInstance());
      blurLatestPressedButton();
    } else {
      deliverInputEvent(e);
    }
  }

  private void handleMouseEvent(MouseEvent e) {
    AbstractButton button = null;
    switch (e.getID()) {
      case MouseEvent.MOUSE_CLICKED:
        button = getButtonUnder(e.getPoint());
        if (button != null) {
          if (button != latestPressedButton) {
            blurLatestPressedButton();
          }

          button.click(e);
          afndStateDispatcher.exitCurrentState();
          AFNDState boundedAFNDState = bindings.get(button);

          if (button instanceof ToggleButton) {
            afndStateDispatcher.setCurrentState(((ToggleButton) button).isToggled() ? boundedAFNDState : IdleState.getInstance());
          } else {
            afndStateDispatcher.setCurrentState(boundedAFNDState);
          }

          latestPressedButton = button;
        }
        visualAFND.requestFocus();
        break;
      case MouseEvent.MOUSE_MOVED:
        button = getButtonUnder(e.getPoint());
        view.setCursor(Cursor.getPredefinedCursor(button != null ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
        break;
    }

    if (!menu.clicked(e.getPoint())
        || button instanceof Button
        || button != null && button.getID() == Menu.COMPROBACION_PASOS_ID) {
      deliverInputEvent(e);
    }
  }

  private void deliverInputEvent(InputEvent e) {
    int buttonID = latestPressedButton != null ? latestPressedButton.getID() : -1;
    afndStateDispatcher.dispatchInputEvent(buttonID, e);
  }

  private AbstractButton getButtonUnder(Point point) {
    for (AbstractButton button : bindings.keySet()) {
      if (button.isClicked(point)) {
        return button;
      }
    }
    return null;
  }

  private void blurLatestPressedButton() {
    if (latestPressedButton != null) {
      latestPressedButton.blur();
    }
  }

  public void addBinding(AbstractButton button, AFNDState state) {
    bindings.put(button, state);
  }

}
