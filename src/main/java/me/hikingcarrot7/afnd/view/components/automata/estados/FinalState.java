package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.automata.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;

import java.awt.*;

public class FinalState extends VisualNode {
  public static final int INTERN_CIRCLE_STROKE_WIDTH = 3;
  public static final int INTERN_CIRCLE_OFFSET = 6;

  public FinalState(String name, Point pos) {
    super(name, pos);
  }

  @Override
  public void draw(Graphics2D g) {
    super.draw(g);

    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(INTERN_CIRCLE_STROKE_WIDTH));
    g.drawOval(
        xCenter() - NODE_RADIUS + INTERN_CIRCLE_OFFSET,
        yCenter() - NODE_RADIUS + INTERN_CIRCLE_OFFSET,
        (NODE_RADIUS - INTERN_CIRCLE_OFFSET) * 2,
        (NODE_RADIUS - INTERN_CIRCLE_OFFSET) * 2
    );

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

}
