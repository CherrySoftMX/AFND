package me.hikingcarrot7.afnd.view.components.afnd;

import me.hikingcarrot7.afnd.view.components.TextBox;
import me.hikingcarrot7.afnd.view.components.afnd.connections.VisualConnectionFactoryImp;
import me.hikingcarrot7.afnd.view.components.afnd.states.VisualNodeFactoryImp;
import me.hikingcarrot7.afnd.view.graphics.Drawable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.isNull;

public final class AutomataPanel extends JPanel {
  public static final Font DEFAULT_FONT = new Font("Open Sans SemiBold", Font.PLAIN, 14);
  public static final int MAX_LAYER = Integer.MAX_VALUE;
  public static final int MIDDLE_LAYER = Integer.MAX_VALUE / 2;
  public static final int MIN_LAYER = Integer.MIN_VALUE;

  private static AutomataPanel instance;
  private final VisualAutomata visualAutomata;

  public synchronized static AutomataPanel getInstance() {
    if (instance == null) {
      instance = new AutomataPanel();
    }
    return instance;
  }

  private final List<Drawable> components;
  private final List<JComponent> swingComponents;
  private TextBox textBox;

  private AutomataPanel() {
    components = new ArrayList<>();
    swingComponents = new ArrayList<>();
    visualAutomata = new VisualAutomata(
        VisualNodeFactoryImp.getInstance(),
        VisualConnectionFactoryImp.getInstance()
    );
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
    components.forEach(component -> component.draw(g2d));

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
    if (isNull(textBox)) {
      textBox = new TextBox.TextBoxBuilder().build();
    }
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
