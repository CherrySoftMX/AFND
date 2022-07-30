package me.hikingcarrot7.afnd.core.utils;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAFND;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GraphUtils {

  private GraphUtils() {
  }

  public static int getPressedNode(AFNDGraph<?> afndGraph, List<VisualNode> vnodes, Point mouse) {
    for (int i = 0; i < afndGraph.cardinality(); i++) {
      Point center = new Point(vnodes.get(i).xCenter(), vnodes.get(i).yCenter());
      if (MathHelper.distanciaEntreDosPuntos(center, mouse) <= VisualNode.NODE_RADIUS) {
        return i;
      }
    }
    return -1;
  }

  public static List<VisualConnection> getAdjacentVArchs(VisualNode origen, VisualAFND visualAFND) {
    List<VisualConnection> adjacentVisualConnections = new ArrayList<>();
    List<VisualConnection> varches = visualAFND.getVisualConnections();
    varches.forEach(varch -> {
      if (origen == varch.getOrigin()) {
        adjacentVisualConnections.add(varch);
      }
    });
    return adjacentVisualConnections;
  }
}
