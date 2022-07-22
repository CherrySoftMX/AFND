package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class VArch implements Drawable {
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

  protected String condicion;
  protected Movable origen;
  protected Movable destino;
  protected Blob blob;
  protected Triangle triangle;
  protected ColorPalette colorPalette;
  protected boolean previewMode;

  public VArch(Movable origen, Movable destino, String condicion, ColorPalette colorPalette) {
    this(origen, destino, condicion, false, colorPalette);
  }

  public VArch(Movable origen, Movable destino, String condicion, boolean previewMode, ColorPalette colorPalette) {
    this(origen, destino, condicion, previewMode);
    this.colorPalette = colorPalette;
  }

  public VArch(Movable origen, Movable destino, String condicion) {
    this(origen, destino, condicion, false);
  }

  public VArch(Movable origen, Movable destino, boolean previewMode) {
    this(origen, destino, "", previewMode);
  }

  public VArch(Movable origen, Movable destino, String condicion, boolean previewMode) {
    this.origen = origen;
    this.destino = destino;
    this.condicion = condicion;
    this.previewMode = previewMode;
    this.colorPalette = DEFAULT_VARCH_COLOR_PALETTE;
    this.blob = new Blob();
    this.triangle = new Triangle();
    this.triangle.setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
  }

  public abstract void updateTrianglePosition(Graphics2D g, Point origen, Point destino, int alturaCurvatura);

  public abstract void updateBlobPosition(Graphics2D g, Point origen, Point destino);

  public Movable getOrigen() {
    return origen;
  }

  public void setOrigen(Movable origen) {
    this.origen = origen;
  }

  public Movable getDestino() {
    return destino;
  }

  public void setDestino(Movable destino) {
    this.destino = destino;
  }

  public ColorPalette getColorPalette() {
    return colorPalette;
  }

  public void setColorPalette(ColorPalette colorPalette) {
    this.colorPalette = colorPalette;
  }

  public String getCondicion() {
    return condicion;
  }

  public void setCondicion(String character) {
    this.condicion = character;
  }

  public Blob getBlob() {
    return blob;
  }

  public void setBlob(Blob blob) {
    this.blob = blob;
  }

  public Triangle getTriangle() {
    return triangle;
  }

  public boolean isPreviewMode() {
    return previewMode;
  }

  public void setPreviewMode(boolean previewMode) {
    this.previewMode = previewMode;
  }

}
