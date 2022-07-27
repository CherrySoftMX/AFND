package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.graphics.ColorPalette;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class InitialFinalState extends InitialState {

  public InitialFinalState(String name, int xCenter, int yCenter) {
    super(name, xCenter, yCenter);
  }

  @Override
  public void draw(Graphics2D g) {
    super.draw(g);

    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(FinalState.INTERN_CIRCLE_STROKE_WIDTH));
    g.drawOval(
        xCenter - NODE_RADIUS + FinalState.INTERN_CIRCLE_OFFSET,
        yCenter - NODE_RADIUS + FinalState.INTERN_CIRCLE_OFFSET,
        (NODE_RADIUS - FinalState.INTERN_CIRCLE_OFFSET) * 2,
        (NODE_RADIUS - FinalState.INTERN_CIRCLE_OFFSET) * 2
    );

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

}
