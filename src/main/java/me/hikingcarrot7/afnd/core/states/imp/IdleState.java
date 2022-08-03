package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.states.AutomataState;

public class IdleState extends AutomataState {
  private static IdleState instance;

  public synchronized static IdleState getInstance() {
    if (instance == null) {
      instance = new IdleState();
    }
    return instance;
  }

  private IdleState() {
  }

  @Override
  public void updateGraphState() {
  }

}
