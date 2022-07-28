package me.hikingcarrot7.afnd.view.graphics;

import javax.swing.*;
import java.awt.*;

public class GraphicsUtils {
  public static void drawStringOnPoint(Graphics2D g, String text, Point pos) {
    drawStringOnPoint(g, text, pos.x, pos.y);
  }

  public static void drawStringOnPoint(Graphics2D g, String text, float x, float y) {
    Rectangle bounds = getStringBounds(g, text);
    g.drawString(text, x - (float) bounds.getWidth() / 2, y + (float) bounds.getHeight() / 2);
  }

  public static Rectangle getStringBounds(Graphics2D g, String text) {
    return new Rectangle(new Dimension(
        getStringWidth(g, text),
        g.getFontMetrics().getAscent())
    );
  }

  public static int getStringWidth(Graphics2D g, String text) {
    return SwingUtilities.computeStringWidth(g.getFontMetrics(), text);
  }

  public static int getStringHeight(Graphics2D g, String text) {
    return getStringBounds(g, text).height;
  }

  public static Point getBoxPositionOnScreen(Dimension screenDimension, Dimension boxDimension, Box.BoxPosition boxPosition, int margin) {
    double xPos = 0;
    double yPos = 0;
    switch (boxPosition) {
      case TOP:
        xPos = (screenDimension.getWidth() - boxDimension.getWidth()) / 2;
        yPos = margin;
        break;
      case BOTTOM:
        xPos = (screenDimension.getWidth() - boxDimension.getWidth()) / 2;
        yPos = screenDimension.getHeight() - boxDimension.getHeight() - margin;
        break;
      case RIGHT:
        xPos = screenDimension.getWidth() - boxDimension.getWidth() - margin;
        yPos = (screenDimension.getHeight() - boxDimension.getHeight()) / 2;
        break;
      case LEFT:
        xPos = margin;
        yPos = (screenDimension.getHeight() - boxDimension.getHeight()) / 2;
        break;
      case TOP_RIGHT:
        xPos = screenDimension.getWidth() - boxDimension.getWidth();
        yPos = margin;
        break;
      case TOP_LEFT:
        xPos = 0;
        yPos = margin;
        break;
      case BOTTOM_RIGHT:
        xPos = screenDimension.getWidth() - boxDimension.getWidth();
        yPos = screenDimension.getHeight() - boxDimension.getHeight() - margin;
        break;
      case BOTTOM_LEFT:
        xPos = 0;
        yPos = screenDimension.getHeight() - boxDimension.getHeight() - margin;
        break;
    }
    return new Point((int) xPos, (int) yPos);
  }

  public static boolean intersects(Rectangle rec1, Rectangle rec2) {
    if (rec1.x > rec2.x + rec2.width || rec2.x > rec1.x + rec1.width) {
      return false;
    }
    return !(rec1.y > rec2.y + rec2.height || rec2.y > rec1.y + rec1.height);
  }

}
