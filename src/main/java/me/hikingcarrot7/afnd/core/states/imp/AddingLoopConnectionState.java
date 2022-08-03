package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;
import me.hikingcarrot7.afnd.view.components.afnd.GraphUtils;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.components.afnd.connections.LoopConnection;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AddingLoopConnectionState implements AFNDState {
  private static AddingLoopConnectionState instance;

  public synchronized static AddingLoopConnectionState getInstance() {
    if (instance == null) {
      instance = new AddingLoopConnectionState();
    }
    return instance;
  }

  private AddingLoopConnectionState() {
  }

  private boolean insertingCondition;
  private VisualNode origin;
  private VisualConnection previewArch;
  private VisualConnection previousArch;
  private TextTyper textTyper;
  private DialogueBalloon dialogueBalloon;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      clearState(afndGraph, panel, afndStateDispatcher);
      selectOrigin(afndGraph, panel, (MouseEvent) event);
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
        insertarEstado(panel, keyEvent);
      }
    }
    panel.repaint();
  }

  private void selectOrigin(AFNDGraph<String> afndGraph, AFNDPanel panel, MouseEvent e) {
    if (origin == null) {
      int pressedNode = GraphUtils.getPressedNode(afndGraph, panel.getVNodes(), e.getPoint());
      if (pressedNode >= 0) {
        origin = panel.getVNode(pressedNode);

        if (afndGraph.existConnection(origin.element(), origin.element())) {
          previousArch = panel.getVArch(origin, origin);
          afndGraph.removeConnection(origin.element(), origin.element());
          panel.removeVArch(previousArch);
        }

        origin.setColorPalette(VisualNode.SELECTED_NODE_COLOR_PALETTE);

        previewArch = new LoopConnection(origin, origin, true);

        textTyper = new TextTyper(previewArch.getConditionNode().getPos(), 1);
        dialogueBalloon = new DialogueBalloon(panel, previewArch.getConditionNode(), "Inserte la condición");

        panel.addVArch(previewArch, AFNDPanel.MIN_LAYER);
        panel.addComponent(dialogueBalloon);
        panel.textBox().setTitle("Inserte la condición para el estado");
        insertingCondition = true;
      }
    }
  }

  private void insertarEstado(AFNDPanel AFNDPanel, KeyEvent keyEvent) {
    textTyper.handleInputEvent(keyEvent);
    previewArch.setCondition(textTyper.getText());
    AFNDPanel.repaint();
  }

  private boolean addArch(AFNDGraph<String> afndGraph, AFNDPanel panel) {
    if (textTyper.getText().isEmpty()) {
      return false;
    }

    String text = textTyper.getText();

    afndGraph.insertConnection(origin.element(), origin.element(), text);
    panel.addVArch(new LoopConnection(origin, origin, text), AFNDPanel.MIN_LAYER);
    previousArch = null;
    return true;
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, AFNDPanel panel, AFNDStateDispatcher afndStateDispatcher) {
    if (previousArch != null) {
      afndGraph.insertConnection(
          previousArch.getOrigin().element(),
          previousArch.getDestination().element(),
          previousArch.condition()
      );
      panel.addVArch(previousArch, AFNDPanel.MIN_LAYER);
    }

    if (previewArch != null) {
      panel.removeVArch(previewArch);
    }

    if (origin != null) {
      origin.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE);
    }

    panel.removeComponent(dialogueBalloon);

    origin = null;
    previewArch = null;
    dialogueBalloon = null;
    insertingCondition = false;
    panel.textBox().clearTextBox();
    panel.repaint();
  }

}
