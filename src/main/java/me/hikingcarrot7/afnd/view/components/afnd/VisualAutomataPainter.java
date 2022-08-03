package me.hikingcarrot7.afnd.view.components.afnd;

import lombok.RequiredArgsConstructor;
import me.hikingcarrot7.afnd.view.graphics.Drawable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@RequiredArgsConstructor
public class VisualAutomataPainter {
  private final VisualAutomata visualAutomata;

  public void paintAutomata(Graphics2D g) {
    List<VisualConnection> sortedConnections = sortedConnectionsByLayer();
    sortedConnections.forEach(conn -> conn.draw(g));
    visualAutomata.forEachVisualConnection(conn -> conn.draw(g));
    visualAutomata.forEachVisualNode(node -> node.draw(g));
  }

  private List<VisualConnection> sortedConnectionsByLayer() {
    List<VisualConnection> sortedConnections = new ArrayList<>();
    visualAutomata.forEachVisualConnection(sortedConnections::add);
    sortedConnections.sort(Comparator.comparingInt(Drawable::getLayer));
    return sortedConnections;
  }

}
