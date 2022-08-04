package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.components.afnd.AutomataPanel;
import me.hikingcarrot7.afnd.view.components.afnd.states.FinalState;
import me.hikingcarrot7.afnd.view.components.afnd.states.InitialFinalState;
import me.hikingcarrot7.afnd.view.components.afnd.states.InitialState;
import me.hikingcarrot7.afnd.view.components.afnd.states.NormalState;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;

import javax.swing.*;
import java.awt.*;

public class Menu implements Drawable {
  public static final Color GRAY_TEXT_COLOR = new Color(159, 162, 166);
  public static final int MENU_WIDTH = 290;

  public static final int MOVE_STATE_ID = 5;
  public static final int DELETE_STATE_ID = 6;
  public static final int NORMAL_CONNECTION_ID = 7;
  public static final int LOOP_CONNECTION_ID = 8;
  public static final int DELETE_CONNECTION_ID = 9;
  public static final int VERIFY_AUTOMATA_ID = 10;
  public static final int STEP_VERIFICATION_ID = 11;

  public static AbstractButton ADD_INITIAL_STATE_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Initial state")
      .setID(InitialState.INITIAL_STATE_ID)
      .build();

  public static AbstractButton ADD_NORMAL_STATE_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Normal state")
      .setID(NormalState.NORMAL_STATE_ID)
      .build();

  public static AbstractButton ADD_FINAL_STATE_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Final state")
      .setID(FinalState.FINAL_STATE_ID)
      .build();

  public static AbstractButton ADD_INITIAL_FINAL_STATE_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Initial-final")
      .addLine("state")
      .setID(InitialFinalState.INITIAL_FINAL_STATE_ID)
      .build();

  public static AbstractButton MOVE_STATE_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Move state")
      .setID(MOVE_STATE_ID)
      .build();

  public static AbstractButton DELETE_STATE_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Delete state")
      .setID(DELETE_STATE_ID)
      .build();

  public static AbstractButton ADD_NORMAL_CONNECTION_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Normal")
      .addLine("connection")
      .setID(NORMAL_CONNECTION_ID)
      .build();

  public static AbstractButton ADD_LOOP_CONNECTION_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Loop")
      .addLine("connection")
      .setID(LOOP_CONNECTION_ID)
      .build();

  public static AbstractButton DELETE_CONNECTION_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Delete")
      .addLine("connection")
      .setID(DELETE_CONNECTION_ID)
      .build();

  public static AbstractButton VERIFY_AUTOMATA_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("VERIFY")
      .setColorPalette(Button.BUTTON_COLOR_PALETTE)
      .setDimension(50, 40)
      .setFontSize(8)
      .setID(VERIFY_AUTOMATA_ID)
      .build();

  public static AbstractButton STEP_VERIFICATION_BUTTON = new AbstractButton.ButtonBuilder()
      .addLine("Step by step")
      .setID(STEP_VERIFICATION_ID)
      .build();

  public static JTextField TEXT_FIELD = new JTextField();
  private final AutomataPanel panel;

  public Menu(AutomataPanel panel) {
    this.panel = panel;
    initComponents();
  }

  private void initComponents() {
    panel.addSwingComponent(TEXT_FIELD);
  }

  @Override
  public void draw(Graphics2D g) {
    Stroke defaultStroke = g.getStroke();
    Color defaultColor = g.getColor();

    drawBackground(g);
    drawLabels(g);
    drawButtons(g);

    g.setStroke(defaultStroke);
    g.setColor(defaultColor);
  }

  @Override
  public int getLayer() {
    return panel.MAX_LAYER;
  }

  private void drawButtons(Graphics2D g) {
    ADD_INITIAL_STATE_BUTTON.setPosition(panel.getWidth() - 260, 80);
    ADD_NORMAL_STATE_BUTTON.setPosition(panel.getWidth() - 140, 80);
    ADD_FINAL_STATE_BUTTON.setPosition(panel.getWidth() - 260, 140);
    ADD_INITIAL_FINAL_STATE_BUTTON.setPosition(panel.getWidth() - 140, 140);
    MOVE_STATE_BUTTON.setPosition(panel.getWidth() - 260, 200);
    DELETE_STATE_BUTTON.setPosition(panel.getWidth() - 140, 200);
    ADD_NORMAL_CONNECTION_BUTTON.setPosition(panel.getWidth() - 260, 290);
    ADD_LOOP_CONNECTION_BUTTON.setPosition(panel.getWidth() - 140, 290);
    DELETE_CONNECTION_BUTTON.setPosition(panel.getWidth() - 200, 350);
    VERIFY_AUTOMATA_BUTTON.setPosition(panel.getWidth() - 80, 440);
    STEP_VERIFICATION_BUTTON.setPosition(panel.getWidth() - 200, 490);
    TEXT_FIELD.setBounds(panel.getWidth() - 200, 440, 110, 40);

    ADD_INITIAL_STATE_BUTTON.draw(g);
    ADD_NORMAL_STATE_BUTTON.draw(g);
    ADD_FINAL_STATE_BUTTON.draw(g);
    ADD_INITIAL_FINAL_STATE_BUTTON.draw(g);
    MOVE_STATE_BUTTON.draw(g);
    DELETE_STATE_BUTTON.draw(g);
    ADD_NORMAL_CONNECTION_BUTTON.draw(g);
    ADD_LOOP_CONNECTION_BUTTON.draw(g);
    DELETE_CONNECTION_BUTTON.draw(g);
    VERIFY_AUTOMATA_BUTTON.draw(g);
    STEP_VERIFICATION_BUTTON.draw(g);
  }

  private void drawLabels(Graphics2D g) {
    g.setColor(Color.WHITE);
    GraphicsUtils.drawStringOnPoint(g, "Menu", panel.getWidth() - 285 / 2, 20);

    g.setColor(GRAY_TEXT_COLOR);
    GraphicsUtils.drawStringOnPoint(g, "Add states", panel.getWidth() - 285 / 2, 55);
    GraphicsUtils.drawStringOnPoint(g, "Add connections", panel.getWidth() - 285 / 2, 267);
    GraphicsUtils.drawStringOnPoint(g, "Verify automata", panel.getWidth() - 285 / 2, 415);
  }

  private void drawBackground(Graphics2D g) {
    g.setColor(ToggleButton.DEFAULT_COLOR);
    g.fillRect(panel.getWidth() - MENU_WIDTH, 0, MENU_WIDTH, panel.getHeight());

    g.setColor(Color.WHITE);
    g.fillRect(panel.getWidth() - 285, 40, 285, panel.getHeight());
  }

  public boolean clicked(Point point) {
    return GraphicsUtils.intersects(
        new Rectangle(panel.getWidth() - MENU_WIDTH, 0, MENU_WIDTH, panel.getHeight()),
        new Rectangle(point)
    );
  }

}
