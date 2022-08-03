package me.hikingcarrot7.afnd.view.components.afnd;

import me.hikingcarrot7.afnd.core.utils.Pair;
import me.hikingcarrot7.afnd.view.components.TextBox;
import me.hikingcarrot7.afnd.view.components.afnd.states.VisualStateFactoryImp;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class AFNDPanel extends JPanel {
  public static final Font DEFAULT_FONT = new Font("Open Sans SemiBold", Font.PLAIN, 14);
  public static final int MAX_LAYER = Integer.MAX_VALUE;
  public static final int MIDDLE_LAYER = Integer.MAX_VALUE / 2;
  public static final int MIN_LAYER = Integer.MIN_VALUE;

  private static AFNDPanel instance;
  private final VisualAutomata visualAutomata;

  public synchronized static AFNDPanel getInstance() {
    if (instance == null) {
      instance = new AFNDPanel();
    }
    return instance;
  }

  private final List<VisualNode> visualNodes;
  private final List<Pair<VisualConnection, Integer>> visualConnections;
  private final List<Drawable> components;
  private final List<JComponent> swingComponents;
  private TextBox textBox;

  private AFNDPanel() {
    visualNodes = new ArrayList<>();
    visualConnections = new ArrayList<>();
    components = new ArrayList<>();
    swingComponents = new ArrayList<>();
    visualAutomata = new VisualAutomata(VisualStateFactoryImp.getInstance());
    setLayout(null);
    setFocusable(true);
    requestFocus();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    configGraphics(g2d);

    visualAutomata.draw(g2d);
    if (!textBox.isEmpty()) {
      textBox.draw(g2d);
    }
    EventQueue.invokeLater(() -> swingComponents.forEach(Component::repaint));

    g.dispose();
    g2d.dispose();
  }

  private void configGraphics(Graphics2D g) {
    g.setFont(DEFAULT_FONT);
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    if (textBox == null) {
      textBox = new TextBox.TextBoxBuilder().build();
    }
  }

  public VisualNode getVNode(int idx) {
    return visualNodes.get(idx);
  }

  public List<VisualNode> getVNodes() {
    return visualNodes;
  }

  public void addVArch(VisualConnection varch, int zIndex) {
    visualConnections.add(new Pair<>(varch, zIndex));
    addComponent(varch.getConditionNode());
    addComponent(varch.getTriangle());
    visualConnections.sort(Comparator.comparing(Pair::getRight));
  }

  public void removeVArch(VisualConnection varch) {
    visualConnections.removeIf(pair -> pair.getLeft() == varch);
    components.removeIf(component -> component == varch.getConditionNode()
        || component == varch.getTriangle());
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

  public void addComponent(Drawable drawable) {
    if (!components.contains(drawable)) {
      components.add(drawable);
      components.sort(Comparator.comparingInt(Drawable::getLayer));
    }
  }

  public void removeComponent(Drawable drawable) {
    components.remove(drawable);
  }

  public void addSwingComponent(JComponent component) {
    swingComponents.add(component);
    add(component);
  }

  public TextBox textBox() {
    return textBox;
  }

  public VisualAutomata getVisualAutomata() {
    return visualAutomata;
  }

}
