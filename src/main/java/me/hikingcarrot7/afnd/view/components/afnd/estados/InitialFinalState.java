package me.hikingcarrot7.afnd.view.components.afnd.estados;

import me.hikingcarrot7.afnd.view.graphics.ColorPalette;

import java.awt.*;

public class InitialFinalState extends InitialState {

  public InitialFinalState(String element, Point pos) {
    super(element, pos);
  }

  @Override
  public void draw(Graphics2D g) {
    super.draw(g);

    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(FinalState.INTERN_CIRCLE_STROKE_WIDTH));
    g.drawOval(
        xCenter() - NODE_RADIUS + FinalState.INTERN_CIRCLE_OFFSET,
        yCenter() - NODE_RADIUS + FinalState.INTERN_CIRCLE_OFFSET,
        (NODE_RADIUS - FinalState.INTERN_CIRCLE_OFFSET) * 2,
        (NODE_RADIUS - FinalState.INTERN_CIRCLE_OFFSET) * 2
    );

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

}
