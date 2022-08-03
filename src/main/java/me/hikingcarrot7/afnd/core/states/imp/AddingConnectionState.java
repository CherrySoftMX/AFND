package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAutomata;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static java.util.Objects.isNull;

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

  private VisualNode origin;
  private VisualNode destination;
  private VisualConnection previousConnection;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;
  private boolean insertingCondition;
  private boolean previewConnectionInserted = false;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event instanceof MouseEvent) {
      MouseEvent mouseEvent = (MouseEvent) event;
      if (event.getID() == MouseEvent.MOUSE_CLICKED) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
          if (originHasBeenSelected()) {
            selectDestination(panel, mouseEvent);
          } else {
            selectOrigin(panel, mouseEvent);
          }
        } else {
          clearState(afndGraph, panel, afndStateDispatcher);
        }
        panel.repaint();
      }
      if (event.getID() == MouseEvent.MOUSE_MOVED) {
        updateConnectionPreview(panel, mouseEvent);
        panel.repaint();
      }
    }

    if (event.getID() == KeyEvent.KEY_PRESSED) {
      KeyEvent keyEvent = (KeyEvent) event;
      if (insertingCondition && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
        if (insertConnection(panel)) {
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

  private void selectOrigin(AFNDPanel panel, MouseEvent e) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    if (isNull(origin)) {
      VisualNode pressedNode = visualAutomata.getVisualNodeBellow(e.getPoint());
      if (!isNull(pressedNode) && visualAutomata.cardinality() > 1) {
        origin = pressedNode;
        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);
        panel.textBox().setTitle("Da click derecho a otro estado para crear una conexión.");
      }
    }
  }

  private void selectDestination(AFNDPanel panel, MouseEvent e) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    VisualNode pressedNode = visualAutomata.getVisualNodeBellow(e.getPoint());

    if (!isNull(pressedNode)) {
      destination = pressedNode;

      if (destination != origin) {
        if (visualAutomata.existConnection(origin.element(), destination.element())) {
          previousConnection = visualAutomata.getVisualConnection(origin.element(), destination.element());
          visualAutomata.removeConnection(origin.element(), destination.element());
        }

        VisualConnection previewConnection = visualAutomata.getPreviewConnection();
        previewConnection.setDestination(destination);
        previewConnection.setPreviewMode(false);

        textTyper = new TextTyper(previewConnection.getConditionNode().getPos(), 1);
        dialogueBalloon = new DialogueBalloon(panel, previewConnection.getConditionNode(), "Inserte la condición");
        insertingCondition = true;

        panel.addComponent(dialogueBalloon);
        panel.textBox().setTitle("Asígnale una condición a la conexión.");
      }
    }
  }

  private void updateConnectionPreview(AFNDPanel panel, MouseEvent e) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    if (!isNull(origin)) {
      if (!previewConnectionInserted) {
        previewConnectionInserted = visualAutomata.insertPreviewConnection(origin.element());
      } else {
        Point cursorPreviewPos = visualAutomata.cursorPreviewPos();
        cursorPreviewPos.x = e.getX();
        cursorPreviewPos.y = e.getY();
      }
    }
  }

  private void insertCondition(AFNDPanel panel, KeyEvent keyEvent) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    VisualConnection previewConnection = visualAutomata.getPreviewConnection();
    textTyper.handleInputEvent(keyEvent);
    previewConnection.setCondition(textTyper.getText());
    panel.repaint();
  }

  private boolean insertConnection(AFNDPanel panel) {
    if (textTyper.getText().isEmpty()) {
      return false;
    }

    VisualAutomata visualAutomata = panel.getVisualAutomata();
    String condition = textTyper.getText();

    visualAutomata.insertNormalConnection(origin.element(), destination.element(), condition);
    previousConnection = null;
    return true;
  }

  private boolean originHasBeenSelected() {
    return !isNull(origin);
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    if (!isNull(previousConnection)) {
      visualAutomata.removePreviewConnection();
      visualAutomata.insertConnection(
          previousConnection.getOrigin().element(),
          previousConnection.getDestination().element(),
          previousConnection.getConditionNode().element()
      );
    }

    visualAutomata.removeCursorPreview();

    if (!isNull(origin)) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }

    panel.removeComponent(dialogueBalloon);

    origin = null;
    destination = null;
    dialogueBalloon = null;
    insertingCondition = false;
    previewConnectionInserted = false;
    AFNDState.super.clearState(afndGraph, panel, afndStateDispatcher);
  }

}
