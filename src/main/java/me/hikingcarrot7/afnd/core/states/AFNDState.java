package me.hikingcarrot7.afnd.core.states;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;

import java.awt.event.InputEvent;

public interface AFNDState {

  void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID);

  default void clearState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    vafnd.getDefaultTextBox().clearTextBox();
    vafnd.repaint();
  }

  default void exitState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    clearState(afndGraph, vafnd, afndStateManager);
    afndStateManager.setCurrentState(IdleState.getInstance());
  }

}
