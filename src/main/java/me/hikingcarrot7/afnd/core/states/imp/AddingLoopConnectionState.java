package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAFND;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.components.afnd.conexiones.LoopConnection;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AddingLoopConnectionState implements AFNDState {
  private static AddingLoopConnectionState instance;

  public synchronized static AddingLoopConnectionState getInstance() {
    if (instance == null) {
      instance = new AddingLoopConnectionState();
    }
    return instance;
  }

  private AddingLoopConnectionState() {
  }

  private boolean insertingCondition;
  private VisualNode origen;
  private VisualConnection previewArch;
  private VisualConnection previousArch;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      clearState(afndGraph, visualAFND, afndStateDispatcher);
      selectOrigen(afndGraph, visualAFND, (MouseEvent) event);
    }

    if (event.getID() == KeyEvent.KEY_PRESSED) {
      KeyEvent keyEvent = (KeyEvent) event;
      if (insertingCondition && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
        if (addArch(afndGraph, visualAFND)) {
          clearState(afndGraph, visualAFND, afndStateDispatcher);
        } else {
          dialogueBalloon.setText("El valor es inválido");
          visualAFND.repaint();
        }
      }

      if (insertingCondition) {
        insertarEstado(visualAFND, keyEvent);
      }
    }

    visualAFND.repaint();
  }

  private void selectOrigen(AFNDGraph<String> afndGraph, VisualAFND visualAFND, MouseEvent e) {
    if (origen == null) {
      int pressedNode = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
      if (pressedNode >= 0) {
        origen = visualAFND.getVNode(pressedNode);

        if (afndGraph.existConnection(origen.element(), origen.element())) {
          previousArch = visualAFND.getVArch(origen, origen);
          afndGraph.removeConnection(origen.element(), origen.element());
          visualAFND.removeVArch(previousArch);
        }

        origen.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);

        previewArch = new LoopConnection(origen, origen, true);

        textTyper = new TextTyper(previewArch.getConditionNode().getPos(), 1);
        dialogueBalloon = new DialogueBalloon(visualAFND, previewArch.getConditionNode(), "Inserte la condición");

        visualAFND.addVArch(previewArch, VisualAFND.MIN_LAYER);
        visualAFND.addComponent(dialogueBalloon, VisualAFND.MIDDLE_LAYER);
        visualAFND.getDefaultTextBox().setTitle("Inserte la condición para el estado");
        insertingCondition = true;
      }
    }
  }

  private void insertarEstado(VisualAFND visualAFND, KeyEvent keyEvent) {
    textTyper.handleInputEvent(keyEvent);
    previewArch.setCondition(textTyper.getText());
    visualAFND.repaint();
  }

  private boolean addArch(AFNDGraph<String> afndGraph, VisualAFND visualAFND) {
    if (textTyper.getText().isEmpty()) {
      return false;
    }

    String text = textTyper.getText();

    afndGraph.insertConnection(origen.element(), origen.element(), text);
    visualAFND.addVArch(new LoopConnection(origen, origen, text), VisualAFND.MIN_LAYER);
    previousArch = null;
    return true;
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    if (previousArch != null) {
      afndGraph.insertConnection(
          ((VisualNode) previousArch.getOrigin()).element(),
          ((VisualNode) previousArch.getDestination()).element(),
          previousArch.condition());

      visualAFND.addVArch(previousArch, VisualAFND.MIN_LAYER);
    }

    if (previewArch != null) {
      visualAFND.removeVArch(previewArch);
    }

    if (origen != null) {
      origen.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }

    visualAFND.removeComponent(dialogueBalloon);

    origen = null;
    previewArch = null;
    dialogueBalloon = null;
    insertingCondition = false;
    visualAFND.getDefaultTextBox().clearTextBox();
    visualAFND.repaint();
  }

}
