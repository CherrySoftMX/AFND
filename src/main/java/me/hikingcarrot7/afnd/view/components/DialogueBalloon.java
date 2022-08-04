package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.components.afnd.AutomataPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.*;

import java.awt.*;

public class DialogueBalloon implements Box, Drawable {
  public static final int RADIUS_BORDER = 10;
  public static final int PADDING = 7;
  public static final int MARGIN = 5;
  public static final int TRIANGLE_HEIGHT = 10;
  public static final int TRIANGLE_LENGTH = 25;

  public static final Color DEFAULT_GLOBE_FILL_COLOR = new Color(249, 247, 136);
  public static final Color DEFAULT_GLOBE_STROKE_COLOR = new Color(241, 221, 1);

  public static final ColorPalette DEFAULT_GLOBE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, DEFAULT_GLOBE_FILL_COLOR)
      .addColor(ColorPalette.ColorKey.STROKE_COLOR_KEY, DEFAULT_GLOBE_STROKE_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Color.BLACK)
      .build();

  private final AutomataPanel panel;
  private final VisualNode relativeTo;
  private final ColorPalette colorPalette;
  private final Point pos;
  private String text;
  private Rectangle textBounds;
  private BoxPosition boxPosition;

  public DialogueBalloon(AutomataPanel panel, VisualNode relativeTo, String text, ColorPalette colorPalette) {
    this.panel = panel;
    this.relativeTo = relativeTo;
    this.colorPalette = colorPalette;
    this.text = text;
    this.pos = new Point();
  }

  public DialogueBalloon(AutomataPanel panel, VisualNode relativeTo, String text) {
    this(panel, relativeTo, text, DEFAULT_GLOBE_COLOR_PALETTE);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    drawRelativeTo(g);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public int getLayer() {
    return AutomataPanel.MIDDLE_LAYER;
  }

  private void drawRelativeTo(Graphics2D g) {
    textBounds = GraphicsUtils.getStringBounds(g, text);

    if (canPlaceOnTop()) {
      pos.x = relativeTo.xCenter() - textBounds.width / 2 - 7;
      pos.y = relativeTo.yCenter() - relativeTo.radius() - (textBounds.height + PADDING * 2) - TRIANGLE_HEIGHT - MARGIN;
      boxPosition = BoxPosition.TOP;
    } else if (canPlaceOnRight()) {
      pos.x = relativeTo.xCenter() + relativeTo.radius() + MARGIN + TRIANGLE_HEIGHT;
      pos.y = relativeTo.yCenter() - (textBounds.height / 2 + PADDING);
      boxPosition = BoxPosition.RIGHT;
    } else if (canPlaceOnBottom()) {
      pos.x = relativeTo.xCenter() - textBounds.width / 2 - 7;
      pos.y = relativeTo.yCenter() + relativeTo.radius() + TRIANGLE_HEIGHT + MARGIN;
      boxPosition = BoxPosition.BOTTOM;
    } else if (canPlaceOnLeft()) {
      pos.x = relativeTo.xCenter() - (textBounds.width + PADDING * 2) - TRIANGLE_HEIGHT - MARGIN - relativeTo.radius();
      pos.y = relativeTo.yCenter() - (textBounds.height / 2 + PADDING);
      boxPosition = BoxPosition.LEFT;
    }

    drawBox(g, boxPosition);
  }

  private boolean canPlaceOnTop() {
    return relativeTo.yCenter() - VisualNode.NODE_RADIUS >= getHeight()
        && relativeTo.xCenter() + getWidth() / 2 <= panel.getWidth() - Menu.MENU_WIDTH
        && relativeTo.xCenter() - getWidth() / 2 >= 0;
  }

  private boolean canPlaceOnBottom() {
    return relativeTo.yCenter() + VisualNode.NODE_RADIUS + getHeight() <= panel.getHeight()
        && relativeTo.xCenter() + getWidth() / 2 <= panel.getWidth() - Menu.MENU_WIDTH;
  }

  private boolean canPlaceOnRight() {
    return relativeTo.xCenter() + VisualNode.NODE_RADIUS + getWidth() <= panel.getWidth() - Menu.MENU_WIDTH;
  }

  private boolean canPlaceOnLeft() {
    return relativeTo.xCenter() - VisualNode.NODE_RADIUS - getWidth() >= 0;
  }

  @Override
  public void drawBox(Graphics2D g, BoxPosition boxPosition) {
    Dimension boxDimension = new Dimension(getBoxWidth(), getBoxHeight());
    Shape shape = DialogueBalloonFactory.getInstance().createShape(pos, boxDimension, boxPosition);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.fill(shape);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.STROKE_COLOR_KEY));
    g.setStroke(new BasicStroke(2));
    g.draw(shape);

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    GraphicsUtils.drawStringOnPoint(g, text, pos.x + boxDimension.width / 2, pos.y + boxDimension.height / 2);
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public int getWidth() {
    return getBoxWidth() + TRIANGLE_HEIGHT + MARGIN;
  }

  @Override
  public int getHeight() {
    return getBoxHeight() + TRIANGLE_HEIGHT + MARGIN;
  }

  public int getBoxWidth() {
    return textBounds.width + PADDING * 2;
  }

  public int getBoxHeight() {
    return textBounds.height + PADDING * 2;
  }

}
