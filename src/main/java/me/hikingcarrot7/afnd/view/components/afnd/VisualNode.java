package me.hikingcarrot7.afnd.view.components.afnd;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.hikingcarrot7.afnd.core.graphs.GraphNode;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.*;

@Getter
@Setter
public class VisualNode extends GraphNode<String> implements Drawable, Movable {
  public static final Color NODE_FILL_COLOR = new Color(81, 237, 236);
  public static final Color NODE_STROKE_COLOR = new Color(70, 206, 205);
  public static final Color SELECTED_NODE_FILL_COLOR = new Color(158, 244, 162);
  public static final Color SELECTED_NODE_STROKE_COLOR = new Color(23, 229, 31);
  public static final int NODE_RADIUS = 25;
  public static final int STROKE_WIDTH = 5;

  protected Point pos;
  @Accessors(fluent = true)
  protected int radius;
  protected ColorPalette colorPalette;

  public static final ColorPalette DEFAULT_NODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, NODE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, NODE_STROKE_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  public static final ColorPalette SELECTED_NODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, SELECTED_NODE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, SELECTED_NODE_STROKE_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  public static final ColorPalette SELECTED_PATH_NODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, NODE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, SELECTED_NODE_STROKE_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  public VisualNode(String element) {
    this(element, new Point());
  }

  public VisualNode(String element, Point pos) {
    super(element);
    this.pos = pos;
    this.radius = NODE_RADIUS;
    this.colorPalette = DEFAULT_NODE_COLOR_PALETTE;
  }

  public VisualNode(String element, Point pos, ColorPalette colorPalette) {
    this(element, pos);
    this.colorPalette = colorPalette;
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.fillOval(xCenter() - NODE_RADIUS, yCenter() - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(STROKE_WIDTH));
    g.drawOval(xCenter() - NODE_RADIUS, yCenter() - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    GraphicsUtils.drawStringOnPoint(g, element(), pos);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public int xCenter() {
    return pos.x;
  }

  @Override
  public void setXCenter(int xPos) {
    pos.x = xPos;
  }

  @Override
  public int yCenter() {
    return pos.y;
  }

  @Override
  public void setYCenter(int yPos) {
    pos.y = yPos;
  }

  @Override
  public Point getPos() {
    return pos;
  }

}
