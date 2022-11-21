package com.cherrysoft.afnd.core.states.imp;

import com.cherrysoft.afnd.core.states.AutomataState;

public class IdleState extends AutomataState {
  private static IdleState instance;

  private IdleState() {
  }

  public synchronized static IdleState getInstance() {
    if (instance == null) {
      instance = new IdleState();
    }
    return instance;
  }

  @Override
  public void updateGraphState() {
  }

}
