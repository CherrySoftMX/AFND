package me.hikingcarrot7.afnd.core.states;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;

import java.awt.event.InputEvent;

public interface AFNDState {

  void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID);

  default void clearState(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, AFNDStateDispatcher afndStateDispatcher) {
    AFNDPanel.getDefaultTextBox().clearTextBox();
    AFNDPanel.repaint();
  }

  default void exitState(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, AFNDStateDispatcher afndStateDispatcher) {
    clearState(afndGraph, AFNDPanel, afndStateDispatcher);
    afndStateDispatcher.setCurrentState(IdleState.getInstance());
  }

}
