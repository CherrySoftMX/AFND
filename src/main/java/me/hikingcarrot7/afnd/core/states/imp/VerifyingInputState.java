package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.afnd.MatchResult;
import me.hikingcarrot7.afnd.core.afnd.MatchResultStep;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.*;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAutomata;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.Box;

import java.awt.event.InputEvent;

public class VerifyingInputState implements AFNDState {
  private static VerifyingInputState instance;

  public synchronized static VerifyingInputState getInstance() {
    if (instance == null) {
      instance = new VerifyingInputState();
    }
    return instance;
  }

  private final TextBox messageBox;
  private boolean inputTested;
  private MatchResult result;

  private VerifyingInputState() {
    messageBox = new TextBox.TextBoxBuilder()
        .setBoxPosition(Box.BoxPosition.TOP_LEFT)
        .build();
  }

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (!inputTested) {
      testInput(panel);
    }
    panel.repaint();
  }

  private void testInput(AFNDPanel panel) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    String text = Menu.TEXT_FIELD.getText();
    try {
      result = visualAutomata.matches(text);
      panel.addComponent(messageBox);
      if (result.matches()) {
        messageBox.setTitle("La palabra FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        markPath();
      } else {
        messageBox.setTitle("La palabra NO FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
      }
    } catch (IllegalStateException e) {
      panel.textBox().setTitle(e.getMessage());
      panel.repaint();
    }
    inputTested = true;
  }

  private void markPath() {
    for (MatchResultStep step : result.getPath()) {
      VisualConnection connection = (VisualConnection) step.getConnection();
      connection.getOrigin().setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      connection.getDestination().setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      connection.getTriangle().setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      connection.setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
    }
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    visualAutomata.forEachVisualNode(node -> node.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE));
    visualAutomata.forEachVisualConnection(conn -> {
      conn.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      conn.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      conn.setLayer(AFNDPanel.MIN_LAYER);
    });
    inputTested = false;
    panel.removeComponent(messageBox);
    AFNDState.super.clearState(afndGraph, panel, afndStateDispatcher);
  }

}
