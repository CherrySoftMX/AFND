package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractButton implements Drawable {
  public static final int MAX_BUTTON_WIDTH = 110;
  public static final int MAX_BUTTON_HEIGHT = 35;
  public static final Color DEFAULT_COLOR = new Color(60, 150, 215);
  public static final ColorPalette DEFAULT_BUTTON_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, Color.WHITE)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, DEFAULT_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, DEFAULT_COLOR)
      .build();

  protected final int id;
  protected List<String> content;
  protected ColorPalette colorPalette;
  protected int fontSize;
  protected int xPos;
  protected int yPos;
  protected int width;
  protected int height;

  public AbstractButton(List<String> content, int xPos, int yPos, int width, int height, int id, int fontSize, ColorPalette colorPalette) {
    this.content = content;
    this.xPos = xPos;
    this.yPos = yPos;
    this.width = width;
    this.height = height;
    this.id = id;
    this.fontSize = fontSize;
    this.colorPalette = colorPalette;
  }

  public AbstractButton(List<String> content, Point coords, Dimension dimension, int id, int fontSize, ColorPalette colorPalette) {
    this(content, coords.x, coords.y, dimension.width, dimension.height, id, fontSize, colorPalette);
  }

  public void drawContent(Graphics2D g) {
    int heightPerLine = height / content.size();

    for (int i = 0; i < content.size(); i++) {
      String line = content.get(i);
      GraphicsUtils.drawStringOnPoint(g, line,
          xPos + width / 2,
          yPos + heightPerLine / 2 + i * heightPerLine - 1);
    }
  }

  public abstract void click(InputEvent event);

  public abstract void blur();

  public void setPosition(int xPos, int yPos) {
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public void setPosition(Point coords) {
    this.xPos = coords.x;
    this.yPos = coords.y;
  }

  public boolean isClicked(Point point) {
    return GraphicsUtils.intersects(new Rectangle(xPos, yPos, width, height), new Rectangle(point));
  }

  public int getID() {
    return id;
  }

  public List<String> getContent() {
    return content;
  }

  public void setContent(List<String> content) {
    this.content = content;
  }

  public ColorPalette getColorPalette() {
    return colorPalette;
  }

  public void setColorPalette(ColorPalette colorPalette) {
    this.colorPalette = colorPalette;
  }

  public int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public int getxPos() {
    return xPos;
  }

  public void setxPos(int xPos) {
    this.xPos = xPos;
  }

  public int getyPos() {
    return yPos;
  }

  public void setyPos(int yPos) {
    this.yPos = yPos;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public static class ButtonBuilder {

    private Dimension dimension;
    private Point position;
    private ColorPalette colorPalette;
    private final List<String> content;
    private int id;
    private int fontSize;

    public ButtonBuilder() {
      this.position = new Point(0, 0);
      this.dimension = new Dimension(AbstractButton.MAX_BUTTON_WIDTH, AbstractButton.MAX_BUTTON_HEIGHT);
      this.colorPalette = AbstractButton.DEFAULT_BUTTON_COLOR_PALETTE;
      this.content = new ArrayList<>();
      this.id = -1;
      this.fontSize = 13;
    }

    public ButtonBuilder addLine(String line) {
      this.content.add(line);
      return this;
    }

    public ButtonBuilder setPosition(int xPos, int yPos) {
      this.position = new Point(xPos, yPos);
      return this;
    }

    public ButtonBuilder setDimension(int width, int height) {
      this.dimension = new Dimension(width, height);
      return this;
    }

    public ButtonBuilder setColorPalette(ColorPalette colorPalette) {
      this.colorPalette = colorPalette;
      return this;
    }

    public ButtonBuilder setID(int id) {
      this.id = id;
      return this;
    }

    public ButtonBuilder setFontSize(int fontSize) {
      this.fontSize = fontSize;
      return this;
    }

    public AbstractButton build() {
      if (id == Menu.COMPROBAR_AUTOMATA_ID) {
        return new Button(content, position, dimension, id, fontSize, colorPalette);
      }
      return new ToggleButton(content, position, dimension, id, fontSize, colorPalette);
    }

  }

}
