package me.hikingcarrot7.afnd.controller;

import me.hikingcarrot7.afnd.view.MainView;
import me.hikingcarrot7.afnd.view.components.AbstractButton;
import me.hikingcarrot7.afnd.view.components.Button;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.ToggleButton;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.model.states.AFNDState;
import me.hikingcarrot7.afnd.model.states.AFNDStateManager;
import me.hikingcarrot7.afnd.model.states.IdleState;
import me.hikingcarrot7.afnd.model.utils.Pair;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class AFNDController implements Observer {

  private final List<Pair<AbstractButton, AFNDState>> bindings;
  private final AFNDStateManager afndStateManager;

  private final MainView view;
  private final Menu menu;
  private final VAFND vafnd;
  private AbstractButton latestPressedButton;

  public AFNDController(MainView view, AFND afnd, VAFND vafnd, Menu menu) {
    this.view = view;
    this.latestPressedButton = null;
    this.menu = menu;
    this.vafnd = vafnd;
    this.afndStateManager = new AFNDStateManager(afnd);
    this.bindings = new ArrayList<>();
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
      afndStateManager.exitCurrentState();
      afndStateManager.setCurrentState(IdleState.getInstance());
      blurLatestPressedButton();
    } else {
      deliverInputEvent(e);
    }
  }

  private void handleMouseEvent(MouseEvent e) {
    AbstractButton button = null;
    switch (e.getID()) {
      case MouseEvent.MOUSE_CLICKED:
        button = getMouseOverButton(e.getPoint());
        if (button != null) {
          if (button != latestPressedButton) {
            blurLatestPressedButton();
          }

          button.click(e);
          afndStateManager.exitCurrentState();
          AFNDState boundedAFNDState = getBoundAFNDState(button);

          if (button instanceof ToggleButton) {
            afndStateManager.setCurrentState(((ToggleButton) button).isToggled() ? boundedAFNDState : IdleState.getInstance());
          } else {
            afndStateManager.setCurrentState(boundedAFNDState);
          }

          latestPressedButton = button;
        }
        vafnd.requestFocus();
        break;
      case MouseEvent.MOUSE_MOVED:
        button = getMouseOverButton(e.getPoint());
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
    afndStateManager.dispatchInputEvent(latestPressedButton != null ? latestPressedButton.getID() : -1, e);
  }

  private AbstractButton getMouseOverButton(Point point) {
    for (Pair<AbstractButton, AFNDState> pair : bindings) {
      if (pair.getLeft().isClicked(point)) {
        return pair.getLeft();
      }
    }
    return null;
  }

  private AFNDState getBoundAFNDState(AbstractButton button) {
    for (Pair<AbstractButton, AFNDState> pair : bindings) {
      if (pair.getLeft() == button) {
        return pair.getRight();
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
    bindings.add(new Pair<>(button, state));
  }

  public void removeBinding(AbstractButton button, AFNDState state) {
    bindings.remove(new Pair<>(button, state));
  }

}
