package com.cherrysoft.afnd.view.graphics;

import java.awt.*;

public interface Drawable {

  void draw(Graphics2D g);

  int getLayer();

}
