package me.hikingcarrot7.afnd.view.components.afnd;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.utils.MathHelper;

import java.awt.*;
import java.util.List;

public class GraphUtils {

  private GraphUtils() {
  }

  public static int getPressedNode(AFNDGraph<?> afndGraph, List<VisualNode> vnodes, Point mouse) {
    for (int i = 0; i < afndGraph.cardinality(); i++) {
      Point center = new Point(vnodes.get(i).xCenter(), vnodes.get(i).yCenter());
      if (MathHelper.distanceBetweenTwoPoints(center, mouse) <= VisualNode.NODE_RADIUS) {
        return i;
      }
    }
    return -1;
  }

}
