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
import me.hikingcarrot7.afnd.view.components.afnd.conexiones.NormalConnection;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AddingConnectionState implements AFNDState {
  private static AddingConnectionState instance;

  public synchronized static AddingConnectionState getInstance() {
    if (instance == null) {
      instance = new AddingConnectionState();
    }
    return instance;
  }

  private AddingConnectionState() {
  }

  private boolean insertingCondition;
  private VisualNode origin;
  private VisualNode destination;
  private Movable cursor;
  private VisualConnection previewArch;
  private VisualConnection previousArch;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event instanceof MouseEvent) {
      MouseEvent mouseEvent = (MouseEvent) event;
      if (event.getID() == MouseEvent.MOUSE_CLICKED) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
          if (origenSeleccionado()) {
            selectDestination(afndGraph, visualAFND, mouseEvent);
          } else {
            selectOrigin(afndGraph, visualAFND, mouseEvent);
          }
        } else {
          clearState(afndGraph, visualAFND, afndStateDispatcher);
        }
        visualAFND.repaint();
      }
      if (event.getID() == MouseEvent.MOUSE_MOVED) {
        updateArchPreview(visualAFND, mouseEvent);
        visualAFND.repaint();
      }
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
  }

  private void selectOrigin(AFNDGraph<String> afndGraph, VisualAFND visualAFND, MouseEvent e) {
    if (origin == null) {
      int pressedNode = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
      if (pressedNode >= 0 && afndGraph.cardinality() > 1) {
        origin = visualAFND.getVNode(pressedNode);
        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);
        visualAFND.getDefaultTextBox().setTitle("Da click derecho a otro estado para crear una conexión.");
      }
    }
  }

  private void selectDestination(AFNDGraph<String> afndGraph, VisualAFND visualAFND, MouseEvent e) {
    int pressedNode = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());

    if (pressedNode >= 0) {
      destination = visualAFND.getVNode(pressedNode);

      if (destination != origin) {
        if (afndGraph.existConnection(origin.element(), destination.element())) {
          previousArch = visualAFND.getVArch(origin, destination);
          afndGraph.removeConnection(origin.element(), destination.element());
          visualAFND.removeVArch(previousArch);
        }

        destination = visualAFND.getVNode(pressedNode);
        previewArch.setDestination(destination);
        previewArch.setPreviewMode(false);

        textTyper = new TextTyper(previewArch.getConditionNode().getPos(), 1);
        dialogueBalloon = new DialogueBalloon(visualAFND, previewArch.getConditionNode(), "Inserte la condición");
        insertingCondition = true;

        visualAFND.addComponent(dialogueBalloon, VisualAFND.MIDDLE_LAYER);
        visualAFND.getDefaultTextBox().setTitle("Asígnale una condición a la conexión.");
      }
    }
  }

  private void updateArchPreview(VisualAFND visualAFND, MouseEvent e) {
    if (origin != null) {
      if (cursor == null) {
        cursor = new VisualNode("CURSOR_PREVIEW", e.getPoint());
        previewArch = new NormalConnection(origin, cursor, true);
        visualAFND.addVArch(previewArch, VisualAFND.MIN_LAYER);
      } else {
        cursor.setXCenter(e.getX());
        cursor.setYCenter(e.getY());
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

    afndGraph.insertConnection(origin.element(), destination.element(), text);
    visualAFND.addVArch(new NormalConnection(origin, destination, text), VisualAFND.MIN_LAYER);
    previousArch = null;
    return true;
  }

  private boolean origenSeleccionado() {
    return origin != null;
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    if (previousArch != null) {
      afndGraph.insertConnection(
          ((VisualNode) previousArch.getOrigin()).element(),
          ((VisualNode) previousArch.getDestination()).element(),
          previousArch.getConditionNode().element());

      visualAFND.addVArch(previousArch, VisualAFND.MIN_LAYER);
    }

    visualAFND.removeVNode((VisualNode) cursor);

    if (previewArch != null) {
      visualAFND.removeVArch(previewArch);
    }

    if (origin != null) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }

    visualAFND.removeComponent(dialogueBalloon);

    cursor = null;
    origin = null;
    destination = null;
    previewArch = null;
    dialogueBalloon = null;
    insertingCondition = false;
    AFNDState.super.clearState(afndGraph, visualAFND, afndStateDispatcher);
  }

}
