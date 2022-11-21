package com.cherrysoft.afnd.core.states.imp;

import com.cherrysoft.afnd.core.states.AutomataState;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;

import static java.util.Objects.isNull;

public class DeletingNodeState extends AutomataState {
  private static DeletingNodeState instance;

  private DeletingNodeState() {
  }

  public synchronized static DeletingNodeState getInstance() {
    if (instance == null) {
      instance = new DeletingNodeState();
    }
    return instance;
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
