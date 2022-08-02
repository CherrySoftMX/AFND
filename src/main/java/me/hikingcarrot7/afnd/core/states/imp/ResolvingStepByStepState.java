package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.afnd.MatchResult;
import me.hikingcarrot7.afnd.core.afnd.MatchResultStep;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.*;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
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
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_CLICKED) {
      if (inputMatched) {
        paintNextStep(AFNDPanel);
      } else {
        testInput(afndGraph, AFNDPanel);
      }
    }
    if (!inputTested) {
      testInput(afndGraph, AFNDPanel);
    }
    AFNDPanel.repaint();
  }

  private void testInput(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel) {
    String text = Menu.TEXT_FIELD.getText();
    try {
      result = afndGraph.matches(text);
      AFNDPanel.addComponent(messageBox);
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
      AFNDPanel.getDefaultTextBox().setTitle(e.getMessage());
      inputTested = true;
    }
  }

  private void paintNextStep(AFNDPanel panel) {
    if (pathIterator == null) {
      pathIterator = result.pathIterator();
    }
    if (pathIterator.hasNext()) {
      MatchResultStep step = pathIterator.next();
      Connection<?> connection = step.getConnection();
      clearAllMarks(panel);

      VisualNode origen = panel.getVNode(connection.getOrigin().element().toString());
      VisualNode destino = panel.getVNode(connection.getDestination().element().toString());
      VisualConnection varch = panel.getVArch(origen, destino);

      origen.setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      destino.setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      varch.setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);

      messageBox.setTitle("Palabra por ser consumida: " + step.inputSnapshot());
    } else {
      clearAllMarks(panel);
      messageBox.setTitle("La palabra ha sido consumida");
    }

    panel.repaint();
  }

  private void clearAllMarks(AFNDPanel panel) {
    panel.getVNodes().forEach(vnode -> vnode.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE));
    panel.getVisualConnections().forEach(varch -> {
      varch.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      panel.setVArchZIndex(varch, AFNDPanel.MIN_LAYER);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, AFNDStateDispatcher afndStateDispatcher) {
    inputTested = false;
    inputMatched = false;
    pathIterator = null;
    AFNDPanel.removeComponent(messageBox);
    clearAllMarks(AFNDPanel);
    AFNDState.super.clearState(afndGraph, AFNDPanel, afndStateDispatcher);
  }

}
