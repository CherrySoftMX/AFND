package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualAutomata;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static java.util.Objects.isNull;

public class AddingNodeState implements AFNDState {
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
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    if (event.getID() == KeyEvent.KEY_PRESSED) {
      KeyEvent keyEvent = (KeyEvent) event;
      if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
        if (addState(panel, visualAutomata.previewNodePos(), buttonID)) {
          clearState(afndGraph, panel, afndStateDispatcher);
        } else {
          dialogueBalloon.setText("Ese estado ya existe!");
          panel.repaint();
          return;
        }
      }
      if (namingState) {
        nameState(panel, keyEvent);
        return;
      }
    }
    if (!namingState && event.getID() == MouseEvent.MOUSE_CLICKED) {
      previewNewState(panel, (MouseEvent) event, buttonID);
    }
  }

  private void previewNewState(AFNDPanel panel, MouseEvent e, int stateId) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    switch (stateId) {
      case Menu.INITIAL_STATE_ID:
      case Menu.INITIAL_FINAL_STATE_ID:
        if (visualAutomata.hasInitialState()) {
          panel.textBox().setTitle("Ya has establecido el estado inicial!");
          panel.repaint();
          return;
        }
    }

    Point pressedNodePos = visualAutomata.getPosOfNodeBellow(e.getPoint());

    if (isNull(pressedNodePos)) {
      visualAutomata.insertPreviewNode(stateId, e.getPoint());

      textTyper = new TextTyper(e.getPoint(), 6);
      panel.addComponent(textTyper);

      dialogueBalloon = new DialogueBalloon(panel, visualAutomata.previewNode(), "Inserte el nombre");
      panel.addComponent(dialogueBalloon);

      namingState = true;

      panel.textBox().setTitle("Ponle un nombre al estado, acepta con ENTER");
      panel.repaint();
    }
  }

  private boolean addState(AFNDPanel panel, Point center, int stateId) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    String element = textTyper.getText();
    boolean elementInserted;
    if (element.length() <= 0) {
      return false;
    }
    switch (stateId) {
      case Menu.INITIAL_STATE_ID:
        elementInserted = visualAutomata.insertAsInitialState(element, center);
        break;
      case Menu.INITIAL_FINAL_STATE_ID:
        elementInserted = visualAutomata.insertAsInitialAndFinalState(element, center);
        break;
      case Menu.FINAL_STATE_ID:
        elementInserted = visualAutomata.insertAsFinalState(element, center);
        break;
      default:
        elementInserted = visualAutomata.insertElement(element, center);
    }
    panel.repaint();
    return elementInserted;
  }

  private void nameState(AFNDPanel panel, KeyEvent event) {
    textTyper.handleInputEvent(event);
    panel.repaint();
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher) {
    VisualAutomata visualAutomata = panel.getVisualAutomata();
    panel.removeComponent(textTyper);
    panel.removeComponent(dialogueBalloon);
    visualAutomata.removePreviewNode();
    namingState = false;
    AFNDState.super.clearState(afndGraph, panel, afndStateDispatcher);
  }

}
