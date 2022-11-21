package com.cherrysoft.afnd.view.components.afnd.states;

import com.cherrysoft.afnd.view.components.afnd.VisualNode;

import java.awt.*;

import static java.util.Objects.isNull;

public class VisualNodeFactoryImp implements VisualNodeFactory {
  private static VisualNodeFactoryImp instance;

  private VisualNodeFactoryImp() {
  }

  public synchronized static VisualNodeFactory getInstance() {
    if (isNull(instance)) {
      instance = new VisualNodeFactoryImp();
    }
    return instance;
  }

  @Override
  public VisualNode createVisualNode(int stateId, String element, Point pos) {
    switch (stateId) {
      case InitialState.INITIAL_STATE_ID:
        return new InitialState(element, pos);
      case NormalState.NORMAL_STATE_ID:
        return new NormalState(element, pos);
      case FinalState.FINAL_STATE_ID:
        return new FinalState(element, pos);
      case InitialFinalState.INITIAL_FINAL_STATE_ID:
        return new InitialFinalState(element, pos);
      default:
        return new VisualNode(element, pos);
    }
  }

}
