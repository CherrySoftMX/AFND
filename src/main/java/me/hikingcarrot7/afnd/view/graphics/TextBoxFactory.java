package me.hikingcarrot7.afnd.view.graphics;

import me.hikingcarrot7.afnd.view.components.TextBox;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

public class TextBoxFactory implements ShapeFactory {
  private static TextBoxFactory instance;

  public static synchronized TextBoxFactory getInstance() {
    if (instance == null) {
      instance = new TextBoxFactory();
    }
    return instance;
  }

  private TextBoxFactory() {
  }

  @Override
  public Shape createShape(int xPos, int yPos, int width, int height, Box.BoxPosition boxPosition) {
    GeneralPath path = new GeneralPath();
    switch (boxPosition) {
      case TOP_RIGHT:
      case BOTTOM_RIGHT:
        path.moveTo(xPos, yPos + TextBox.RADIO_BORDER);
        path.curveTo(xPos, yPos + TextBox.RADIO_BORDER / 2, xPos + TextBox.RADIO_BORDER / 2, yPos, xPos + TextBox.RADIO_BORDER, yPos);
        path.lineTo(xPos + width, yPos);
        path.lineTo(xPos + width, yPos + height);
        path.lineTo(xPos + TextBox.RADIO_BORDER, yPos + height);
        path.curveTo(xPos + TextBox.RADIO_BORDER / 2, yPos + height, xPos, yPos + height - TextBox.RADIO_BORDER / 2, xPos, yPos + height - TextBox.RADIO_BORDER);
        path.closePath();
        break;

      case TOP_LEFT:
      case BOTTOM_LEFT:
        path.moveTo(xPos, yPos);
        path.lineTo(xPos + width - TextBox.RADIO_BORDER, yPos);
        path.curveTo(xPos + width - TextBox.RADIO_BORDER / 2, yPos, xPos + width, yPos + TextBox.RADIO_BORDER / 2, xPos + width, yPos + TextBox.RADIO_BORDER);
        path.lineTo(xPos + width, yPos + height - TextBox.RADIO_BORDER);
        path.curveTo(xPos + width, yPos + height - TextBox.RADIO_BORDER / 2, xPos + width - TextBox.RADIO_BORDER / 2, yPos + height, xPos + width - TextBox.RADIO_BORDER, yPos + height);
        path.lineTo(xPos, yPos + height);
        path.closePath();
        break;
      case TOP:
      case BOTTOM:
      case RIGHT:
      case LEFT:
        path.moveTo(xPos, yPos + TextBox.RADIO_BORDER);
        path.curveTo(xPos, yPos + TextBox.RADIO_BORDER / 2, xPos + TextBox.RADIO_BORDER / 2, yPos, xPos + TextBox.RADIO_BORDER, yPos);
        path.lineTo(xPos + width - TextBox.RADIO_BORDER, yPos);
        path.curveTo(xPos + width - TextBox.RADIO_BORDER / 2, yPos, xPos + width, yPos + TextBox.RADIO_BORDER / 2, xPos + width, yPos + TextBox.RADIO_BORDER);
        path.lineTo(xPos + width, yPos + height - TextBox.RADIO_BORDER);
        path.curveTo(xPos + width, yPos + height - TextBox.RADIO_BORDER / 2, xPos + width - TextBox.RADIO_BORDER / 2, yPos + height, xPos + width - TextBox.RADIO_BORDER, yPos + height);
        path.lineTo(xPos + TextBox.RADIO_BORDER, yPos + height);
        path.curveTo(xPos + TextBox.RADIO_BORDER / 2, yPos + height, xPos, yPos + height - TextBox.RADIO_BORDER / 2, xPos, yPos + height - TextBox.RADIO_BORDER);
        path.closePath();
    }
    return path;
  }

}
