package me.hikingcarrot7.afnd.view.components;

import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.graphics.Drawable;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;

import javax.swing.*;
import java.awt.*;

public class Menu implements Drawable {
  public static final Color GRAY_TEXT_COLOR = new Color(159, 162, 166);
  public static final int MENU_WIDTH = 290;

  public static final int INITIAL_STATE_ID = 1;
  public static final int ESTADO_NORMAL_ID = 2;
  public static final int FINAL_STATE_ID = 3;
  public static final int INITIAL_FINAL_STATE_ID = 4;
  public static final int MOVER_ESTADO_ID = 5;
  public static final int ELIMINAR_ESTADO_ID = 6;
  public static final int CONEXION_NORMAL_ID = 7;
  public static final int CONEXION_BUCLE_ID = 8;
  public static final int ELIMINAR_CONEXION_ID = 9;
  public static final int COMPROBAR_AUTOMATA_ID = 10;
  public static final int COMPROBACION_PASOS_ID = 11;

  public static AbstractButton BUTTON_ADD_ESTADO_INICIAL = new AbstractButton.ButtonBuilder()
      .addLine("Estado inicial")
      .setID(INITIAL_STATE_ID)
      .build();

  public static AbstractButton BUTTON_ADD_ESTADO_NORMAL = new AbstractButton.ButtonBuilder()
      .addLine("Estado normal")
      .setID(ESTADO_NORMAL_ID)
      .build();

  public static AbstractButton BUTTON_ADD_ESTADO_FINAL = new AbstractButton.ButtonBuilder()
      .addLine("Estado final")
      .setID(FINAL_STATE_ID)
      .build();

  public static AbstractButton BUTTON_ADD_ESTADO_INICIAL_FINAL = new AbstractButton.ButtonBuilder()
      .addLine("Estado inicial-")
      .addLine("final")
      .setID(INITIAL_FINAL_STATE_ID)
      .build();

  public static AbstractButton BUTTON_MOVER_ESTADO = new AbstractButton.ButtonBuilder()
      .addLine("Mover estado")
      .setID(MOVER_ESTADO_ID)
      .build();

  public static AbstractButton BUTTON_ELIMINAR_ESTADO = new AbstractButton.ButtonBuilder()
      .addLine("Borrar estado")
      .setID(ELIMINAR_ESTADO_ID)
      .build();

  public static AbstractButton BUTTON_ADD_CONEXION_NORMAL = new AbstractButton.ButtonBuilder()
      .addLine("Conexión")
      .addLine("normal")
      .setID(CONEXION_NORMAL_ID)
      .build();

  public static AbstractButton BUTTON_ADD_CONEXION_BUCLE = new AbstractButton.ButtonBuilder()
      .addLine("Conexión")
      .addLine("bucle")
      .setID(CONEXION_BUCLE_ID)
      .build();

  public static AbstractButton BUTTON_ELIMINAR_CONEXION = new AbstractButton.ButtonBuilder()
      .addLine("Borrar")
      .addLine("conexión")
      .setID(ELIMINAR_CONEXION_ID)
      .build();

  public static AbstractButton BUTTON_COMPROBAR_AUTOMATA = new AbstractButton.ButtonBuilder()
      .addLine("COMPROBAR")
      .setColorPalette(Button.BUTTON_COLOR_PALETTE)
      .setDimension(50, 40)
      .setFontSize(8)
      .setID(COMPROBAR_AUTOMATA_ID)
      .build();

  public static AbstractButton BUTTON_COMPROBACION_PASOS_AUTOMATA = new AbstractButton.ButtonBuilder()
      .addLine("Paso a paso")
      .setID(COMPROBACION_PASOS_ID)
      .build();

  public static JTextField TEXT_FIELD = new JTextField();

  private final AFNDPanel AFNDPanel;

  public Menu(AFNDPanel AFNDPanel) {
    this.AFNDPanel = AFNDPanel;
    initComponents();
  }

