package me.hikingcarrot7.afnd.view.components.afnd.conexiones;

import me.hikingcarrot7.afnd.core.utils.MathHelper;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class NormalConnection extends VisualConnection {

  public NormalConnection(Movable origin, Movable destination, String condition) {
    super(origin, destination, condition);
  }

  public NormalConnection(Movable origin, Movable destination, boolean previewMode) {
    super(origin, destination, previewMode);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.setStroke(new BasicStroke(STROKE_WIDTH));

    if (previewMode) {
      g.drawLine(origin.xCenter(), origin.yCenter(), destination.xCenter(), destination.yCenter());
    } else {
      GeneralPath path = new GeneralPath();
      path.moveTo(origin.xCenter(), origin.yCenter());

      Point minPoint = MathHelper.midPoint(origin.getPos(), destination.getPos());
      Point controlPoint = MathHelper.controlPoint(origin.getPos(), destination.getPos(), ALTURA_CURVATURA);

      path.curveTo(
          minPoint.x + controlPoint.x,
          minPoint.y + controlPoint.y,
          minPoint.x + controlPoint.x,
          minPoint.y + controlPoint.y,
          destination.xCenter(),
          destination.yCenter());

      g.draw(path);

      updateConditionNodePos(g, origin.getPos(), destination.getPos());
      updateTrianglePos(g, origin.getPos(), destination.getPos(), ALTURA_CURVATURA);
    }

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public void updateTrianglePos(Graphics2D g, Point origin, Point destination, int alturaCurvatura) {
    Point midPoint = MathHelper.midPoint(origin, destination);
    Point controlPoint = MathHelper.controlPoint(origin, destination, alturaCurvatura);

    triangle.setOrigenX(midPoint.x + controlPoint.x);
    triangle.setOrigenY(midPoint.y + controlPoint.y);
    triangle.setDestinoX(destination.x);
    triangle.setDestinoY(destination.y);
    triangle.setLength(TRIANGLE_LENGTH);
    triangle.setOffset(VisualNode.NODE_RADIUS + STROKE_WIDTH);
  }

  @Override
  public void updateConditionNodePos(Graphics2D g, Point origin, Point destination) {
    Point midPoint = MathHelper.midPoint(origin, destination);
    Point controlPoint = MathHelper.controlPoint(origin, destination, ALTURA_CURVATURA);
    g.setColor(Menu.GRAY_TEXT_COLOR);
    int textWidth = GraphicsUtils.getStringWidth(g, conditionNode.element());
    int blobRadio = textWidth / 2 + BLOB_PADDING * 2;

    // blob.setElement(condition);
    conditionNode.radius(blobRadio);
    conditionNode.setXCenter(midPoint.x + controlPoint.x);
    conditionNode.setYCenter(midPoint.y + controlPoint.y);
  }

}
