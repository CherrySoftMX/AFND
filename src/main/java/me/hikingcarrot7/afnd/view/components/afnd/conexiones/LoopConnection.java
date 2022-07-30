package me.hikingcarrot7.afnd.view.components.afnd.conexiones;

import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class LoopConnection extends VisualConnection {

  public LoopConnection(Movable origin, Movable destination, String condition) {
    super(origin, destination, condition);
  }

  public LoopConnection(Movable origin, Movable destination, boolean previewMode) {
    super(origin, destination, previewMode);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
    g.setStroke(new BasicStroke(STROKE_WIDTH));

    Point startRightPoint = calculateStartRightPoint();
    Point startLeftPoint = calculateStartLeftPoint();
    Point rightPointControl = calculateRightPointControl();
    Point leftPointControl = calculateLeftPointControl();

    GeneralPath path = new GeneralPath();
    path.moveTo(origin.xCenter() + startLeftPoint.x, origin.yCenter() + startLeftPoint.y);
    path.curveTo(
        origin.xCenter() + leftPointControl.x,
        origin.yCenter() + leftPointControl.y,
        origin.xCenter() + rightPointControl.x,
        origin.yCenter() + rightPointControl.y,
        origin.xCenter() + startRightPoint.x,
        origin.yCenter() + startRightPoint.y);

    g.draw(path);

    updateTrianglePos(g, rightPointControl, startRightPoint, ALTURA_CURVATURA);
    updateConditionNodePos(g, startLeftPoint, startRightPoint);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public void updateTrianglePos(Graphics2D g, Point origin, Point destination, int alturaCurvatura) {
    triangle.setOrigenX(this.origin.xCenter() + origin.x);
    triangle.setOrigenY(this.origin.yCenter() + origin.y);
    triangle.setDestinoX(this.destination.xCenter() + destination.x);
    triangle.setDestinoY(this.destination.yCenter() + destination.y);
    triangle.setLength(TRIANGLE_LENGTH);
    triangle.setOffset(VisualNode.STROKE_WIDTH);
  }

  @Override
  public void updateConditionNodePos(Graphics2D g, Point origin, Point destination) {
    Point rightPointControl = calculateRightPointControl();

    g.setColor(Menu.GRAY_TEXT_COLOR);
    int textWidth = GraphicsUtils.getStringWidth(g, conditionNode.element());
    int blobRadio = textWidth / 2 + BLOB_PADDING * 2;

    conditionNode.radius(blobRadio);
    conditionNode.setXCenter(this.origin.xCenter());
    conditionNode.setYCenter(this.origin.yCenter() + rightPointControl.y);
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
    return new Point(startRightPoint.x, startRightPoint.y - ALTURA_CURVATURA - 15);
  }

  private Point calculateLeftPointControl() {
    Point startLeftPoint = calculateStartLeftPoint();
    return new Point(startLeftPoint.x, startLeftPoint.y - ALTURA_CURVATURA - 15);
  }

}