  private void initComponents() {
    AFNDPanel.addSwingComponent(TEXT_FIELD);
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
    return AFNDPanel.MAX_LAYER;
  }

  private void drawButtons(Graphics2D g) {
    BUTTON_ADD_ESTADO_INICIAL.setPosition(AFNDPanel.getWidth() - 260, 80);
    BUTTON_ADD_ESTADO_NORMAL.setPosition(AFNDPanel.getWidth() - 140, 80);
    BUTTON_ADD_ESTADO_FINAL.setPosition(AFNDPanel.getWidth() - 260, 140);
    BUTTON_ADD_ESTADO_INICIAL_FINAL.setPosition(AFNDPanel.getWidth() - 140, 140);
    BUTTON_MOVER_ESTADO.setPosition(AFNDPanel.getWidth() - 260, 200);
    BUTTON_ELIMINAR_ESTADO.setPosition(AFNDPanel.getWidth() - 140, 200);
    BUTTON_ADD_CONEXION_NORMAL.setPosition(AFNDPanel.getWidth() - 260, 290);
    BUTTON_ADD_CONEXION_BUCLE.setPosition(AFNDPanel.getWidth() - 140, 290);
    BUTTON_ELIMINAR_CONEXION.setPosition(AFNDPanel.getWidth() - 200, 350);
    BUTTON_COMPROBAR_AUTOMATA.setPosition(AFNDPanel.getWidth() - 80, 440);
    BUTTON_COMPROBACION_PASOS_AUTOMATA.setPosition(AFNDPanel.getWidth() - 200, 490);
    TEXT_FIELD.setBounds(AFNDPanel.getWidth() - 200, 440, 110, 40);

    BUTTON_ADD_ESTADO_INICIAL.draw(g);
    BUTTON_ADD_ESTADO_NORMAL.draw(g);
    BUTTON_ADD_ESTADO_FINAL.draw(g);
    BUTTON_ADD_ESTADO_INICIAL_FINAL.draw(g);
    BUTTON_MOVER_ESTADO.draw(g);
    BUTTON_ELIMINAR_ESTADO.draw(g);
    BUTTON_ADD_CONEXION_NORMAL.draw(g);
    BUTTON_ADD_CONEXION_BUCLE.draw(g);
    BUTTON_ELIMINAR_CONEXION.draw(g);
    BUTTON_COMPROBAR_AUTOMATA.draw(g);
    BUTTON_COMPROBACION_PASOS_AUTOMATA.draw(g);
  }

  private void drawLabels(Graphics2D g) {
    g.setColor(Color.WHITE);
    GraphicsUtils.drawStringOnPoint(g, "Menú", AFNDPanel.getWidth() - 285 / 2, 20);

    g.setColor(GRAY_TEXT_COLOR);
    GraphicsUtils.drawStringOnPoint(g, "Agregar estados", AFNDPanel.getWidth() - 285 / 2, 55);
    GraphicsUtils.drawStringOnPoint(g, "Agregar conexiones", AFNDPanel.getWidth() - 285 / 2, 267);
    GraphicsUtils.drawStringOnPoint(g, "Ejecutar autómata", AFNDPanel.getWidth() - 285 / 2, 415);
  }

  private void drawBackground(Graphics2D g) {
    g.setColor(ToggleButton.DEFAULT_COLOR);
    g.fillRect(AFNDPanel.getWidth() - MENU_WIDTH, 0, MENU_WIDTH, AFNDPanel.getHeight());

    g.setColor(Color.WHITE);
    g.fillRect(AFNDPanel.getWidth() - 285, 40, 285, AFNDPanel.getHeight());
  }

  public boolean clicked(Point point) {
    return GraphicsUtils.intersects(
        new Rectangle(AFNDPanel.getWidth() - MENU_WIDTH, 0, MENU_WIDTH, AFNDPanel.getHeight()),
        new Rectangle(point)
    );
  }

}
