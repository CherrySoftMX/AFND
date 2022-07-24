package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.VNode;

import java.awt.Point;

public interface AFNDEstadoFactory {

  VNode createEstado(int id, String data, Point centerCoords);

}
