package me.hikingcarrot7.afnd.view.graphics;

import java.awt.*;
import java.awt.geom.GeneralPath;

import static me.hikingcarrot7.afnd.view.components.DialogueBalloon.*;

public class DialogueBalloonFactory implements ShapeFactory {
  private static DialogueBalloonFactory instance;

  public static synchronized ShapeFactory getInstance() {
    if (instance == null) {
      instance = new DialogueBalloonFactory();
    }
    return instance;
  }

  private DialogueBalloonFactory() {
  }

  @Override
  public Shape createShape(Point pos, Dimension dimension, Box.BoxPosition position) {
    return createShape(pos.x, pos.y, dimension.width, dimension.height, position);
  }

  public Shape createShape(int xPos, int yPos, int width, int height, Box.BoxPosition boxPosition) {
    GeneralPath path = new GeneralPath();
    switch (boxPosition) {
      case TOP:
        path.moveTo(xPos, yPos + RADIUS_BORDER);
        path.curveTo(xPos, yPos + RADIUS_BORDER / 2, xPos + RADIUS_BORDER / 2, yPos, xPos + RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - RADIUS_BORDER / 2, yPos, xPos + width, yPos + RADIUS_BORDER / 2, xPos + width, yPos + RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - RADIUS_BORDER / 2, xPos + width - RADIUS_BORDER / 2, yPos + height, xPos + width - RADIUS_BORDER, yPos + height);

        path.lineTo(xPos + width / 2 + TRIANGLE_LENGTH / 2, yPos + height);
        path.lineTo(xPos + width / 2, yPos + height + TRIANGLE_HEIGHT);
        path.lineTo(xPos + width / 2 - TRIANGLE_LENGTH / 2, yPos + height);
        path.lineTo(xPos + RADIUS_BORDER, yPos + height);

        path.curveTo(xPos + RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - RADIUS_BORDER / 2, xPos, yPos + height - RADIUS_BORDER);
        path.closePath();
        break;
      case BOTTOM:
        path.moveTo(xPos, yPos + RADIUS_BORDER);
        path.curveTo(xPos, yPos + RADIUS_BORDER / 2, xPos + RADIUS_BORDER / 2, yPos, xPos + RADIUS_BORDER, yPos);

        path.lineTo(xPos + width / 2 - TRIANGLE_LENGTH / 2, yPos);
        path.lineTo(xPos + width / 2, yPos - TRIANGLE_HEIGHT);
        path.lineTo(xPos + width / 2 + TRIANGLE_LENGTH / 2, yPos);
        path.lineTo(xPos + width - RADIUS_BORDER, yPos);

        path.curveTo(xPos + width - RADIUS_BORDER / 2, yPos, xPos + width, yPos + RADIUS_BORDER / 2, xPos + width, yPos + RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - RADIUS_BORDER / 2, xPos + width - RADIUS_BORDER / 2, yPos + height, xPos + width - RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - RADIUS_BORDER / 2, xPos, yPos + height - RADIUS_BORDER);
        path.closePath();
        break;
      case RIGHT:
        path.moveTo(xPos, yPos + RADIUS_BORDER);
        path.curveTo(xPos, yPos + RADIUS_BORDER / 2, xPos + RADIUS_BORDER / 2, yPos, xPos + RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - RADIUS_BORDER / 2, yPos, xPos + width, yPos + RADIUS_BORDER / 2, xPos + width, yPos + RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - RADIUS_BORDER / 2, xPos + width - RADIUS_BORDER / 2, yPos + height, xPos + width - RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - RADIUS_BORDER / 2, xPos, yPos + height - RADIUS_BORDER);

        path.lineTo(xPos, yPos + height / 2 + TRIANGLE_LENGTH / 2);
        path.lineTo(xPos - TRIANGLE_HEIGHT, yPos + height / 2);
        path.lineTo(xPos, yPos + height / 2 - TRIANGLE_LENGTH / 2);

        path.closePath();
        break;
      case LEFT:
        path.moveTo(xPos, yPos + RADIUS_BORDER);
        path.curveTo(xPos, yPos + RADIUS_BORDER / 2, xPos + RADIUS_BORDER / 2, yPos, xPos + RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - RADIUS_BORDER / 2, yPos, xPos + width, yPos + RADIUS_BORDER / 2, xPos + width, yPos + RADIUS_BORDER);

        path.lineTo(xPos + width, yPos + height / 2 - TRIANGLE_LENGTH / 2);
        path.lineTo(xPos + width + TRIANGLE_HEIGHT, yPos + height / 2);
        path.lineTo(xPos + width, yPos + height / 2 + TRIANGLE_LENGTH / 2);
        path.lineTo(xPos + width, yPos + height - RADIUS_BORDER);

        path.curveTo(xPos + width, yPos + height - RADIUS_BORDER / 2, xPos + width - RADIUS_BORDER / 2, yPos + height, xPos + width - RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - RADIUS_BORDER / 2, xPos, yPos + height - RADIUS_BORDER);
        path.closePath();
      default:
        path.moveTo(xPos, yPos + RADIUS_BORDER);
        path.curveTo(xPos, yPos + RADIUS_BORDER / 2, xPos + RADIUS_BORDER / 2, yPos, xPos + RADIUS_BORDER, yPos);
        path.lineTo(xPos + width - RADIUS_BORDER, yPos);
        path.curveTo(xPos + width - RADIUS_BORDER / 2, yPos, xPos + width, yPos + RADIUS_BORDER / 2, xPos + width, yPos + RADIUS_BORDER);
        path.lineTo(xPos + width, yPos + height - RADIUS_BORDER);
        path.curveTo(xPos + width, yPos + height - RADIUS_BORDER / 2, xPos + width - RADIUS_BORDER / 2, yPos + height, xPos + width - RADIUS_BORDER, yPos + height);
        path.lineTo(xPos + RADIUS_BORDER, yPos + height);
        path.curveTo(xPos + RADIUS_BORDER / 2, yPos + height, xPos, yPos + height - RADIUS_BORDER / 2, xPos, yPos + height - RADIUS_BORDER);
        path.closePath();
    }
    return path;
  }

}
