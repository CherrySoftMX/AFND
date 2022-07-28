package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.automata.VisualAFND;
import me.hikingcarrot7.afnd.view.components.automata.VisualNode;
import me.hikingcarrot7.afnd.view.components.automata.estados.AFNDStateFactory;
import me.hikingcarrot7.afnd.view.components.automata.estados.AFNDStateFactoryImp;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AddingNodeState implements AFNDState {
  private static AddingNodeState instance;

  public synchronized static AddingNodeState getInstance() {
    if (instance == null) {
      instance = new AddingNodeState();
    }
    return instance;
  }

  private AddingNodeState() {
    factory = AFNDStateFactoryImp.getInstance();
  }

  private final AFNDStateFactory factory;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;
  private VisualNode previewNode;
  private boolean namingState = false;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == KeyEvent.KEY_PRESSED) {
      KeyEvent keyEvent = (KeyEvent) event;
      if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
        if (addState(afndGraph, visualAFND, previewNode.getPos(), buttonID)) {
          clearState(afndGraph, visualAFND, afndStateDispatcher);
        } else {
          dialogueBalloon.setText("Ese estado ya existe!");
          visualAFND.repaint();
          return;
        }
      }
      if (namingState) {
        nombrarEstado(visualAFND, keyEvent);
        return;
      }
    }
    if (!namingState && event.getID() == MouseEvent.MOUSE_CLICKED) {
      previewNewState(afndGraph, visualAFND, (MouseEvent) event, buttonID);
    }
  }

  private void previewNewState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, MouseEvent e, int buttonID) {
    switch (buttonID) {
      case Menu.ESTADO_INICIAL_ID:
      case Menu.ESTADO_INICIAL_FINAL_ID:
        if (afndGraph.hasInitialState()) {
          visualAFND.getDefaultTextBox().setTitle("Ya has establecido el estado inicial!");
          visualAFND.repaint();
          return;
        }
    }

    int pressedNode = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());

    if (pressedNode < 0) {
      previewNode = factory.createState(buttonID, "", e.getPoint());
      visualAFND.addVNode(previewNode);

      textTyper = new TextTyper(e.getPoint(), 6);
      visualAFND.addComponent(textTyper, VisualAFND.MIDDLE_LAYER);

      dialogueBalloon = new DialogueBalloon(visualAFND, previewNode, "Inserte el nombre");
      visualAFND.addComponent(dialogueBalloon, VisualAFND.MIDDLE_LAYER);

      namingState = true;

      visualAFND.getDefaultTextBox().setTitle("Ponle un nombre al estado, acepta con ENTER");
      visualAFND.repaint();
    }
  }

  private boolean addState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, Point center, int buttonID) {
    String element = textTyper.getText();
    boolean elementInserted;
    if (element.length() <= 0) {
      return false;
    }
    switch (buttonID) {
      case Menu.ESTADO_INICIAL_ID:
        elementInserted = afndGraph.insertAsInitialState(element);
        break;
      case Menu.ESTADO_INICIAL_FINAL_ID:
        elementInserted = afndGraph.insertAsInitialAndFinalState(element);
        break;
      case Menu.ESTADO_FINAL_ID:
        elementInserted = afndGraph.insertAsFinalState(element);
        break;
      default:
        elementInserted = afndGraph.insertElement(element);
    }
    visualAFND.addVNode(factory.createState(buttonID, element, center));
    visualAFND.repaint();
    return elementInserted;
  }

  private void nombrarEstado(VisualAFND visualAFND, KeyEvent event) {
    textTyper.handleInputEvent(event);
    visualAFND.repaint();
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    visualAFND.removeComponent(textTyper);
    visualAFND.removeComponent(dialogueBalloon);
    visualAFND.removeVNode(previewNode);
    namingState = false;
    AFNDState.super.clearState(afndGraph, visualAFND, afndStateDispatcher);
  }

}
