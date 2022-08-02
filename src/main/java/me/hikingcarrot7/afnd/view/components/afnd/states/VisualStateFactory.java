package me.hikingcarrot7.afnd.view.components.afnd.states;

import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import java.awt.Point;

public interface VisualStateFactory {

  VisualNode createState(int stateId, String element, Point center);

}
