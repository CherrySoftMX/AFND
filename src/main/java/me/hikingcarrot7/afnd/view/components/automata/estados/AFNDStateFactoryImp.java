package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.VNode;

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
  public VNode createState(int id, String data, Point center) {
    switch (id) {
      case Menu.ESTADO_INICIAL_ID:
        return new InitialState(data, center.x, center.y);
      case Menu.ESTADO_NORMAL_ID:
        return new NormalState(data, center.x, center.y);
      case Menu.ESTADO_FINAL_ID:
        return new FinalState(data, center.x, center.y);
      case Menu.ESTADO_INICIAL_FINAL_ID:
        return new InitialFinalState(data, center.x, center.y);
    }
    return null;
  }

}
