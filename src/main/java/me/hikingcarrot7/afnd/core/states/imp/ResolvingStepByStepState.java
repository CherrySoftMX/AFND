package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.afnd.MatchResult;
import me.hikingcarrot7.afnd.core.afnd.MatchResultStep;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.TextBox;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAutomata;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.Box;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class ResolvingStepByStepState implements AFNDState {
  private static ResolvingStepByStepState instance;

  public synchronized static ResolvingStepByStepState getInstance() {
    if (instance == null) {
      instance = new ResolvingStepByStepState();
    }
    return instance;
  }

  private boolean inputTested = false;
  private boolean inputMatched = false;
  private final TextBox messageBox;
  private Iterator<MatchResultStep> pathIterator;
  private MatchResult result;

  private ResolvingStepByStepState() {
    messageBox = new TextBox.TextBoxBuilder()
        .setBoxPosition(Box.BoxPosition.TOP_LEFT)
        .build();
  }

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_CLICKED) {
      if (inputMatched) {
        markNextStep(panel);
      } else {
        testInput(panel);
      }
    }
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
        messageBox.setTitle("Palabra ACEPTADA, presiona sobre el aútomata para iniciar el recorrido");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        inputMatched = true;
      } else {
        messageBox.setTitle("Palabra NO ACEPTADA, no podemos iniciar el recorrido");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
        inputMatched = false;
      }
      inputTested = true;
    } catch (IllegalStateException e) {
      panel.textBox().setTitle(e.getMessage());
      inputTested = true;
    }
  }

  private void markNextStep(AFNDPanel panel) {
    if (pathIterator == null) {
      pathIterator = result.pathIterator();
    }
    if (pathIterator.hasNext()) {
      clearAllMarks(panel);
      MatchResultStep step = pathIterator.next();
      VisualConnection connection = (VisualConnection) step.getConnection();
      connection.getOrigin().setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      connection.getDestination().setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      connection.getTriangle().setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      connection.setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      messageBox.setTitle("Palabra por ser consumida: " + step.inputSnapshot());
    } else {
      clearAllMarks(panel);
      messageBox.setTitle("La palabra ha sido consumida");
    }
    panel.repaint();
  }

  private void clearAllMarks(AFNDPanel panel) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    visualAutomata.forEachVisualNode(node -> node.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE));
    visualAutomata.forEachVisualConnection(conn -> {
      conn.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      conn.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      conn.setLayer(AFNDPanel.MIN_LAYER);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher) {
    inputTested = false;
    inputMatched = false;
    pathIterator = null;
    panel.removeComponent(messageBox);
    clearAllMarks(panel);
    AFNDState.super.clearState(afndGraph, panel, afndStateDispatcher);
  }

}
