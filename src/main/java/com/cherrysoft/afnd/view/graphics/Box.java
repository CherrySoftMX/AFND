package com.cherrysoft.afnd.view.graphics;

import java.awt.*;

public interface Box {

  void drawBox(Graphics2D g, BoxPosition boxPosition);

  int getWidth();

  int getHeight();

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

}
