package me.hikingcarrot7.afnd.view.components.automata;

import me.hikingcarrot7.afnd.view.components.Blob;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;

import java.awt.*;

public class VisualNode extends Blob {
  public static final Color NODE_FILL_COLOR = new Color(81, 237, 236);
  public static final Color NODE_STROKE_COLOR = new Color(70, 206, 205);
  public static final Color SELECTED_NODE_FILL_COLOR = new Color(158, 244, 162);
  public static final Color SELECTED_NODE_STROKE_COLOR = new Color(23, 229, 31);
  public static final int NODE_RADIUS = 25;
  public static final int STROKE_WIDTH = 5;

  public static final ColorPalette DEFAULT_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, NODE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, NODE_STROKE_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  public static final ColorPalette SELECTED_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, SELECTED_NODE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, SELECTED_NODE_STROKE_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  public static final ColorPalette SELECTED_RUTA_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, NODE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, SELECTED_NODE_STROKE_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  public VisualNode(String element, Point pos, ColorPalette colorPalette) {
    this(element, pos);
    this.colorPalette = colorPalette;
  }

  public VisualNode(String name, Point pos) {
    super(name, pos, NODE_RADIUS, DEFAULT_VNODE_COLOR_PALETTE);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.fillOval(xCenter() - NODE_RADIUS, yCenter() - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(STROKE_WIDTH));
    g.drawOval(xCenter() - NODE_RADIUS, yCenter() - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    GraphicsUtils.drawStringOnPoint(g, element, pos);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

}
