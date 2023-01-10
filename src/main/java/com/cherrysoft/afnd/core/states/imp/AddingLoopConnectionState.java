package com.cherrysoft.afnd.core.states.imp;

import com.cherrysoft.afnd.core.states.AutomataState;
import com.cherrysoft.afnd.view.components.DialogueBalloon;
import com.cherrysoft.afnd.view.components.TextTyper;
import com.cherrysoft.afnd.view.components.afnd.VisualAutomata;
import com.cherrysoft.afnd.view.components.afnd.VisualConnection;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;
import com.cherrysoft.afnd.view.components.afnd.connections.LoopConnection;

import static java.util.Objects.isNull;

public class AddingLoopConnectionState extends AutomataState {
  private static AddingLoopConnectionState instance;
  private boolean insertingCondition;
  private VisualNode origin;
  private VisualConnection previousConnection;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;

  private AddingLoopConnectionState() {
  }

  public synchronized static AddingLoopConnectionState getInstance() {
    if (instance == null) {
      instance = new AddingLoopConnectionState();
    }
    return instance;
  }

  @Override
  public void updateGraphState() {
    if (isMousePressed()) {
      clearState();
      selectOrigin();
    }
    if (isKeyPressed()) {
      if (insertingCondition && isEnterPressed()) {
        if (insertConnection()) {
          clearState();
        } else {
          dialogueBalloon.setText("Invalid condition!");
          panel.repaint();
        }
      }
      if (insertingCondition) {
        insertCondition();
      }
    }
    panel.repaint();
  }

  private void selectOrigin() {
    if (isNull(origin)) {
      VisualNode pressedNode = visualAutomata.getVisualNodeBellow(getMousePos());
      if (!isNull(pressedNode)) {
        origin = pressedNode;
        if (visualAutomata.existConnection(origin.element(), origin.element())) {
          previousConnection = visualAutomata.getVisualConnection(origin.element(), origin.element());
          visualAutomata.removeConnection(origin.element(), origin.element());
        }

        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);
        visualAutomata.insertPreviewConnection(LoopConnection.LOOP_CONNECTION_ID, origin.element());
        VisualConnection previewConnection = visualAutomata.getPreviewConnection();
        previewConnection.setDestination(origin);
        previewConnection.setPreviewMode(false);

        textTyper = new TextTyper(previewConnection.getConditionNode().getPos(), 1);
        dialogueBalloon = new DialogueBalloon(panel, previewConnection.getConditionNode(), "Insert condition");
        insertingCondition = true;

        panel.addComponent(dialogueBalloon);
        panel.textBox().setTitle("Insert state's condition");
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
    return visualAutomata.insertLoopConnection(origin.element(), condition);
  }

  @Override
  public void clearState() {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    if (!isNull(previousConnection)) {
      visualAutomata.insertLoopConnection(
          previousConnection.getOrigin().element(),
          previousConnection.getConditionNode().element()
      );
    }

    visualAutomata.removePreviewCursor();

    if (!isNull(origin)) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }

    panel.removeComponent(dialogueBalloon);

    origin = null;
    dialogueBalloon = null;
    insertingCondition = false;
    panel.textBox().clear();
    panel.repaint();
  }

}
