package com.cherrysoft.afnd.controller;

import com.cherrysoft.afnd.core.states.AutomataState;
import com.cherrysoft.afnd.core.states.AutomataStateDispatcher;
import com.cherrysoft.afnd.core.states.imp.IdleState;
import com.cherrysoft.afnd.view.MainView;
import com.cherrysoft.afnd.view.components.AbstractButton;
import com.cherrysoft.afnd.view.components.Button;
import com.cherrysoft.afnd.view.components.Menu;
import com.cherrysoft.afnd.view.components.ToggleButton;
import com.cherrysoft.afnd.view.components.afnd.AutomataPanel;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class AutomataController implements Observer {
  private final Map<AbstractButton, AutomataState> bindings;
  private final AutomataStateDispatcher automataStateDispatcher;
  private final MainView view;
  private final Menu menu;
  private final AutomataPanel panel;
  private AbstractButton latestPressedButton;

  public AutomataController(MainView view, AutomataPanel panel, Menu menu) {
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
        || button != null && button.getID() == Menu.STEP_VERIFICATION_ID) {
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
