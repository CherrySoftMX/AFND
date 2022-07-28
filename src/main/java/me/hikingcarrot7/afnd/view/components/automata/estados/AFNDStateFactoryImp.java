package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.automata.VisualNode;

import java.awt.*;

public class AFNDStateFactoryImp implements AFNDStateFactory {
  private static AFNDStateFactoryImp instance;

  public synchronized static AFNDStateFactoryImp getInstance() {
    if (instance == null) {
      instance = new AFNDStateFactoryImp();
    }
    return instance;
  }

  private AFNDStateFactoryImp() {
  }

  @Override
  public VisualNode createState(int nodeType, String element, Point pos) {
    switch (nodeType) {
      case Menu.ESTADO_INICIAL_ID:
        return new InitialState(element, pos);
      case Menu.ESTADO_NORMAL_ID:
        return new NormalState(element, pos);
      case Menu.ESTADO_FINAL_ID:
        return new FinalState(element, pos);
      case Menu.ESTADO_INICIAL_FINAL_ID:
        return new InitialFinalState(element, pos);
    }
    throw new RuntimeException();
  }

}
