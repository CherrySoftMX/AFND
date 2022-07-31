package me.hikingcarrot7.afnd.view.components.afnd.connections;

import me.hikingcarrot7.afnd.core.utils.MathHelper;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class NormalConnection extends VisualConnection {

  public NormalConnection(VisualNode origin, VisualNode destination, String condition) {
    super(origin, destination, condition);
  }

  public NormalConnection(VisualNode origin, VisualNode destination, boolean previewMode) {
    super(origin, destination, previewMode);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.setStroke(new BasicStroke(STROKE_WIDTH));

    if (previewMode) {
      g.drawLine(originPos().x, originPos().y, destinationPos().x, destinationPos().y);
    } else {
      GeneralPath path = new GeneralPath();
      path.moveTo(originPos().x, originPos().y);

      Point minPoint = MathHelper.midPoint(originPos(), destinationPos());
      Point controlPoint = MathHelper.controlPoint(originPos(), destinationPos(), ALTURA_CURVATURA);

      path.curveTo(
          minPoint.x + controlPoint.x,
          minPoint.y + controlPoint.y,
          minPoint.x + controlPoint.x,
          minPoint.y + controlPoint.y,
          destinationPos().x,
          destinationPos().y
      );

      g.draw(path);

      updateConditionNodePos(g);
      updateTrianglePos(g);
    }

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public void updateTrianglePos(Graphics2D g) {
    Point destinationPos = destinationPos();
    Point midPoint = MathHelper.midPoint(originPos(), destinationPos);
    Point controlPoint = MathHelper.controlPoint(originPos(), destinationPos, ALTURA_CURVATURA);

    triangle.setOrigenX(midPoint.x + controlPoint.x);
    triangle.setOrigenY(midPoint.y + controlPoint.y);
    triangle.setDestinoX(destinationPos.x);
    triangle.setDestinoY(destinationPos.y);
    triangle.setLength(TRIANGLE_LENGTH);
    triangle.setOffset(VisualNode.NODE_RADIUS + STROKE_WIDTH);
  }

  @Override
  public void updateConditionNodePos(Graphics2D g) {
    Point midPoint = MathHelper.midPoint(originPos(), destinationPos());
    Point controlPoint = MathHelper.controlPoint(originPos(), destinationPos(), ALTURA_CURVATURA);
    g.setColor(Menu.GRAY_TEXT_COLOR);
    int textWidth = GraphicsUtils.getStringWidth(g, conditionNode.element());
    int blobRadio = textWidth / 2 + BLOB_PADDING * 2;

    conditionNode.radius(blobRadio);
    conditionNode.setXCenter(midPoint.x + controlPoint.x);
    conditionNode.setYCenter(midPoint.y + controlPoint.y);
  }

}
