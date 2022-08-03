package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.states.AutomataState;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.components.afnd.connections.NormalConnection;

import java.awt.*;

import static java.util.Objects.isNull;

public class AddingNormalConnectionState extends AutomataState {
  private static AddingNormalConnectionState instance;

  public synchronized static AddingNormalConnectionState getInstance() {
    if (instance == null) {
      instance = new AddingNormalConnectionState();
    }
    return instance;
  }

  private AddingNormalConnectionState() {
  }

  private VisualNode origin;
  private VisualNode destination;
  private VisualConnection previousConnection;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;
  private boolean insertingCondition;
  private boolean previewConnectionInserted = false;

  @Override
  public void updateGraphState() {
    if (isMouseEvent()) {
      if (isMouseClicked()) {
        if (isLeftClick()) {
          if (originHasBeenSelected()) {
            selectDestination();
          } else {
            selectOrigin();
          }
        } else {
          clearState();
        }
        panel.repaint();
      }
      if (isMouseMoved()) {
        updateConnectionPreview();
        panel.repaint();
      }
    }

    if (isKeyPressed()) {
      if (insertingCondition && isEnterPressed()) {
        if (insertConnection()) {
          clearState();
        } else {
          dialogueBalloon.setText("El valor es inválido");
          panel.repaint();
        }
      }
      if (insertingCondition) {
        insertCondition();
      }
    }
  }

  private void selectOrigin() {
    if (isNull(origin)) {
      VisualNode pressedNode = visualAutomata.getVisualNodeBellow(getMousePos());
      if (!isNull(pressedNode) && visualAutomata.hasAtLeastOneNode()) {
        origin = pressedNode;
        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);
        panel.textBox().setTitle("Da click derecho a otro estado para crear una conexión.");
      }
    }
  }

  private void selectDestination() {
    VisualNode pressedNode = visualAutomata.getVisualNodeBellow(getMousePos());

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

  private void updateConnectionPreview() {
    if (!isNull(origin)) {
      if (!previewConnectionInserted) {
        previewConnectionInserted = visualAutomata.insertPreviewConnection(NormalConnection.NORMAL_CONNECTION_ID, origin.element());
      } else {
        Point cursorPreviewPos = visualAutomata.cursorPreviewPos();
        Point mousePos = getMousePos();
        cursorPreviewPos.x = mousePos.x;
        cursorPreviewPos.y = mousePos.y;
      }
    }
  }

  private void insertCondition() {
    VisualConnection previewConnection = visualAutomata.getPreviewConnection();
    textTyper.handleInputEvent(getAsKeyEvent());
    previewConnection.setCondition(textTyper.getText());
    panel.repaint();
  }

  private boolean insertConnection() {
    String condition = textTyper.getText();
    if (condition.isEmpty()) {
      return false;
    }
    previousConnection = null;
    return visualAutomata.insertNormalConnection(origin.element(), destination.element(), condition);
  }

  private boolean originHasBeenSelected() {
    return !isNull(origin);
  }

  @Override
  public void clearState() {
    if (!isNull(previousConnection)) {
      visualAutomata.insertNormalConnection(
          previousConnection.getOrigin().element(),
          previousConnection.getDestination().element(),
          previousConnection.getConditionNode().element()
      );
    }

    visualAutomata.removePreviewCursor();

    if (!isNull(origin)) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }

    panel.removeComponent(dialogueBalloon);

    origin = null;
    destination = null;
    dialogueBalloon = null;
    insertingCondition = false;
    previewConnectionInserted = false;
    super.clearState();
  }

}
