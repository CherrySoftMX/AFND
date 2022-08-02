package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
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
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, VisualConnection.COLOR_SELECTED_ARCH)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  private final AFNDPanel vgraph;
  private int width = -1;
  private int height = -1;
  private String title;
  private String footer;
  private ArrayList<String> content;
  private ColorPalette colorPalette;
  private BoxPosition boxPosition;

  public TextBox(AFNDPanel vgraph, String title, ArrayList<String> content, String footer, BoxPosition boxPosition, ColorPalette colorPalette) {
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

    Point pos = GraphicsUtils.getBoxPositionOnScreen(vgraph.getSize(), new Dimension(getWidth(), getHeight()), boxPosition, MARGIN);
    drawBox(g, pos.x, pos.y, getWidth(), getHeight(), boxPosition);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public int getLayer() {
    return AFNDPanel.MAX_LAYER;
  }

  @Override
  public void drawBox(Graphics2D g, int xPos, int yPos, int width, int height, BoxPosition boxPosition) {
    Shape shape = TextBoxFactory.getInstance().createShape(xPos, yPos, width, height, boxPosition);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.fill(shape);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
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
      return new TextBox(AFNDPanel.getInstance(), title, content, footer, boxPosition, colorPalette);
    }

  }

}
