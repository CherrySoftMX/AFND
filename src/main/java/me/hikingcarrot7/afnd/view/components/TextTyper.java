package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.controller.TextFormatter;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TextTyper implements Drawable, Movable {
  private int xCenter;
  private int yCenter;
  private int maxChars;
  private String text;
  private final TextFormatter textFormatter;

  public static final TextFormatter DEFAULT_FORMATTER = keyCode -> keyCode > 33;
  public static final TextFormatter POSITIVE_INTEGER_FORMATTER = keyCode -> keyCode >= 48 && keyCode <= 57;

  public TextTyper(int xCenter, int yCenter, String text, int maxChars) {
    this(xCenter, yCenter, text, maxChars, DEFAULT_FORMATTER);
  }

  public TextTyper(Point pos, String text, int maxChars, TextFormatter textFormatter) {
    this(pos.x, pos.y, text, maxChars, textFormatter);
  }

  public TextTyper(int xCenter, int yCenter, String text, int maxChars, TextFormatter textFormatter) {
    this.xCenter = xCenter;
    this.yCenter = yCenter;
    this.text = text;
    this.maxChars = maxChars;
    this.textFormatter = textFormatter;
  }

  public TextTyper(Point coords, int maxChars, TextFormatter textFormatter) {
    this(coords.x, coords.y, "", maxChars, textFormatter);
  }

  public TextTyper(Point coords, int maxChars) {
    this(coords.x, coords.y, maxChars);
  }

  public TextTyper(int xCenter, int yCenter, int maxChars) {
    this(xCenter, yCenter, "", maxChars);
  }

  @Override
  public void draw(Graphics2D g) {
    GraphicsUtils.drawStringOnPoint(g, text, xCenter, yCenter);
  }

  @Override
  public int getLayer() {
    return AFNDPanel.MIDDLE_LAYER;
  }

  public void handleInputEvent(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
      text = quitarUltimoCharacter(text);
    } else if (textFormatter.isValidCharacter(e.getKeyCode()) && text.length() < maxChars) {
      text += e.getKeyChar();
    }
  }

  private String quitarUltimoCharacter(String cadena) {
    return cadena.length() <= 1 ? "" : cadena.substring(0, cadena.length() - 1);
  }

  @Override
  public int xCenter() {
    return xCenter;
  }

  @Override
  public void setXCenter(int xPos) {
    this.xCenter = xPos;
  }

  @Override
  public int yCenter() {
    return yCenter;
  }

  @Override
  public void setYCenter(int yPos) {
    this.yCenter = yPos;
  }

  public String getText() {
    return text;
  }

  public int getMaxChars() {
    return maxChars;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setMaxChars(int maxChars) {
    this.maxChars = maxChars;
  }

  @Override
  public Point getPos() {
    return new Point(xCenter, yCenter);
  }
}
