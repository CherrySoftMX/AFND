package com.cherrysoft.afnd.view.components.afnd.connections;

import com.cherrysoft.afnd.core.utils.MathHelper;
import com.cherrysoft.afnd.view.components.Menu;
import com.cherrysoft.afnd.view.components.afnd.VisualConnection;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;
import com.cherrysoft.afnd.view.graphics.ColorPalette;
import com.cherrysoft.afnd.view.graphics.GraphicsUtils;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class NormalConnection extends VisualConnection {
  public static final int NORMAL_CONNECTION_ID = 1;

  public NormalConnection(VisualNode origin, VisualNode destination, String condition) {
    super(origin, destination, condition);
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
      Point controlPoint = MathHelper.controlPoint(originPos(), destinationPos(), BEND_HEIGHT);

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

      triangle.draw(g);
      conditionNode.draw(g);
    }

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public void updateTrianglePos(Graphics2D g) {
    Point destinationPos = destinationPos();
    Point midPoint = MathHelper.midPoint(originPos(), destinationPos);
    Point controlPoint = MathHelper.controlPoint(originPos(), destinationPos, BEND_HEIGHT);

    triangle.setOriginX(midPoint.x + controlPoint.x);
    triangle.setOriginY(midPoint.y + controlPoint.y);
    triangle.setDestinationX(destinationPos.x);
    triangle.setDestinationY(destinationPos.y);
    triangle.setLength(TRIANGLE_LENGTH);
    triangle.setOffset(VisualNode.NODE_RADIUS + STROKE_WIDTH);
  }

  @Override
  public void updateConditionNodePos(Graphics2D g) {
    Point midPoint = MathHelper.midPoint(originPos(), destinationPos());
    Point controlPoint = MathHelper.controlPoint(originPos(), destinationPos(), BEND_HEIGHT);
    g.setColor(Menu.GRAY_TEXT_COLOR);
    int textWidth = GraphicsUtils.getStringWidth(g, conditionNode.element());
    int nodeRadius = textWidth / 2 + BLOB_PADDING * 2;

    conditionNode.radius(nodeRadius);
    conditionNode.setXCenter(midPoint.x + controlPoint.x);
    conditionNode.setYCenter(midPoint.y + controlPoint.y);
  }

}
