package me.hikingcarrot7.afnd.view.components.automata;

import me.hikingcarrot7.afnd.core.utils.Pair;
import me.hikingcarrot7.afnd.view.components.TextBox;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class VAFND extends JPanel implements Drawable {
  public static final Font DEFAULT_FONT = new Font("Open Sans SemiBold", Font.PLAIN, 14);
  public static final int MAX_LAYER = Integer.MAX_VALUE;
  public static final int MIDDLE_LAYER = Integer.MAX_VALUE / 2;
  public static final int MIN_LAYER = Integer.MIN_VALUE;

  private static VAFND instance;

  public synchronized static VAFND getInstance() {
    if (instance == null) {
      instance = new VAFND();
    }
    return instance;
  }

  private final List<VNode> vnodes;
  private final List<Pair<VArch, Integer>> varchs;
  private final List<Pair<Drawable, Integer>> components;
  private final List<JComponent> swingComponents;
  private TextBox defaultTextBox;

  private VAFND() {
    vnodes = new ArrayList<>();
    varchs = new ArrayList<>();
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
    varchs.forEach(pair -> pair.getLeft().draw(g));
    vnodes.forEach(vnode -> vnode.draw(g));
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

  public void addVNode(VNode vnode) {
    vnodes.add(vnode);
  }

  public VNode getVNode(int idx) {
    return vnodes.get(idx);
  }

  public VNode getVNode(String name) {
    for (VNode vnode : vnodes) {
      if (vnode.getName().equals(name)) {
        return vnode;
      }
    }
    return null;
  }

  public void removeVNode(VNode vnode) {
    vnodes.remove(vnode);
  }

  public List<VNode> getVNodes() {
    return vnodes;
  }

  public void addVArch(VArch varch, int zIndex) {
    varchs.add(new Pair<>(varch, zIndex));
    addComponent(varch.getBlob(), MIDDLE_LAYER);
    addComponent(varch.getTriangle(), MIDDLE_LAYER);
    varchs.sort(Comparator.comparing(Pair::getRight));
  }

  public void removeVArch(VArch varch) {
    varchs.removeIf(pair -> pair.getLeft() == varch);
    components.removeIf(pair -> pair.getLeft() == varch.getBlob()
        || pair.getLeft() == varch.getTriangle());
  }

  public void setVArchZIndex(VArch varch, int zIndex) {
    for (Pair<VArch, Integer> vArchIntegerPair : varchs) {
      VArch currentVArch = vArchIntegerPair.getLeft();
      if (currentVArch == varch) {
        vArchIntegerPair.setRight(zIndex);
      }
    }
    varchs.sort(Comparator.comparing(Pair::getRight));
  }

  public VArch getVArch(Movable origen, Movable destino) {
    for (Pair<VArch, Integer> vArchIntegerPair : varchs) {
      VArch varch = vArchIntegerPair.getLeft();
      if (varch.getOrigen() == origen && varch.getDestino() == destino) {
        return varch;
      }
    }
    return null;
  }

  public List<VArch> getVArchs() {
    return varchs.stream().map(Pair::getLeft).collect(Collectors.toList());
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

  public void removeSwingComponent(JComponent component) {
    swingComponents.remove(component);
    remove(component);
  }

  public List<Drawable> getGraphComponents() {
    return components.stream().map(Pair::getLeft).collect(Collectors.toList());
  }

  public TextBox getDefaultTextBox() {
    return defaultTextBox;
  }

}
