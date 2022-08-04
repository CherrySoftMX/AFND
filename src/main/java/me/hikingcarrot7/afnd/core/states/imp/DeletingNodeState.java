package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.states.AutomataState;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import static java.util.Objects.isNull;

public class DeletingNodeState extends AutomataState {
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
  public void updateGraphState() {
    if (isMousePressed()) {
      deletePressedNode();
      panel.repaint();
    }
  }

  private void deletePressedNode() {
    VisualNode pressedNode = visualAutomata.getVisualNodeBellow(getMousePos());
    if (!isNull(pressedNode)) {
      visualAutomata.removeElement(pressedNode.element());
    }
  }

}
