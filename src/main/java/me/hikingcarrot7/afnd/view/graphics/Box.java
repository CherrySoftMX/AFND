package me.hikingcarrot7.afnd.view.graphics;

import java.awt.Graphics2D;

public interface Box {

  enum BoxPosition {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    TOP_RIGHT,
    TOP_LEFT,
    BOTTOM_RIGHT,
    BOTTOM_LEFT
  }

  void drawBox(Graphics2D g, BoxPosition boxPosition);

  int getWidth();

  int getHeight();

}
