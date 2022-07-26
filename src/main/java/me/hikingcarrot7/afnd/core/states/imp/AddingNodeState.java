package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.automata.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateManager;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.view.components.automata.estados.AFNDEstadoFactory;
import me.hikingcarrot7.afnd.view.components.automata.estados.AFNDEstadoFactoryImp;

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
    factory = AFNDEstadoFactoryImp.getInstance();
  }

  private final AFNDEstadoFactory factory;
  private TextTyper textTyper;
  private DialogueBalloon dialogueballoon;
  private VNode previewNode;
  private boolean namingState = false;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (event.getID() == KeyEvent.KEY_PRESSED) {
      KeyEvent keyEvent = (KeyEvent) event;
      if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
        if (addState(afndGraph, vafnd, previewNode.getCoords(), buttonID)) {
          clearState(afndGraph, vafnd, afndStateManager);
        } else {
          dialogueballoon.setText("Ese estado ya existe!");
          vafnd.repaint();
          return;
        }
      }
      if (namingState) {
        nombrarEstado(vafnd, keyEvent);
        return;
      }
    }
    if (!namingState && event.getID() == MouseEvent.MOUSE_CLICKED) {
      previewNewState(afndGraph, vafnd, (MouseEvent) event, buttonID);
    }
  }

  private void previewNewState(AFNDGraph<String> afndGraph, VAFND vafnd, MouseEvent e, int buttonID) {
    switch (buttonID) {
      case Menu.ESTADO_INICIAL_ID:
      case Menu.ESTADO_INICIAL_FINAL_ID:
        if (afndGraph.hasInitialState()) {
          vafnd.getDefaultTextBox().setTitle("Ya has establecido el estado inicial!");
          vafnd.repaint();
          return;
        }
    }

    int pressedNode = GraphUtils.getVerticePresionado(afndGraph, vafnd.getVNodes(), e.getPoint());

    if (pressedNode < 0) {
      previewNode = factory.createEstado(buttonID, "", e.getPoint());
      vafnd.addVNode(previewNode);

      textTyper = new TextTyper(e.getPoint(), 6);
      vafnd.addComponent(textTyper, VAFND.MIDDLE_LAYER);

      dialogueballoon = new DialogueBalloon(vafnd, previewNode, "Inserte el nombre");
      vafnd.addComponent(dialogueballoon, VAFND.MIDDLE_LAYER);

      namingState = true;

      vafnd.getDefaultTextBox().setTitle("Ponle un nombre al estado, acepta con ENTER");
      vafnd.repaint();
    }
  }

  private boolean addState(AFNDGraph<String> afndGraph, VAFND vafnd, Point center, int buttonID) {
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
    vafnd.addVNode(factory.createEstado(buttonID, element, center));
    vafnd.repaint();
    return elementInserted;
  }

  private void nombrarEstado(VAFND vafnd, KeyEvent event) {
    textTyper.handleInputEvent(event);
    vafnd.repaint();
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    vafnd.removeComponent(textTyper);
    vafnd.removeComponent(dialogueballoon);
    vafnd.removeVNode(previewNode);
    namingState = false;
    AFNDState.super.clearState(afndGraph, vafnd, afndStateManager);
  }

}
