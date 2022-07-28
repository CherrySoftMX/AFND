package me.hikingcarrot7.afnd.view.components.automata;

import lombok.Getter;
import lombok.Setter;
import me.hikingcarrot7.afnd.view.components.Blob;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

@Getter
@Setter
public abstract class VisualConnection implements Drawable {
  public static final int TRIANGLE_LENGTH = 7;
  public static final int STROKE_WIDTH = 2;
  public static final int BLOB_PADDING = 3;
  public static final int ALTURA_CURVATURA = 40;
  public static final Color COLOR_ARCH = new Color(155, 92, 181);
  public static final Color COLOR_SELECTED_ARCH = new Color(25, 229, 39);
  public static final Color RED_VARCH_COLOR = new Color(255, 80, 80);

  public static final ColorPalette DEFAULT_VARCH_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_ARCH)
      .build();

  public static final ColorPalette SELECTED_VARCH_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_SELECTED_ARCH)
      .build();

  public static final ColorPalette RED_VARCH_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, RED_VARCH_COLOR)
      .build();

  protected String condition;
  protected Movable origin;
  protected Movable destination;
  protected Blob blob;
  protected Triangle triangle;
  protected ColorPalette colorPalette;
  protected boolean previewMode;

  public VisualConnection(Movable origin, Movable destination, String condition, ColorPalette colorPalette) {
    this(origin, destination, condition, false, colorPalette);
  }

  public VisualConnection(Movable origin, Movable destination, String condition, boolean previewMode, ColorPalette colorPalette) {
    this(origin, destination, condition, previewMode);
    this.colorPalette = colorPalette;
  }

  public VisualConnection(Movable origin, Movable destination, String condition) {
    this(origin, destination, condition, false);
  }

  public VisualConnection(Movable origin, Movable destination, boolean previewMode) {
    this(origin, destination, "", previewMode);
  }

  public VisualConnection(Movable origin, Movable destination, String condition, boolean previewMode) {
    this.origin = origin;
    this.destination = destination;
    this.condition = condition;
    this.previewMode = previewMode;
    this.colorPalette = DEFAULT_VARCH_COLOR_PALETTE;
    this.blob = new Blob();
    this.triangle = new Triangle();
    this.triangle.setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
  }

  public abstract void updateTrianglePosition(Graphics2D g, Point origin, Point destination, int alturaCurvatura);

  public abstract void updateBlobPosition(Graphics2D g, Point origin, Point destination);

}
