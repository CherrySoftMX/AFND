package com.cherrysoft.afnd.view.components.afnd.states;

import com.cherrysoft.afnd.view.components.Triangle;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;

import java.awt.*;

public class InitialState extends VisualNode {
  public static final int INITIAL_STATE_ID = 1;
  public static final int TRIANGLE_LENGTH = 25;
  public static final int TRIANGLE_STROKE_WIDTH = 4;
  private final Triangle triangle;

  public InitialState(String element, Point pos) {
    super(element, pos, VisualNode.DEFAULT_NODE_COLOR_PALETTE);
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
    triangle.setOriginX(xCenter() - TRIANGLE_LENGTH - VisualNode.NODE_RADIUS - VisualNode.STROKE_WIDTH);
    triangle.setOriginY(yCenter());
    triangle.setDestinationX(xCenter());
    triangle.setDestinationY(yCenter());
    triangle.setLength(TRIANGLE_LENGTH);
    triangle.setOffset(VisualNode.NODE_RADIUS + VisualNode.STROKE_WIDTH);
  }

}
