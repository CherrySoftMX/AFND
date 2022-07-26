package com.cherrysoft.afnd.view.components.afnd.connections;

import com.cherrysoft.afnd.view.components.Menu;
import com.cherrysoft.afnd.view.components.afnd.VisualConnection;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;
import com.cherrysoft.afnd.view.graphics.ColorPalette;
import com.cherrysoft.afnd.view.graphics.GraphicsUtils;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class LoopConnection extends VisualConnection {
  public static final int LOOP_CONNECTION_ID = 2;
  private Point startRightPoint;
  private Point rightPointControl;

  public LoopConnection(VisualNode origin, VisualNode destination, String condition) {
    super(origin, destination, condition);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.setStroke(new BasicStroke(STROKE_WIDTH));

    startRightPoint = calculateStartRightPoint();
    rightPointControl = calculateRightPointControl();
    Point startLeftPoint = calculateStartLeftPoint();
    Point leftPointControl = calculateLeftPointControl();

    GeneralPath path = new GeneralPath();
    path.moveTo(originPos().x + startLeftPoint.x, originPos().y + startLeftPoint.y);
    path.curveTo(
        originPos().x + leftPointControl.x,
        originPos().y + leftPointControl.y,
        originPos().x + rightPointControl.x,
        originPos().y + rightPointControl.y,
        originPos().x + startRightPoint.x,
        originPos().y + startRightPoint.y
    );

    g.draw(path);

    updateTrianglePos(g);
    updateConditionNodePos(g);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);

    conditionNode.draw(g);
    triangle.draw(g);
  }

  @Override
  public void updateTrianglePos(Graphics2D g) {
    triangle.setOriginX(originPos().x + rightPointControl.x);
    triangle.setOriginY(originPos().y + rightPointControl.y);
    triangle.setDestinationX(destinationPos().x + startRightPoint.x);
    triangle.setDestinationY(destinationPos().y + startRightPoint.y);
    triangle.setLength(TRIANGLE_LENGTH);
    triangle.setOffset(VisualNode.STROKE_WIDTH);
  }

  @Override
  public void updateConditionNodePos(Graphics2D g) {
    g.setColor(Menu.GRAY_TEXT_COLOR);
    int textWidth = GraphicsUtils.getStringWidth(g, conditionNode.element());
    int blobRadio = textWidth / 2 + BLOB_PADDING * 2;

    conditionNode.radius(blobRadio);
    conditionNode.setXCenter(originPos().x);
    conditionNode.setYCenter(originPos().y + rightPointControl.y);
  }

  private Point calculateStartRightPoint() {
    return new Point((int) (VisualNode.NODE_RADIUS * Math.cos(-Math.PI / 4)),
        (int) (VisualNode.NODE_RADIUS * Math.sin(-Math.PI / 4)));
  }

  private Point calculateStartLeftPoint() {
    return new Point((int) (VisualNode.NODE_RADIUS * Math.cos(Math.PI + Math.PI / 4)),
        (int) ((VisualNode.NODE_RADIUS) * Math.sin(Math.PI + Math.PI / 4)));
  }

  private Point calculateRightPointControl() {
    Point startRightPoint = calculateStartRightPoint();
    return new Point(startRightPoint.x, startRightPoint.y - BEND_HEIGHT - 15);
  }

  private Point calculateLeftPointControl() {
    Point startLeftPoint = calculateStartLeftPoint();
    return new Point(startLeftPoint.x, startLeftPoint.y - BEND_HEIGHT - 15);
  }

}
