package me.hikingcarrot7.afnd.view.components.afnd.estados;

import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import java.awt.Point;

public interface AFNDStateFactory {

  VisualNode createState(int id, String data, Point center);

}
