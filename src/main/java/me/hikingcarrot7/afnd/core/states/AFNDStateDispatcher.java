package me.hikingcarrot7.afnd.core.states;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.imp.IdleState;
import me.hikingcarrot7.afnd.view.components.automata.VisualAFND;

import java.awt.event.InputEvent;

public class AFNDStateDispatcher {
  private AFNDState currentState;
  private final AFNDGraph afndGraph;

  public AFNDStateDispatcher(AFNDGraph afndGraph) {
    this.afndGraph = afndGraph;
    currentState = IdleState.getInstance();
  }

  public void dispatchInputEvent(int buttonID, InputEvent event) {
    currentState.updateGraphState(afndGraph, VisualAFND.getInstance(), this, event, buttonID);
  }

  public void exitCurrentState() {
    currentState.exitState(afndGraph, VisualAFND.getInstance(), this);
  }

  public void setCurrentState(AFNDState newState) {
    currentState = newState;
  }

}