package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.automata.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateManager;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;

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
  public void updateGraphState(AFNDGraph<String> graph, VAFND vgraph, AFNDStateManager graphStateManager, InputEvent event, int buttonID) {

  }

}
