package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.afnd.MatchResult;
import me.hikingcarrot7.afnd.core.afnd.MatchResultStep;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateManager;
import me.hikingcarrot7.afnd.view.components.*;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
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
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (!inputTested) {
      testInput(afndGraph, vafnd);
    }
    vafnd.repaint();
  }

  private void testInput(AFNDGraph<String> afndGraph, VAFND vafnd) {
    String text = Menu.TEXT_FIELD.getText();
    try {
      result = afndGraph.matches(text);
      vafnd.addComponent(messageBox, VAFND.MAX_LAYER);
      if (result.matches()) {
        messageBox.setTitle("La palabra FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        paintPath(vafnd);
      } else {
        messageBox.setTitle("La palabra NO FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
      }
    } catch (IllegalStateException e) {
      vafnd.getDefaultTextBox().setTitle(e.getMessage());
      vafnd.repaint();
    }
    inputTested = true;
  }

  private void paintPath(VAFND vafnd) {
    for (MatchResultStep step : result.getPath()) {
      Connection<?> connection = step.getConnection();
      VNode origin = vafnd.getVNode(connection.getOrigin().getElement().toString());
      VNode destination = vafnd.getVNode(connection.getDestination().getElement().toString());
      VArch varch = vafnd.getVArch(origin, destination);
      origin.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
      destination.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
      varch.setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
    }
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    vafnd.getVNodes().forEach(vnode -> vnode.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE));
    vafnd.getVArchs().forEach(varch -> {
      varch.setColorPalette(VArch.DEFAULT_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      vafnd.setVArchZIndex(varch, VAFND.MIN_LAYER);
    });
    inputTested = false;
    vafnd.removeComponent(messageBox);
    AFNDState.super.clearState(afndGraph, vafnd, afndStateManager);
  }

}
