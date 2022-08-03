package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAutomata;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import static java.util.Objects.isNull;

public class DeletingNodeState implements AFNDState {
  private static DeletingNodeState instance;

  public synchronized static DeletingNodeState getInstance() {
    if (instance == null) {
      instance = new DeletingNodeState();
    }
    return instance;
  }

  private DeletingNodeState() {
  }

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      deletePressedNode(panel, event);
      panel.repaint();
    }
  }

  private void deletePressedNode(AFNDPanel panel, InputEvent event) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    MouseEvent e = (MouseEvent) event;
    VisualNode pressedNode = visualAutomata.getVisualNodeBellow(e.getPoint());
    if (!isNull(pressedNode)) {
      visualAutomata.removeElement(pressedNode.element());
      visualAutomata.printGraph();
    }
  }

}
