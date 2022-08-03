package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.states.AutomataState;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAutomata;

import java.awt.*;

import static java.util.Objects.isNull;

public class AddingNodeState extends AutomataState {
  private static AddingNodeState instance;

  public synchronized static AddingNodeState getInstance() {
    if (instance == null) {
      instance = new AddingNodeState();
    }
    return instance;
  }

  private AddingNodeState() {
  }

  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;
  private boolean namingState = false;

  @Override
  public void updateGraphState() {
    if (isKeyPressed()) {
      if (isEnterPressed()) {
        if (insertState(visualAutomata.previewNodePos())) {
          clearState();
        } else {
          dialogueBalloon.setText("Ese estado ya existe!");
          panel.repaint();
          return;
        }
      }
      if (namingState) {
        nameState();
        return;
      }
    }
    if (!namingState && isMouseClicked()) {
      previewNewState();
    }
  }

  private void previewNewState() {
    Point point = getMousePos();
    switch (stateId) {
      case Menu.INITIAL_STATE_ID:
      case Menu.INITIAL_FINAL_STATE_ID:
        if (visualAutomata.hasInitialState()) {
          panel.textBox().setTitle("Ya has establecido el estado inicial!");
          panel.repaint();
          return;
        }
    }

    Point pressedNodePos = visualAutomata.getPosOfNodeBellow(point);

    if (isNull(pressedNodePos)) {
      visualAutomata.insertPreviewNode(stateId, point);

      textTyper = new TextTyper(point, 6);
      panel.addComponent(textTyper);

      dialogueBalloon = new DialogueBalloon(panel, visualAutomata.previewNode(), "Inserte el nombre");
      panel.addComponent(dialogueBalloon);

      namingState = true;

      panel.textBox().setTitle("Ponle un nombre al estado, acepta con ENTER");
      panel.repaint();
    }
  }

  private boolean insertState(Point pos) {
    String element = textTyper.getText();
    boolean elementInserted;
    if (element.length() <= 0) {
      return false;
    }
    switch (stateId) {
      case Menu.INITIAL_STATE_ID:
        elementInserted = visualAutomata.insertAsInitialState(element, pos);
        break;
      case Menu.INITIAL_FINAL_STATE_ID:
        elementInserted = visualAutomata.insertAsInitialAndFinalState(element, pos);
        break;
      case Menu.FINAL_STATE_ID:
        elementInserted = visualAutomata.insertAsFinalState(element, pos);
        break;
      default:
        elementInserted = visualAutomata.insertElement(element, pos);
    }
    panel.repaint();
    return elementInserted;
  }

  private void nameState() {
    textTyper.handleInputEvent(getAsKeyEvent());
    panel.repaint();
  }

  @Override
  public void clearState() {
    panel.removeComponent(textTyper);
    panel.removeComponent(dialogueBalloon);
    visualAutomata.removePreviewNode();
    namingState = false;
    super.clearState();
  }

}
