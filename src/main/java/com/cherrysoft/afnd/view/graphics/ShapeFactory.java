package com.cherrysoft.afnd.view.graphics;

import java.awt.*;

public interface ShapeFactory {
  Shape createShape(Point pos, Dimension dimension, Box.BoxPosition position);
}
