package me.hikingcarrot7.afnd.core.states;

import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;

import java.awt.event.InputEvent;

public class AutomataStateDispatcher {
  private AutomataState currentState;

  public AutomataStateDispatcher() {
    currentState = IdleState.getInstance();
  }

  public void dispatchInputEvent(int stateId, InputEvent event) {
    currentState.setStateId(stateId);
    currentState.setEvent(event);
    currentState.updateGraphState();
  }

  public void exitCurrentState() {
    currentState.exitState();
  }

  public void setCurrentState(AutomataState newState) {
    currentState = newState;
    currentState.setPanel(AFNDPanel.getInstance());
    currentState.setDispatcher(this);
  }

}
