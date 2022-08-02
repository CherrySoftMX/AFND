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
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
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
        messageBox.setTitle("La palabra FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        paintPath(AFNDPanel);
      } else {
        messageBox.setTitle("La palabra NO FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
      }
    } catch (IllegalStateException e) {
      AFNDPanel.getDefaultTextBox().setTitle(e.getMessage());
      AFNDPanel.repaint();
    }
    inputTested = true;
  }

  private void paintPath(AFNDPanel AFNDPanel) {
    for (MatchResultStep step : result.getPath()) {
      Connection<?> connection = step.getConnection();
      VisualNode origin = AFNDPanel.getVNode(connection.getOrigin().element().toString());
      VisualNode destination = AFNDPanel.getVNode(connection.getDestination().element().toString());
      VisualConnection varch = AFNDPanel.getVArch(origin, destination);
      origin.setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      destination.setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      varch.setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
    }
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, AFNDStateDispatcher afndStateDispatcher) {
    AFNDPanel.getVNodes().forEach(vnode -> vnode.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE));
    AFNDPanel.getVisualConnections().forEach(varch -> {
      varch.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      AFNDPanel.setVArchZIndex(varch, AFNDPanel.MIN_LAYER);
    });
    inputTested = false;
    AFNDPanel.removeComponent(messageBox);
    AFNDState.super.clearState(afndGraph, AFNDPanel, afndStateDispatcher);
  }

}
