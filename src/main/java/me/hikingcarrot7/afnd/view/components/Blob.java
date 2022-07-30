package me.hikingcarrot7.afnd.view.components;

import lombok.Getter;
import lombok.Setter;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.*;

@Getter
@Setter
@Deprecated
public abstract class Blob implements Drawable, Movable {
  protected String element;
  protected Point pos;
  protected int radio;
  protected ColorPalette colorPalette;

  public static final ColorPalette DEFAULT_BLOB_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, Menu.GRAY_TEXT_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Menu.GRAY_TEXT_COLOR)
      .build();

  public Blob(String element, Point pos, int radio, ColorPalette colorPalette) {
    this.element = element;
    this.pos = pos;
    this.radio = radio;
    this.colorPalette = colorPalette;
  }

  @Override
  public void draw(Graphics2D g) {
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    GraphicsUtils.drawStringOnPoint(g, element, pos);

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
