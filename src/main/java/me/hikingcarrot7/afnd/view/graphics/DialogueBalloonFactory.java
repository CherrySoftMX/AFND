package me.hikingcarrot7.afnd.view.graphics;

import static me.hikingcarrot7.afnd.view.components.DialogueBalloon.RADIO_BORDER;
import static me.hikingcarrot7.afnd.view.components.DialogueBalloon.TRIANGLE_HEIGHT;
import static me.hikingcarrot7.afnd.view.components.DialogueBalloon.TRIANGLE_LENGTH;

import java.awt.Shape;
import java.awt.geom.GeneralPath;

public class DialogueBalloonFactory implements ShapeFactory {

  private static DialogueBalloonFactory instance;

  public static synchronized DialogueBalloonFactory getInstance() {
    if (instance == null) {
      instance = new DialogueBalloonFactory();
    }
    return instance;
  }

  private DialogueBalloonFactory() {
  }

  @Override
  public Shape createShape(int xPos, int yPos, int width, int height, Box.BoxPosition boxPosition) {
    GeneralPath path = new GeneralPath();
    switch (boxPosition) {
      case TOP:
        path.moveTo(xPos, yPos + RADIO_BORDER);
        path.curveTo(xPos, yPos + RADIO_BORDER / 2, xPos + RADIO_BORDER / 2, yPos, xPos + RADIO_BORDER, yPos);
        path.lineTo(xPos + width - RADIO_BORDER, yPos);
        path.curveTo(xPos + width - RADIO_BORDER / 2, yPos, xPos + width, yPos + RADIO_BORDER / 2, xPos + width, yPos + RADIO_BORDER);
        path.lineTo(xPos + width, yPos + height - RADIO_BORDER);
        path.curveTo(xPos + width, yPos + height - RADIO_BORDER / 2, xPos + width - RADIO_BORDER / 2, yPos + height, xPos + width - RADIO_BORDER, yPos + height);

        path.lineTo(xPos + width / 2 + TRIANGLE_LENGTH / 2, yPos + height);
        path.lineTo(xPos + width / 2, yPos + height + TRIANGLE_HEIGHT);
        path.lineTo(xPos + width / 2 - TRIANGLE_LENGTH / 2, yPos + height);
        path.lineTo(xPos + RADIO_BORDER, yPos + height);

        path.curveTo(xPos + RADIO_BORDER / 2, yPos + height, xPos, yPos + height - RADIO_BORDER / 2, xPos, yPos + height - RADIO_BORDER);
        path.closePath();
        break;
      case BOTTOM:
        path.moveTo(xPos, yPos + RADIO_BORDER);
        path.curveTo(xPos, yPos + RADIO_BORDER / 2, xPos + RADIO_BORDER / 2, yPos, xPos + RADIO_BORDER, yPos);

        path.lineTo(xPos + width / 2 - TRIANGLE_LENGTH / 2, yPos);
        path.lineTo(xPos + width / 2, yPos - TRIANGLE_HEIGHT);
        path.lineTo(xPos + width / 2 + TRIANGLE_LENGTH / 2, yPos);
        path.lineTo(xPos + width - RADIO_BORDER, yPos);

        path.curveTo(xPos + width - RADIO_BORDER / 2, yPos, xPos + width, yPos + RADIO_BORDER / 2, xPos + width, yPos + RADIO_BORDER);
        path.lineTo(xPos + width, yPos + height - RADIO_BORDER);
        path.curveTo(xPos + width, yPos + height - RADIO_BORDER / 2, xPos + width - RADIO_BORDER / 2, yPos + height, xPos + width - RADIO_BORDER, yPos + height);
        path.lineTo(xPos + RADIO_BORDER, yPos + height);
        path.curveTo(xPos + RADIO_BORDER / 2, yPos + height, xPos, yPos + height - RADIO_BORDER / 2, xPos, yPos + height - RADIO_BORDER);
        path.closePath();
        break;
      case RIGHT:
        path.moveTo(xPos, yPos + RADIO_BORDER);
        path.curveTo(xPos, yPos + RADIO_BORDER / 2, xPos + RADIO_BORDER / 2, yPos, xPos + RADIO_BORDER, yPos);
        path.lineTo(xPos + width - RADIO_BORDER, yPos);
        path.curveTo(xPos + width - RADIO_BORDER / 2, yPos, xPos + width, yPos + RADIO_BORDER / 2, xPos + width, yPos + RADIO_BORDER);
        path.lineTo(xPos + width, yPos + height - RADIO_BORDER);
        path.curveTo(xPos + width, yPos + height - RADIO_BORDER / 2, xPos + width - RADIO_BORDER / 2, yPos + height, xPos + width - RADIO_BORDER, yPos + height);
        path.lineTo(xPos + RADIO_BORDER, yPos + height);
        path.curveTo(xPos + RADIO_BORDER / 2, yPos + height, xPos, yPos + height - RADIO_BORDER / 2, xPos, yPos + height - RADIO_BORDER);

        path.lineTo(xPos, yPos + height / 2 + TRIANGLE_LENGTH / 2);
        path.lineTo(xPos - TRIANGLE_HEIGHT, yPos + height / 2);
        path.lineTo(xPos, yPos + height / 2 - TRIANGLE_LENGTH / 2);

        path.closePath();
        break;
      case LEFT:
        path.moveTo(xPos, yPos + RADIO_BORDER);
        path.curveTo(xPos, yPos + RADIO_BORDER / 2, xPos + RADIO_BORDER / 2, yPos, xPos + RADIO_BORDER, yPos);
        path.lineTo(xPos + width - RADIO_BORDER, yPos);
        path.curveTo(xPos + width - RADIO_BORDER / 2, yPos, xPos + width, yPos + RADIO_BORDER / 2, xPos + width, yPos + RADIO_BORDER);

        path.lineTo(xPos + width, yPos + height / 2 - TRIANGLE_LENGTH / 2);
        path.lineTo(xPos + width + TRIANGLE_HEIGHT, yPos + height / 2);
        path.lineTo(xPos + width, yPos + height / 2 + TRIANGLE_LENGTH / 2);
        path.lineTo(xPos + width, yPos + height - RADIO_BORDER);

        path.curveTo(xPos + width, yPos + height - RADIO_BORDER / 2, xPos + width - RADIO_BORDER / 2, yPos + height, xPos + width - RADIO_BORDER, yPos + height);
        path.lineTo(xPos + RADIO_BORDER, yPos + height);
        path.curveTo(xPos + RADIO_BORDER / 2, yPos + height, xPos, yPos + height - RADIO_BORDER / 2, xPos, yPos + height - RADIO_BORDER);
        path.closePath();
      default:
        path.moveTo(xPos, yPos + RADIO_BORDER);
        path.curveTo(xPos, yPos + RADIO_BORDER / 2, xPos + RADIO_BORDER / 2, yPos, xPos + RADIO_BORDER, yPos);
        path.lineTo(xPos + width - RADIO_BORDER, yPos);
        path.curveTo(xPos + width - RADIO_BORDER / 2, yPos, xPos + width, yPos + RADIO_BORDER / 2, xPos + width, yPos + RADIO_BORDER);
        path.lineTo(xPos + width, yPos + height - RADIO_BORDER);
        path.curveTo(xPos + width, yPos + height - RADIO_BORDER / 2, xPos + width - RADIO_BORDER / 2, yPos + height, xPos + width - RADIO_BORDER, yPos + height);
        path.lineTo(xPos + RADIO_BORDER, yPos + height);
        path.curveTo(xPos + RADIO_BORDER / 2, yPos + height, xPos, yPos + height - RADIO_BORDER / 2, xPos, yPos + height - RADIO_BORDER);
        path.closePath();
    }
    return path;
  }

}
