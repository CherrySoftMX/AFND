package me.hikingcarrot7.afnd.controller;

import me.hikingcarrot7.afnd.core.states.AutomataState;
import me.hikingcarrot7.afnd.core.states.AutomataStateDispatcher;
import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.MainView;
import me.hikingcarrot7.afnd.view.components.AbstractButton;
import me.hikingcarrot7.afnd.view.components.Button;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.ToggleButton;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;

public class AutomataController implements Observer {
  private final Map<AbstractButton, AutomataState> bindings;
  private final AutomataStateDispatcher automataStateDispatcher;
  private final MainView view;
  private final Menu menu;
  private final AFNDPanel panel;
  private AbstractButton latestPressedButton;

  public AutomataController(MainView view, AFNDPanel panel, Menu menu) {
    this.view = view;
    this.latestPressedButton = null;
    this.menu = menu;
    this.panel = panel;
    this.automataStateDispatcher = new AutomataStateDispatcher();
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
      automataStateDispatcher.exitCurrentState();
      automataStateDispatcher.setCurrentState(IdleState.getInstance());
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
          automataStateDispatcher.exitCurrentState();
          AutomataState boundedAutomataState = bindings.get(button);

          if (button instanceof ToggleButton) {
            automataStateDispatcher.setCurrentState(((ToggleButton) button).isToggled() ? boundedAutomataState : IdleState.getInstance());
          } else {
            automataStateDispatcher.setCurrentState(boundedAutomataState);
          }

          latestPressedButton = button;
        }
        panel.requestFocus();
        panel.repaint();
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
    automataStateDispatcher.dispatchInputEvent(buttonID, e);
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

  public void addBinding(AbstractButton button, AutomataState state) {
    bindings.put(button, state);
  }

}
