package me.hikingcarrot7.afnd.view.components.afnd.connections;

import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import static java.util.Objects.isNull;

public class VisualConnectionFactoryImp implements VisualConnectionFactory {
  private static VisualConnectionFactory instance;

  public synchronized static VisualConnectionFactory getInstance() {
    if (isNull(instance)) {
      instance = new VisualConnectionFactoryImp();
    }
    return instance;
  }

  private VisualConnectionFactoryImp() {
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
