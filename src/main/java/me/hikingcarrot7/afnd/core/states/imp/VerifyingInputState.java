package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.afnd.MatchResult;
import me.hikingcarrot7.afnd.core.afnd.MatchResultStep;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.*;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAFND;
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
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (!inputTested) {
      testInput(afndGraph, visualAFND);
    }
    visualAFND.repaint();
  }

  private void testInput(AFNDGraph<String> afndGraph, VisualAFND visualAFND) {
    String text = Menu.TEXT_FIELD.getText();
    try {
      result = afndGraph.matches(text);
      visualAFND.addComponent(messageBox, VisualAFND.MAX_LAYER);
      if (result.matches()) {
        messageBox.setTitle("La palabra FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        paintPath(visualAFND);
      } else {
        messageBox.setTitle("La palabra NO FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
      }
    } catch (IllegalStateException e) {
      visualAFND.getDefaultTextBox().setTitle(e.getMessage());
      visualAFND.repaint();
    }
    inputTested = true;
  }

  private void paintPath(VisualAFND visualAFND) {
    for (MatchResultStep step : result.getPath()) {
      Connection<?> connection = step.getConnection();
      VisualNode origin = visualAFND.getVNode(connection.getOrigin().element().toString());
      VisualNode destination = visualAFND.getVNode(connection.getDestination().element().toString());
      VisualConnection varch = visualAFND.getVArch(origin, destination);
      origin.setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      destination.setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      varch.setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
    }
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    visualAFND.getVNodes().forEach(vnode -> vnode.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE));
    visualAFND.getVisualConnections().forEach(varch -> {
      varch.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      visualAFND.setVArchZIndex(varch, VisualAFND.MIN_LAYER);
    });
    inputTested = false;
    visualAFND.removeComponent(messageBox);
    AFNDState.super.clearState(afndGraph, visualAFND, afndStateDispatcher);
  }

}
