package me.hikingcarrot7.afnd.view.graphics;

import java.awt.Shape;

public interface ShapeFactory {
  public static final String DIALOGUE_BALLOON_SHAPE = "DIALOGUE_BALLOON";
  public static final String TEXTBOX_SHAPE = "TEXTBOX";

  Shape createShape(int xPos, int yPos, int width, int height, Box.BoxPosition boxPosition);
}
