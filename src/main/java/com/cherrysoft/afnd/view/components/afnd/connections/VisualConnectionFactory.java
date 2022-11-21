package com.cherrysoft.afnd.view.components.afnd.connections;

import com.cherrysoft.afnd.view.components.afnd.VisualConnection;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;

public interface VisualConnectionFactory {

  VisualConnection createVisualConnection(int connectionId, VisualNode origin, VisualNode destination, String condition);

}
