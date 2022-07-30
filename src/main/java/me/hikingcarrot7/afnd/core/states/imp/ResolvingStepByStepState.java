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
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_CLICKED) {
      if (inputMatched) {
        paintNextStep(visualAFND);
      } else {
        testInput(afndGraph, visualAFND);
      }
    }
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
      visualAFND.getDefaultTextBox().setTitle(e.getMessage());
      inputTested = true;
    }
  }

  private void paintNextStep(VisualAFND visualAFND) {
    if (pathIterator == null) {
      pathIterator = result.pathIterator();
    }
    if (pathIterator.hasNext()) {
      MatchResultStep step = pathIterator.next();
      Connection<?> connection = step.getConnection();
      clearAllMarks(visualAFND);

      VisualNode origen = visualAFND.getVNode(connection.getOrigin().element().toString());
      VisualNode destino = visualAFND.getVNode(connection.getDestination().element().toString());
      VisualConnection varch = visualAFND.getVArch(origen, destino);

      origen.setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      destino.setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      varch.setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);

      messageBox.setTitle("Palabra por ser consumida: " + step.inputSnapshot());
    } else {
      clearAllMarks(visualAFND);
      messageBox.setTitle("La palabra ha sido consumida");
    }

    visualAFND.repaint();
  }

  private void clearAllMarks(VisualAFND visualAFND) {
    visualAFND.getVNodes().forEach(vnode -> vnode.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE));
    visualAFND.getVisualConnections().forEach(varch -> {
      varch.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      visualAFND.setVArchZIndex(varch, VisualAFND.MIN_LAYER);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    inputTested = false;
    inputMatched = false;
    pathIterator = null;
    visualAFND.removeComponent(messageBox);
    clearAllMarks(visualAFND);
    AFNDState.super.clearState(afndGraph, visualAFND, afndStateDispatcher);
  }

}
