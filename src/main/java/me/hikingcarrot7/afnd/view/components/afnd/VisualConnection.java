package me.hikingcarrot7.afnd.view.components.afnd;

import lombok.Getter;
import lombok.Setter;
import me.hikingcarrot7.afnd.core.graphs.GraphConnection;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.Drawable;

import java.awt.*;

@Getter
@Setter
public abstract class VisualConnection extends GraphConnection<String> implements Drawable {
  public static final int TRIANGLE_LENGTH = 7;
  public static final int STROKE_WIDTH = 2;
  public static final int BLOB_PADDING = 3;
  public static final int ALTURA_CURVATURA = 40;
  public static final Color COLOR_ARCH = new Color(155, 92, 181);
  public static final Color COLOR_SELECTED_ARCH = new Color(25, 229, 39);
  public static final Color RED_CONNECTION_COLOR = new Color(255, 80, 80);

  public static final ColorPalette DEFAULT_CONNECTION_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_ARCH)
      .build();

  public static final ColorPalette SELECTED_CONNECTION_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, COLOR_SELECTED_ARCH)
      .build();

  public static final ColorPalette RED_CONNECTION_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, RED_CONNECTION_COLOR)
      .build();

  protected ConditionNode conditionNode;
  protected Triangle triangle;
  protected ColorPalette colorPalette;
  protected boolean previewMode;
  private int layer;

  public VisualConnection(VisualNode origin, VisualNode destination, String condition, boolean previewMode, ColorPalette colorPalette) {
    this(origin, destination, condition, previewMode);
    this.colorPalette = colorPalette;
  }

  public VisualConnection(VisualNode origin, VisualNode destination, String condition) {
    this(origin, destination, condition, false);
  }

  public VisualConnection(VisualNode origin, VisualNode destination, boolean previewMode) {
    this(origin, destination, "", previewMode);
  }

  public VisualConnection(VisualNode origin, VisualNode destination, String condition, boolean previewMode) {
    super(origin, destination, condition);
    this.previewMode = previewMode;
    this.colorPalette = DEFAULT_CONNECTION_COLOR_PALETTE;
    this.conditionNode = new ConditionNode(condition);
    this.triangle = new Triangle();
    this.triangle.setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
    this.layer = AFNDPanel.MIN_LAYER;
  }

  public abstract void updateTrianglePos(Graphics2D g);

  public abstract void updateConditionNodePos(Graphics2D g);

  @Override
  public void setCondition(String condition) {
    conditionNode.setElement(condition);
    super.setCondition(condition);
  }

  public String condition() {
    return conditionNode.element();
  }

  public Point originPos() {
    return getOrigin().getPos();
  }

  public Point destinationPos() {
    return getDestination().getPos();
  }

  @Override
  public VisualNode getOrigin() {
    return (VisualNode) super.getOrigin();
  }

  @Override
  public VisualNode getDestination() {
    return (VisualNode) super.getDestination();
  }

  @Override
  public int getLayer() {
    return layer;
  }

}
