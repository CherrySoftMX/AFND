package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.automata.VisualNode;

import java.awt.Point;

public interface AFNDStateFactory {

  VisualNode createState(int id, String data, Point center);

}
