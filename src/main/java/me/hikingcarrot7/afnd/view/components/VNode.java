package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

public class VNode extends Blob {
  public static final Color COLOR_VERTICE_RELLENO = new Color(81, 237, 236);
  public static final Color COLOR_VERTICE_STROKE = new Color(70, 206, 205);

  public static final Color COLOR_SELECTED_VERTICE_RELLENO = new Color(158, 244, 162);
  public static final Color COLOR_SELECTED_VERTICE_STROKE = new Color(23, 229, 31);

  public static final int NODE_RADIUS = 25;
  public static final int STROKE_WIDTH = 5;

  public static final ColorPalette DEFAULT_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_VERTICE_RELLENO)
    .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, COLOR_VERTICE_STROKE)
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  public static final ColorPalette SELECTED_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_SELECTED_VERTICE_RELLENO)
    .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, COLOR_SELECTED_VERTICE_STROKE)
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  public static final ColorPalette SELECTED_RUTA_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_VERTICE_RELLENO)
    .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, COLOR_SELECTED_VERTICE_STROKE)
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  public static final ColorPalette RED_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_VERTICE_RELLENO)
    .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, new Color(255, 105, 97))
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  public static final ColorPalette GREEN_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_VERTICE_RELLENO)
    .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, new Color(119, 221, 119))
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  public static final ColorPalette YELLOW_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_VERTICE_RELLENO)
    .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, new Color(253, 253, 150))
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  public static final ColorPalette BLUE_VNODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_VERTICE_RELLENO)
    .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, new Color(174, 198, 207))
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  public VNode(String name, int xCenter, int yCenter, ColorPalette colorPalette) {
    this(name, xCenter, yCenter);
    this.colorPalette = colorPalette;
  }

  public VNode(String name, Point coords) {
    this(name, coords.x, coords.y);
  }

  public VNode(String name, int xCenter, int yCenter) {
    super(xCenter, yCenter, NODE_RADIUS, name, DEFAULT_VNODE_COLOR_PALETTE);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.fillOval(xCenter - NODE_RADIUS, yCenter - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(STROKE_WIDTH));
    g.drawOval(xCenter - NODE_RADIUS, yCenter - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    GraphicsUtils.dibujarStringEnPunto(g, name, xCenter, yCenter);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

}
