package com.cherrysoft.afnd.core.states;

import com.cherrysoft.afnd.core.states.imp.IdleState;
import com.cherrysoft.afnd.view.components.afnd.AutomataPanel;

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
    currentState.setPanel(AutomataPanel.getInstance());
    currentState.setDispatcher(this);
  }

}
