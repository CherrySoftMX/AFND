package com.cherrysoft.afnd.view.graphics;

import com.cherrysoft.afnd.view.components.TextBox;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class TextBoxFactory implements ShapeFactory {
  private static TextBoxFactory instance;

  private TextBoxFactory() {
  }

  public static synchronized TextBoxFactory getInstance() {
    if (instance == null) {
      instance = new TextBoxFactory();
    }
    return instance;
  }

  @Override
  public Shape createShape(Point pos, Dimension dimension, Box.BoxPosition position) {
    return createShape(pos.x, pos.y, dimension.width, dimension.height, position);
  }

  public Shape createShape(int xPos, int yPos, int width, int height, Box.BoxPosition boxPosition) {
    GeneralPath path = new GeneralPath();
    switch (boxPosition) {
      case TOP_RIGHT:
      case BOTTOM_RIGHT:
        path.moveTo(xPos, yPos + TextBox.RADIUS_BORDER);
        path.curveTo(xPos, yPos + TextBox.RADIUS_BORDER / 2, xPos + TextBox.RADIUS_BORDER / 2, yPos, xPos + TextBox.RADIUS_BORDER, yPos);
        path.lineTo(xPos + width, yPos);
        path.lineTo(xPos + width, yPos + height);
        path.lineTo(xPos + TextBox.RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + TextBox.RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - TextBox.RADIUS_BORDER / 2, xPos, yPos + height - TextBox.RADIUS_BORDER);
        path.closePath();
        break;

      case TOP_LEFT:
      case BOTTOM_LEFT:
        path.moveTo(xPos, yPos);
        path.lineTo(xPos + width - TextBox.RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - TextBox.RADIUS_BORDER / 2, yPos, xPos + width, yPos + TextBox.RADIUS_BORDER / 2, xPos + width, yPos + TextBox.RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - TextBox.RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - TextBox.RADIUS_BORDER / 2, xPos + width - TextBox.RADIUS_BORDER / 2, yPos + height, xPos + width - TextBox.RADIUS_BORDER, yPos + height);
        path.lineTo(xPos, yPos + height);
        path.closePath();
        break;
      case TOP:
      case BOTTOM:
      case RIGHT:
      case LEFT:
        path.moveTo(xPos, yPos + TextBox.RADIUS_BORDER);
        path.curveTo(xPos, yPos + TextBox.RADIUS_BORDER / 2, xPos + TextBox.RADIUS_BORDER / 2, yPos, xPos + TextBox.RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - TextBox.RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - TextBox.RADIUS_BORDER / 2, yPos, xPos + width, yPos + TextBox.RADIUS_BORDER / 2, xPos + width, yPos + TextBox.RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - TextBox.RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - TextBox.RADIUS_BORDER / 2, xPos + width - TextBox.RADIUS_BORDER / 2, yPos + height, xPos + width - TextBox.RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + TextBox.RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + TextBox.RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - TextBox.RADIUS_BORDER / 2, xPos, yPos + height - TextBox.RADIUS_BORDER);
        path.closePath();
    }
    return path;
  }

}
