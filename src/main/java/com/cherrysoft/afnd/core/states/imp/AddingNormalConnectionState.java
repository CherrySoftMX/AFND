package com.cherrysoft.afnd.core.states.imp;

import com.cherrysoft.afnd.core.states.AutomataState;
import com.cherrysoft.afnd.view.components.DialogueBalloon;
import com.cherrysoft.afnd.view.components.TextTyper;
import com.cherrysoft.afnd.view.components.afnd.VisualConnection;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;
import com.cherrysoft.afnd.view.components.afnd.connections.NormalConnection;

import java.awt.*;

import static java.util.Objects.isNull;

public class AddingNormalConnectionState extends AutomataState {
  private static AddingNormalConnectionState instance;
  private VisualNode origin;
  private VisualNode destination;
  private VisualConnection previousConnection;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;
  private boolean insertingCondition;
  private boolean previewConnectionInserted = false;
  private AddingNormalConnectionState() {
  }

  public synchronized static AddingNormalConnectionState getInstance() {
    if (instance == null) {
      instance = new AddingNormalConnectionState();
    }
    return instance;
  }

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
          dialogueBalloon.setText("Invalid condition");
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
        panel.textBox().setTitle("Right click on another state to create a connection");
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
        dialogueBalloon = new DialogueBalloon(panel, previewConnection.getConditionNode(), "Insert condition");
        insertingCondition = true;

        panel.addComponent(dialogueBalloon);
        panel.textBox().setTitle("Insert connection's condition");
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
