package com.cherrysoft.afnd.view.components.afnd;

import com.cherrysoft.afnd.view.components.Menu;
import com.cherrysoft.afnd.view.graphics.ColorPalette;
import com.cherrysoft.afnd.view.graphics.GraphicsUtils;

import java.awt.*;

public class ConditionNode extends VisualNode {

  public static final ColorPalette DEFAULT_CONDITION_NODE_COLOR_PALETTE = new ColorPalette.ColorPaletteBuilder()
      .addColor(ColorPalette.ColorKey.FILL_COLOR_KEY, Menu.GRAY_TEXT_COLOR)
      .addColor(ColorPalette.ColorKey.TEXT_COLOR_KEY, Menu.GRAY_TEXT_COLOR)
      .build();

  public ConditionNode(String element) {
    super(element);
  }

  @Override
  public void draw(Graphics2D g) {
    Color defaultColor = g.getColor();

    g.setColor(colorPalette.getColor(ColorPalette.ColorKey.TEXT_COLOR_KEY));
    GraphicsUtils.drawStringOnPoint(g, element(), pos);

    g.setColor(defaultColor);
  }

}
