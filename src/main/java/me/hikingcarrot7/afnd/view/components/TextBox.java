package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.view.graphics.Box;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;
import me.hikingcarrot7.afnd.view.graphics.TextBoxFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.ArrayList;

public class TextBox implements Box, Drawable {
  public static final int RADIO_BORDER = 10;
  public static final int MARGIN = 15;
  public static final int PADDING = 7;
  public static final int LINE_SPACING = 8;
  public static final int TITLE_MARGIN_BOTTOM = 12;
  public static final int FOOTER_MARGIN_TOP = 12;
  public static final int CONTENT_INDENTATION = 10;
  public static final Color DEFAULT_TEXTBOX_FILL_COLOR = new Color(255, 141, 141);

  public static final ColorPalette DEFAULT_TEXTBOX_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, DEFAULT_TEXTBOX_FILL_COLOR)
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  public static final ColorPalette RED_TEXTBOX_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VArch.RED_VARCH_COLOR)
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.WHITE)
    .build();

  public static final ColorPalette GREEN_TEXTBOX_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
    .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VArch.COLOR_SELECTED_ARCH)
    .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
    .build();

  private final VAFND vgraph;
  private int width = -1;
  private int height = -1;
  private String title;
  private String footer;
  private ArrayList<String> content;
  private ColorPalette colorPalette;
  private BoxPosition boxPosition;

  public TextBox(VAFND vgraph, String title, ArrayList<String> content, String footer, BoxPosition boxPosition, ColorPalette colorPalette) {
    this.title = title;
    this.content = content;
    this.footer = footer;
    this.boxPosition = boxPosition;
    this.colorPalette = colorPalette;
    this.vgraph = vgraph;
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    if (width < 0) {
      width = calculateWidth(g);
    }

    if (height < 0) {
      height = calculateHeight(g);
    }

    Point coords = GraphicsUtils.getBoxPositionOnScreen(vgraph.getSize(), new Dimension(getWidth(), getHeight()), boxPosition, MARGIN);
    drawBox(g, coords.x, coords.y, getWidth(), getHeight(), boxPosition);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public void drawBox(Graphics2D g, int xPos, int yPos, int width, int height, BoxPosition boxPosition) {
    Shape shape = TextBoxFactory.getInstance().createShape(xPos, yPos, width, height, boxPosition);

    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.fill(shape);

    g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    drawTextBoxContent(g, xPos, yPos);
  }

  private void drawTextBoxContent(Graphics2D g, int xPos, int yPos) {
    int xStartPos = xPos + PADDING;
    int yStartPos = yPos + PADDING;

    if (!title.isEmpty()) {
      yStartPos += GraphicsUtils.getStringHeight(g, title);
      g.drawString(title, xStartPos, yStartPos);
      yStartPos += TITLE_MARGIN_BOTTOM;
    }

    for (int i = 0; i < content.size(); i++) {
      String line = content.get(i);
      int lineHeight = GraphicsUtils.getStringHeight(g, line);
      g.drawString(line, xStartPos + CONTENT_INDENTATION, yStartPos + lineHeight);

      yStartPos += (lineHeight + LINE_SPACING);
    }

    yStartPos -= LINE_SPACING;

    if (!footer.isEmpty()) {
      yStartPos += FOOTER_MARGIN_TOP;
    }

    g.drawString(footer, xStartPos, yStartPos + GraphicsUtils.getStringHeight(g, footer));
  }

  private int calculateWidth(Graphics2D g) {
    int width = GraphicsUtils.getStringWidth(g, title);

    for (int i = 0; i < content.size(); i++) {
      int lineWidth = GraphicsUtils.getStringWidth(g, content.get(i)) + CONTENT_INDENTATION;
      if (lineWidth > width) {
        width = lineWidth;
      }
    }

    int footerWidth = GraphicsUtils.getStringWidth(g, footer);

    if (footerWidth >= width) {
      width = footerWidth;
    }

    return width + PADDING * 2;
  }

  private int calculateHeight(Graphics2D g) {
    int height = 0;

    if (isEmpty()) {
      return height;
    }

    if (!title.isEmpty()) {
      height += GraphicsUtils.getStringHeight(g, title);
      height += TITLE_MARGIN_BOTTOM;
    }

    for (int i = 0; i < content.size(); i++) {
      height += GraphicsUtils.getStringHeight(g, content.get(i));
      height += LINE_SPACING;
    }

    height -= LINE_SPACING;

    if (!footer.isEmpty()) {
      height += GraphicsUtils.getStringHeight(g, footer);
      height += FOOTER_MARGIN_TOP;
    }

    return height + PADDING * 2;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
    width = -1;
    height = -1;
  }

  public ArrayList<String> getContent() {
    return content;
  }

  public void setContent(ArrayList<String> content) {
    this.content = content;
    width = -1;
    height = -1;
  }

  public void addLine(String line) {
    content.add(line);
    width = -1;
    height = -1;
  }

  public String getFooter() {
    return footer;
  }

  public void setFooter(String footer) {
    this.footer = footer;
    width = -1;
    height = -1;
  }

  public BoxPosition getBoxPosition() {
    return boxPosition;
  }

  public void setBoxPosition(BoxPosition boxPosition) {
    this.boxPosition = boxPosition;
  }

  public ColorPalette getColorPalette() {
    return colorPalette;
  }

  public void setColorPalette(ColorPalette colorPalette) {
    this.colorPalette = colorPalette;
  }

  public boolean isEmpty() {
    return title.isEmpty() && footer.isEmpty() && content.isEmpty();
  }

  public void clearTextBox() {
    title = "";
    footer = "";
    content.clear();
  }

  public static class TextBoxBuilder {

    private final ArrayList<String> content;
    private String title;
    private String footer;
    private BoxPosition boxPosition;
    private ColorPalette colorPalette;

    public TextBoxBuilder() {
      title = footer = "";
      content = new ArrayList<>();
      boxPosition = BoxPosition.BOTTOM_LEFT;
      colorPalette = DEFAULT_TEXTBOX_COLOR_PALETTE;
    }

    public TextBoxBuilder setTitle(String title) {
      this.title = title;
      return this;
    }

    public TextBoxBuilder addContentLine(String line) {
      content.add(line);
      return this;
    }

    public TextBoxBuilder setFooter(String footer) {
      this.footer = footer;
      return this;
    }

    public TextBoxBuilder setBoxPosition(BoxPosition boxPosition) {
      this.boxPosition = boxPosition;
      return this;
    }

    public TextBoxBuilder setColorPalette(ColorPalette colorPalette) {
      this.colorPalette = colorPalette;
      return this;
    }

    public TextBox build() {
      return new TextBox(VAFND.getInstance(), title, content, footer, boxPosition, colorPalette);
    }

  }

}
