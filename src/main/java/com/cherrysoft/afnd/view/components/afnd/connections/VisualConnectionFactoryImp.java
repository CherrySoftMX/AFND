package com.cherrysoft.afnd.view.components.afnd.connections;

import com.cherrysoft.afnd.view.components.afnd.VisualConnection;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;

import static java.util.Objects.isNull;

public class VisualConnectionFactoryImp implements VisualConnectionFactory {
  private static VisualConnectionFactory instance;

  private VisualConnectionFactoryImp() {
  }

  public synchronized static VisualConnectionFactory getInstance() {
    if (isNull(instance)) {
      instance = new VisualConnectionFactoryImp();
    }
    return instance;
  }

  @Override
  public VisualConnection createVisualConnection(int connectionId, VisualNode origin, VisualNode destination, String condition) {
    switch (connectionId) {
      case NormalConnection.NORMAL_CONNECTION_ID:
        return new NormalConnection(origin, destination, condition);
      case LoopConnection.LOOP_CONNECTION_ID:
        return new LoopConnection(origin, destination, condition);
    }
    throw new RuntimeException();
  }
}
