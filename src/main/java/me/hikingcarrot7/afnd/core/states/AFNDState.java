package me.hikingcarrot7.afnd.core.states;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.components.automata.VisualAFND;

import java.awt.event.InputEvent;

public interface AFNDState {

  void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID);

  default void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    visualAFND.getDefaultTextBox().clearTextBox();
    visualAFND.repaint();
  }

  default void exitState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    clearState(afndGraph, visualAFND, afndStateDispatcher);
    afndStateDispatcher.setCurrentState(IdleState.getInstance());
  }

}
