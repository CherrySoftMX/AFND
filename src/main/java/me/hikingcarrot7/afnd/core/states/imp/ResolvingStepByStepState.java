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
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_CLICKED) {
      if (inputMatched) {
        paintNextStep(vafnd);
      } else {
        testInput(afndGraph, vafnd);
      }
    }
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
      vafnd.getDefaultTextBox().setTitle(e.getMessage());
      inputTested = true;
    }
  }

  private void paintNextStep(VAFND vafnd) {
    if (pathIterator == null) {
      pathIterator = result.pathIterator();
    }
    if (pathIterator.hasNext()) {
      MatchResultStep step = pathIterator.next();
      Connection<?> connection = step.getConnection();
      clearAllMarks(vafnd);

      VNode origen = vafnd.getVNode(connection.getOrigin().getElement().toString());
      VNode destino = vafnd.getVNode(connection.getDestination().getElement().toString());
      VArch varch = vafnd.getVArch(origen, destino);

      origen.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
      destino.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
      varch.setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);

      messageBox.setTitle("Palabra por ser consumida: " + step.inputSnapshot());
    } else {
      clearAllMarks(vafnd);
      messageBox.setTitle("La palabra ha sido consumida");
    }

    vafnd.repaint();
  }

  private void clearAllMarks(VAFND vafnd) {
    vafnd.getVNodes().forEach(vnode -> vnode.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE));
    vafnd.getVArchs().forEach(varch -> {
      varch.setColorPalette(VArch.DEFAULT_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      vafnd.setVArchZIndex(varch, VAFND.MIN_LAYER);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    inputTested = false;
    inputMatched = false;
    pathIterator = null;
    vafnd.removeComponent(messageBox);
    clearAllMarks(vafnd);
    AFNDState.super.clearState(afndGraph, vafnd, afndStateManager);
  }

}
