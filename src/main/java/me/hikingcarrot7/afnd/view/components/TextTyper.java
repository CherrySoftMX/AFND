package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.controller.TextValidator;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextTyper implements Drawable, Movable {
  public static final TextValidator DEFAULT_VALIDATOR = keyCode -> keyCode > 33;
  private final TextValidator textValidator;
  private final Point pos;
  private final int maxChars;
  private String text;

  public TextTyper(Point pos, int maxChars) {
    this.pos = pos;
    this.maxChars = maxChars;
    this.textValidator = DEFAULT_VALIDATOR;
    this.text = "";
  }

  @Override
  public void draw(Graphics2D g) {
    GraphicsUtils.drawStringOnPoint(g, text, pos.x, pos.y);
  }

  @Override
  public int getLayer() {
    return AFNDPanel.MIDDLE_LAYER;
  }

  public void handleInputEvent(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
      text = removeLastChar(text);
    } else if (textValidator.isValidCharacter(e.getKeyCode()) && text.length() < maxChars) {
      text += e.getKeyChar();
    }
  }

  private String removeLastChar(String text) {
    return text.length() <= 1 ? "" : text.substring(0, text.length() - 1);
  }

  @Override
  public int xCenter() {
    return pos.x;
  }

  @Override
  public void setXCenter(int xPos) {
    pos.x = xPos;
  }

  @Override
  public int yCenter() {
    return pos.y;
  }

  @Override
  public void setYCenter(int yPos) {
    pos.y = yPos;
  }

  public String getText() {
    return text;
  }

  @Override
  public Point getPos() {
    return pos;
  }

}
