package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.graphics.ColorPalette;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

public class Button extends AbstractButton {
  public static final ColorPalette BUTTON_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, DEFAULT_COLOR)
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.WHITE)
    .build();

  public Button(List<String> content, int xPos, int yPos, int width, int height, int id, int fontSize, ColorPalette colorPalette) {
    super(content, xPos, yPos, width, height, id, fontSize, colorPalette);
  }

  public Button(List<String> content, Point coords, Dimension dimension, int id, int fontSize, ColorPalette colorPalette) {
    super(content, coords, dimension, id, fontSize, colorPalette);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();
    Font defaultFont = g.getFont();

    g.setFont(new Font(defaultFont.getName(), defaultFont.getStyle(), fontSize));

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.fillRect(xPos, yPos, width, height);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    super.drawContent(g);

    g.setFont(defaultFont);
    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public void click(InputEvent event) {

  }

  @Override
  public void blur() {

  }

}
