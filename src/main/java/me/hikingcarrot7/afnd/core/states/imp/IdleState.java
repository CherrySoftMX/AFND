package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAFND;

import java.awt.event.InputEvent;

public class IdleState implements AFNDState {

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
  public void updateGraphState(AFNDGraph<String> graph, VisualAFND vgraph, AFNDStateDispatcher graphStateManager, InputEvent event, int buttonID) {

  }

}
