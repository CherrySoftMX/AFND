package me.hikingcarrot7.afnd.view.components.afnd.states;

import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import java.awt.*;

public class VisualStateFactoryImp implements VisualStateFactory {
  private static VisualStateFactoryImp instance;

  public synchronized static VisualStateFactoryImp getInstance() {
    if (instance == null) {
      instance = new VisualStateFactoryImp();
    }
    return instance;
  }

  private VisualStateFactoryImp() {
  }

  @Override
  public VisualNode createState(int stateId, String element, Point pos) {
    switch (stateId) {
      case Menu.INITIAL_STATE_ID:
        return new InitialState(element, pos);
      case Menu.ESTADO_NORMAL_ID:
        return new NormalState(element, pos);
      case Menu.FINAL_STATE_ID:
        return new FinalState(element, pos);
      case Menu.INITIAL_FINAL_STATE_ID:
        return new InitialFinalState(element, pos);
      default:
        return new VisualNode(element, pos);
    }
  }

}
