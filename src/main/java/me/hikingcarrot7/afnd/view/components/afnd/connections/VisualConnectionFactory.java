package me.hikingcarrot7.afnd.view.components.afnd.connections;

import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

public interface VisualConnectionFactory {

  VisualConnection createVisualConnection(int connectionId, VisualNode origin, VisualNode destination, String condition);

}
