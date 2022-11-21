package com.cherrysoft.afnd.view.graphics;

import com.cherrysoft.afnd.view.components.DialogueBalloon;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class DialogueBalloonFactory implements ShapeFactory {
  private static DialogueBalloonFactory instance;

  private DialogueBalloonFactory() {
  }

  public static synchronized ShapeFactory getInstance() {
    if (instance == null) {
      instance = new DialogueBalloonFactory();
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
      case TOP:
        path.moveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + DialogueBalloon.RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - DialogueBalloon.RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos + width - DialogueBalloon.RADIUS_BORDER, yPos + height);

        path.lineTo(xPos + width / 2 + DialogueBalloon.TRIANGLE_LENGTH / 2, yPos + height);
        path.lineTo(xPos + width / 2, yPos + height + DialogueBalloon.TRIANGLE_HEIGHT);
        path.lineTo(xPos + width / 2 - DialogueBalloon.TRIANGLE_LENGTH / 2, yPos + height);
        path.lineTo(xPos + DialogueBalloon.RADIUS_BORDER, yPos + height);

        path.curveTo(xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER);
        path.closePath();
        break;
      case BOTTOM:
        path.moveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + DialogueBalloon.RADIUS_BORDER, yPos);

        path.lineTo(xPos + width / 2 - DialogueBalloon.TRIANGLE_LENGTH / 2, yPos);
        path.lineTo(xPos + width / 2, yPos - DialogueBalloon.TRIANGLE_HEIGHT);
        path.lineTo(xPos + width / 2 + DialogueBalloon.TRIANGLE_LENGTH / 2, yPos);
        path.lineTo(xPos + width - DialogueBalloon.RADIUS_BORDER, yPos);

        path.curveTo(xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos + width - DialogueBalloon.RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + DialogueBalloon.RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER);
        path.closePath();
        break;
      case RIGHT:
        path.moveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + DialogueBalloon.RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - DialogueBalloon.RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos + width - DialogueBalloon.RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + DialogueBalloon.RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER);

        path.lineTo(xPos, yPos + height / 2 + DialogueBalloon.TRIANGLE_LENGTH / 2);
        path.lineTo(xPos - DialogueBalloon.TRIANGLE_HEIGHT, yPos + height / 2);
        path.lineTo(xPos, yPos + height / 2 - DialogueBalloon.TRIANGLE_LENGTH / 2);

        path.closePath();
        break;
      case LEFT:
        path.moveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + DialogueBalloon.RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - DialogueBalloon.RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER);

        path.lineTo(xPos + width, yPos + height / 2 - DialogueBalloon.TRIANGLE_LENGTH / 2);
        path.lineTo(xPos + width + DialogueBalloon.TRIANGLE_HEIGHT, yPos + height / 2);
        path.lineTo(xPos + width, yPos + height / 2 + DialogueBalloon.TRIANGLE_LENGTH / 2);
        path.lineTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER);

        path.curveTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos + width - DialogueBalloon.RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + DialogueBalloon.RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER);
        path.closePath();
      default:
        path.moveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + DialogueBalloon.RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - DialogueBalloon.RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER / 2, xPos + width, yPos + DialogueBalloon.RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos + width - DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos + width - DialogueBalloon.RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + DialogueBalloon.RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + DialogueBalloon.RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER / 2, xPos, yPos + height - DialogueBalloon.RADIUS_BORDER);
        path.closePath();
    }
    return path;
  }

}
