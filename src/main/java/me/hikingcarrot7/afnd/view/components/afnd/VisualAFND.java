package me.hikingcarrot7.afnd.view.components.afnd;

import me.hikingcarrot7.afnd.core.utils.Pair;
import me.hikingcarrot7.afnd.view.components.TextBox;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class VisualAFND extends JPanel implements Drawable {
  public static final Font DEFAULT_FONT = new Font("Open Sans SemiBold", Font.PLAIN, 14);
  public static final int MAX_LAYER = Integer.MAX_VALUE;
  public static final int MIDDLE_LAYER = Integer.MAX_VALUE / 2;
  public static final int MIN_LAYER = Integer.MIN_VALUE;

  private static VisualAFND instance;

  public synchronized static VisualAFND getInstance() {
    if (instance == null) {
      instance = new VisualAFND();
    }
    return instance;
  }

  private final List<VisualNode> visualNodes;
  private final List<Pair<VisualConnection, Integer>> visualConnections;
  private final List<Pair<Drawable, Integer>> components;
  private final List<JComponent> swingComponents;
  private TextBox defaultTextBox;

  private VisualAFND() {
    visualNodes = new ArrayList<>();
    visualConnections = new ArrayList<>();
    components = new ArrayList<>();
    swingComponents = new ArrayList<>();
    setLayout(null);
    setFocusable(true);
    requestFocus();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    configGraphics(g2d);
    draw(g2d);
    g2d.dispose();
  }

  @Override
  public void draw(Graphics2D g) {
    visualConnections.forEach(pair -> pair.getLeft().draw(g));
    visualNodes.forEach(vnode -> vnode.draw(g));
    components.forEach(pair -> pair.getLeft().draw(g));
    if (!defaultTextBox.isEmpty()) {
      defaultTextBox.draw(g);
    }
    EventQueue.invokeLater(() -> swingComponents.forEach(Component::repaint));
    g.dispose();
  }

  private void configGraphics(Graphics2D g) {
    g.setFont(DEFAULT_FONT);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    if (defaultTextBox == null) {
      defaultTextBox = new TextBox.TextBoxBuilder().build();
    }
  }

  public void addVNode(VisualNode visualNode) {
    visualNodes.add(visualNode);
  }

  public VisualNode getVNode(int idx) {
    return visualNodes.get(idx);
  }

  public VisualNode getVNode(String name) {
    for (VisualNode visualNode : visualNodes) {
      if (visualNode.element().equals(name)) {
        return visualNode;
      }
    }
    return null;
  }

  public void removeVNode(VisualNode vnode) {
    visualNodes.remove(vnode);
  }

  public List<VisualNode> getVNodes() {
    return visualNodes;
  }

  public void addVArch(VisualConnection varch, int zIndex) {
    visualConnections.add(new Pair<>(varch, zIndex));
    addComponent(varch.getConditionNode(), MIDDLE_LAYER);
    addComponent(varch.getTriangle(), MIDDLE_LAYER);
    visualConnections.sort(Comparator.comparing(Pair::getRight));
  }

  public void removeVArch(VisualConnection varch) {
    visualConnections.removeIf(pair -> pair.getLeft() == varch);
    components.removeIf(pair -> pair.getLeft() == varch.getConditionNode()
        || pair.getLeft() == varch.getTriangle());
  }

  public void setVArchZIndex(VisualConnection varch, int zIndex) {
    for (Pair<VisualConnection, Integer> vArchIntegerPair : visualConnections) {
      VisualConnection currentVisualConnection = vArchIntegerPair.getLeft();
      if (currentVisualConnection == varch) {
        vArchIntegerPair.setRight(zIndex);
      }
    }
    visualConnections.sort(Comparator.comparing(Pair::getRight));
  }

  public VisualConnection getVArch(Movable origin, Movable destination) {
    for (Pair<VisualConnection, Integer> vArchIntegerPair : visualConnections) {
      VisualConnection visualConnection = vArchIntegerPair.getLeft();
      if (visualConnection.getOrigin() == origin && visualConnection.getDestination() == destination) {
        return visualConnection;
      }
    }
    return null;
  }

  public List<VisualConnection> getVisualConnections() {
    return visualConnections.stream().map(Pair::getLeft).collect(Collectors.toList());
  }

  public void addComponent(Drawable drawable, int zIndex) {
    Pair<Drawable, Integer> pair = new Pair<>(drawable, zIndex);
    if (!components.contains(pair)) {
      components.add(pair);
      components.sort(Comparator.comparing(Pair::getRight));
    }
  }

  public void removeComponent(Drawable drawable) {
    components.removeIf(pair -> pair.getLeft() == drawable);
  }

  public void addSwingComponent(JComponent component) {
    swingComponents.add(component);
    add(component);
  }

  public TextBox getDefaultTextBox() {
    return defaultTextBox;
  }

}
