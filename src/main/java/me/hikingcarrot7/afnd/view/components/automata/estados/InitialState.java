package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.automata.VisualNode;

import java.awt.*;

public class InitialState extends VisualNode {
  public static final int TRIANGLE_LENGTH = 25;
  public static final int TRIANGLE_STROKE_WIDTH = 4;
  private final Triangle triangle;

  public InitialState(String element, Point pos) {
    super(element, pos, VisualNode.DEFAULT_VNODE_COLOR_PALETTE);
    this.triangle = new Triangle();
    this.triangle.setStrokeWidth(TRIANGLE_STROKE_WIDTH);
  }

  @Override
  public void draw(Graphics2D g) {
    super.draw(g);
    updateTrianglePos();
    triangle.draw(g);
  }

  private void updateTrianglePos() {
    triangle.setOrigenX(xCenter() - TRIANGLE_LENGTH - VisualNode.NODE_RADIUS - VisualNode.STROKE_WIDTH);
    triangle.setOrigenY(yCenter());
    triangle.setDestinoX(xCenter());
    triangle.setDestinoY(yCenter());
    triangle.setLength(TRIANGLE_LENGTH);
    triangle.setOffset(VisualNode.NODE_RADIUS + VisualNode.STROKE_WIDTH);
  }

}
