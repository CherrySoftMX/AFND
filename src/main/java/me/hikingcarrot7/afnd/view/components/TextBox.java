package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.components.afnd.AutomataPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.graphics.*;

import java.awt.*;
import java.util.ArrayList;

public class TextBox implements Box, Drawable {
  public static final int RADIUS_BORDER = 10;
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
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VisualConnection.RED_CONNECTION_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.WHITE)
      .build();

  public static final ColorPalette GREEN_TEXTBOX_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VisualConnection.COLOR_SELECTED_CONNECTION)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  private final AutomataPanel visualAutomata;
  private final Dimension dimension = new Dimension(-1, -1);
  private final BoxPosition boxPosition;
  private final ArrayList<String> content;
  private Point pos;
  private String title;
  private String footer;
  private ColorPalette colorPalette;

  public TextBox(AutomataPanel visualAutomata, String title, ArrayList<String> content, String footer, BoxPosition boxPosition, ColorPalette colorPalette) {
    this.title = title;
    this.content = content;
    this.footer = footer;
    this.boxPosition = boxPosition;
    this.colorPalette = colorPalette;
    this.visualAutomata = visualAutomata;
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    if (dimension.width < 0) {
      dimension.width = calculateWidth(g);
    }

    if (dimension.height < 0) {
      dimension.height = calculateHeight(g);
    }

    pos = GraphicsUtils.getBoxPositionOnScreen(visualAutomata.getSize(), dimension, boxPosition, MARGIN);
    drawBox(g, boxPosition);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public int getLayer() {
    return AutomataPanel.MAX_LAYER;
  }

  @Override
  public void drawBox(Graphics2D g, BoxPosition boxPosition) {
    Shape shape = TextBoxFactory.getInstance().createShape(pos.x, pos.y, getWidth(), getHeight(), boxPosition);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.fill(shape);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    drawTextBoxContent(g);
  }

  private void drawTextBoxContent(Graphics2D g) {
    int xStartPos = pos.x + PADDING;
    int yStartPos = pos.y + PADDING;

    if (!title.isEmpty()) {
      yStartPos += GraphicsUtils.getStringHeight(g, title);
      g.drawString(title, xStartPos, yStartPos);
      yStartPos += TITLE_MARGIN_BOTTOM;
    }

    for (String line : content) {
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

    for (String s : content) {
      int lineWidth = GraphicsUtils.getStringWidth(g, s) + CONTENT_INDENTATION;
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

    for (String s : content) {
      height += GraphicsUtils.getStringHeight(g, s);
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
    return dimension.width;
  }

  @Override
  public int getHeight() {
    return dimension.height;
  }

  public void setTitle(String title) {
    this.title = title;
    dimension.width = -1;
    dimension.height = -1;
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

  public void clear() {
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
      return new TextBox(AutomataPanel.getInstance(), title, content, footer, boxPosition, colorPalette);
    }

  }

}
