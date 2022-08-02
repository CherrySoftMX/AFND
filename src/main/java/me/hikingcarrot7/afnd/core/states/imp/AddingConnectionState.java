package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.components.afnd.connections.NormalConnection;

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
  private VisualNode cursor;
  private VisualConnection previewArch;
  private VisualConnection previousArch;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event instanceof MouseEvent) {
      MouseEvent mouseEvent = (MouseEvent) event;
      if (event.getID() == MouseEvent.MOUSE_CLICKED) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
          if (originHasBeenSelected()) {
            selectDestination(afndGraph, panel, mouseEvent);
          } else {
            selectOrigin(afndGraph, panel, mouseEvent);
          }
        } else {
          clearState(afndGraph, panel, afndStateDispatcher);
        }
        panel.repaint();
      }
      if (event.getID() == MouseEvent.MOUSE_MOVED) {
        updateArchPreview(panel, mouseEvent);
        panel.repaint();
      }
    }

    if (event.getID() == KeyEvent.KEY_PRESSED) {
      KeyEvent keyEvent = (KeyEvent) event;
      if (insertingCondition && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
        if (addArch(afndGraph, panel)) {
          clearState(afndGraph, panel, afndStateDispatcher);
        } else {
          dialogueBalloon.setText("El valor es inválido");
          panel.repaint();
        }
      }
      if (insertingCondition) {
        insertCondition(panel, keyEvent);
      }
    }
  }

  private void selectOrigin(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel, MouseEvent e) {
    if (origin == null) {
      int pressedNode = GraphUtils.getPressedNode(afndGraph, AFNDPanel.getVNodes(), e.getPoint());
      if (pressedNode >= 0 && afndGraph.cardinality() > 1) {
        origin = AFNDPanel.getVNode(pressedNode);
        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);
        AFNDPanel.getDefaultTextBox().setTitle("Da click derecho a otro estado para crear una conexión.");
      }
    }
  }

  private void selectDestination(AFNDGraph<String> afndGraph, AFNDPanel panel, MouseEvent e) {
    int pressedNode = GraphUtils.getPressedNode(afndGraph, panel.getVNodes(), e.getPoint());

    if (pressedNode >= 0) {
      destination = panel.getVNode(pressedNode);

      if (destination != origin) {
        if (afndGraph.existConnection(origin.element(), destination.element())) {
          previousArch = panel.getVArch(origin, destination);
          afndGraph.removeConnection(origin.element(), destination.element());
          panel.removeVArch(previousArch);
        }

        destination = panel.getVNode(pressedNode);
        previewArch.setDestination(destination);
        previewArch.setPreviewMode(false);

        textTyper = new TextTyper(previewArch.getConditionNode().getPos(), 1);
        dialogueBalloon = new DialogueBalloon(panel, previewArch.getConditionNode(), "Inserte la condición");
        insertingCondition = true;

        panel.addComponent(dialogueBalloon);
        panel.getDefaultTextBox().setTitle("Asígnale una condición a la conexión.");
      }
    }
  }

  private void updateArchPreview(AFNDPanel panel, MouseEvent e) {
    if (origin != null) {
      if (cursor == null) {
        cursor = new VisualNode("CURSOR_PREVIEW", e.getPoint());
        previewArch = new NormalConnection(origin, cursor, true);
        panel.addVArch(previewArch, AFNDPanel.MIN_LAYER);
      } else {
        cursor.setXCenter(e.getX());
        cursor.setYCenter(e.getY());
      }
    }
  }

  private void insertCondition(AFNDPanel AFNDPanel, KeyEvent keyEvent) {
    textTyper.handleInputEvent(keyEvent);
    previewArch.setCondition(textTyper.getText());
    AFNDPanel.repaint();
  }

  private boolean addArch(AFNDGraph<String> afndGraph, AFNDPanel AFNDPanel) {
    if (textTyper.getText().isEmpty()) {
      return false;
    }

    String condition = textTyper.getText();

    afndGraph.insertConnection(origin.element(), destination.element(), condition);
    AFNDPanel.addVArch(new NormalConnection(origin, destination, condition), AFNDPanel.MIN_LAYER);
    previousArch = null;
    return true;
  }

  private boolean originHasBeenSelected() {
    return origin != null;
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher) {
    if (previousArch != null) {
      afndGraph.insertConnection(
          ((VisualNode) previousArch.getOrigin()).element(),
          ((VisualNode) previousArch.getDestination()).element(),
          previousArch.getConditionNode().element());

      panel.addVArch(previousArch, AFNDPanel.MIN_LAYER);
    }

    panel.removeVNode(cursor);

    if (previewArch != null) {
      panel.removeVArch(previewArch);
    }

    if (origin != null) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }

    panel.removeComponent(dialogueBalloon);

    cursor = null;
    origin = null;
    destination = null;
    previewArch = null;
    dialogueBalloon = null;
    insertingCondition = false;
    AFNDState.super.clearState(afndGraph, panel, afndStateDispatcher);
  }

}
