package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Triangle implements Drawable {
  private final Point origin;
  private final Point destination;
  private int length;
  private int offset;
  private int strokeWidth;
  private ColorPalette colorPalette;

  public static final ColorPalette DEFAULT_TRIANGLE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VisualNode.NODE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, VisualNode.NODE_STROKE_COLOR)
      .build();

  public static final ColorPalette CONNECTION_TRIANGLE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VisualConnection.COLOR_CONNECTION)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, VisualConnection.COLOR_CONNECTION)
      .build();

  public static final ColorPalette RED_TRIANGLE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, new Color(255, 80, 80))
      .build();

  public Triangle() {
    this(0, 0, 0, DEFAULT_TRIANGLE_COLOR_PALETTE);
  }

  public Triangle(int length, int offset, int strokeWidth, ColorPalette colorPalette) {
    this.origin = new Point();
    this.destination = new Point();
    this.length = length;
    this.offset = offset;
    this.strokeWidth = strokeWidth;
    this.colorPalette = colorPalette;
  }

  @Override
  public void draw(Graphics2D g) {
    double dx = destination.x - origin.x;
    double dy = destination.y - origin.y;
    double angle = Math.atan2(dy, dx);
    int distance = (int) (Math.sqrt(dx * dx + dy * dy) - offset);

    AffineTransform originalTransform = g.getTransform();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));

    AffineTransform at = AffineTransform.getTranslateInstance(origin.x, origin.y);
    at.concatenate(AffineTransform.getRotateInstance(angle));
    g.transform(at);

    g.fillPolygon(new int[]{distance, distance - length, distance - length, distance},
        new int[]{0, -length, length, 0}, 4);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(strokeWidth));
    g.drawPolygon(new int[]{distance, distance - length, distance - length, distance},
        new int[]{0, -length, length, 0}, 4);

    g.setTransform(originalTransform);
    g.setColor(defaultColor);
  }

  @Override
  public int getLayer() {
    return AFNDPanel.MIDDLE_LAYER;
  }

  public void setOriginX(int origenX) {
    origin.x = origenX;
  }

  public void setOriginY(int origenY) {
    origin.y = origenY;
  }

  public void setDestinationX(int destinationX) {
    destination.x = destinationX;
  }

  public void setDestinationY(int destinationY) {
    destination.y = destinationY;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public ColorPalette getColorPalette() {
    return colorPalette;
  }

  public void setColorPalette(ColorPalette colorPalette) {
    this.colorPalette = colorPalette;
  }

  public void setStrokeWidth(int strokeWidth) {
    this.strokeWidth = strokeWidth;
  }

}
