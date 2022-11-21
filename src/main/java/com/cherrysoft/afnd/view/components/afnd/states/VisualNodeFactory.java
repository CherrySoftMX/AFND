package com.cherrysoft.afnd.view.components.afnd.states;

import com.cherrysoft.afnd.view.components.afnd.VisualNode;

import java.awt.*;

public interface VisualNodeFactory {

  VisualNode createVisualNode(int stateId, String element, Point center);

}
