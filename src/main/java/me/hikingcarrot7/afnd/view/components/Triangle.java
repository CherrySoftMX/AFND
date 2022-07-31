package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Triangle implements Drawable {
  private int origenX;
  private int origenY;
  private int destinoX;
  private int destinoY;
  private int length;
  private int offset;
  private int strokeWidth;
  private ColorPalette colorPalette;

  public static final ColorPalette DEFAULT_TRIANGLE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VisualNode.NODE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, VisualNode.NODE_STROKE_COLOR)
      .build();

  public static final ColorPalette VARCH_TRIANGLE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VisualConnection.COLOR_ARCH)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, VisualConnection.COLOR_ARCH)
      .build();

  public static final ColorPalette RED_TRIANGLE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, new Color(255, 80, 80))
      .build();

  public Triangle() {
    this(0, 0, 0, 0, 0, 0, 0, DEFAULT_TRIANGLE_COLOR_PALETTE);
  }

  public Triangle(int origenX, int origenY, int destinoX, int destinoY, int length, int offset, int strokeWidth, ColorPalette colorPalette) {
    this.origenX = origenX;
    this.origenY = origenY;
    this.destinoX = destinoX;
    this.destinoY = destinoY;
    this.length = length;
    this.offset = offset;
    this.strokeWidth = strokeWidth;
    this.colorPalette = colorPalette;
  }

  @Override
  public void draw(Graphics2D g) {
    double dx = destinoX - origenX;
    double dy = destinoY - origenY;
    double angle = Math.atan2(dy, dx);
    int distancia = (int) (Math.sqrt(dx * dx + dy * dy) - offset);

    AffineTransform originalTransform = g.getTransform();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));

    AffineTransform at = AffineTransform.getTranslateInstance(origenX, origenY);
    at.concatenate(AffineTransform.getRotateInstance(angle));
    g.transform(at);

    g.fillPolygon(new int[]{distancia, distancia - length, distancia - length, distancia},
        new int[]{0, -length, length, 0}, 4);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(strokeWidth));
    g.drawPolygon(new int[]{distancia, distancia - length, distancia - length, distancia},
        new int[]{0, -length, length, 0}, 4);

    g.setTransform(originalTransform);
    g.setColor(defaultColor);
  }

  public int getOrigenX() {
    return origenX;
  }

  public void setOrigenX(int origenX) {
    this.origenX = origenX;
  }

  public int getOrigenY() {
    return origenY;
  }

  public void setOrigenY(int origenY) {
    this.origenY = origenY;
  }

  public int getDestinoX() {
    return destinoX;
  }

  public void setDestinoX(int destinoX) {
    this.destinoX = destinoX;
  }

  public int getDestinoY() {
    return destinoY;
  }

  public void setDestinoY(int destinoY) {
    this.destinoY = destinoY;
  }

  public int getLenght() {
    return length;
  }

  public void setLength(int lenght) {
    this.length = lenght;
  }

  public int getOffset() {
    return offset;
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

  public int getStrokeWidth() {
    return strokeWidth;
  }

  public void setStrokeWidth(int strokeWidth) {
    this.strokeWidth = strokeWidth;
  }

}
