package me.hikingcarrot7.afnd.view.components.afnd.states;

import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import java.awt.Point;

public interface VisualNodeFactory {

  VisualNode createVisualNode(int stateId, String element, Point center);

}
