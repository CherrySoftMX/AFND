package me.hikingcarrot7.afnd.core.states;

import me.hikingcarrot7.afnd.core.automata.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;

import java.awt.event.InputEvent;

public class AFNDStateManager {
  private AFNDState currentState;
  private final AFNDGraph afndGraph;

  public AFNDStateManager(AFNDGraph afndGraph) {
    this.afndGraph = afndGraph;
    currentState = IdleState.getInstance();
  }

  public void dispatchInputEvent(int buttonID, InputEvent event) {
    currentState.updateGraphState(afndGraph, VAFND.getInstance(), this, event, buttonID);
  }

  public void exitCurrentState() {
    currentState.exitState(afndGraph, VAFND.getInstance(), this);
  }

  public void setCurrentState(AFNDState newState) {
    currentState = newState;
  }

}
