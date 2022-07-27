package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateManager;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.view.components.automata.conexiones.ConexionBucle;

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

  private boolean insertandoCondicion;
  private VNode origen;
  private VArch previewArch;
  private VArch previousArch;
  private TextTyper textTyper;
  private DialogueBalloon dialogueballoon;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      clearState(afndGraph, vafnd, afndStateManager);
      selectOrigen(afndGraph, vafnd, (MouseEvent) event);
    }

    if (event.getID() == KeyEvent.KEY_PRESSED) {
      KeyEvent keyEvent = (KeyEvent) event;
      if (insertandoCondicion && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
        if (addArch(afndGraph, vafnd)) {
          clearState(afndGraph, vafnd, afndStateManager);
        } else {
          dialogueballoon.setText("El valor es inválido");
          vafnd.repaint();
        }
      }

      if (insertandoCondicion) {
        insertarEstado(vafnd, keyEvent);
      }
    }

    vafnd.repaint();
  }

  private void selectOrigen(AFNDGraph<String> afndGraph, VAFND vafnd, MouseEvent e) {
    if (origen == null) {
      int verticePresionado = GraphUtils.getVerticePresionado(afndGraph, vafnd.getVNodes(), e.getPoint());
      if (verticePresionado >= 0) {
        origen = vafnd.getVNode(verticePresionado);

        if (afndGraph.existConnection(origen.getName(), origen.getName())) {
          previousArch = vafnd.getVArch(origen, origen);
          afndGraph.removeConnection(origen.getName(), origen.getName());
          vafnd.removeVArch(previousArch);
        }

        origen.setColorPalette(VNode.SELECTED_VNODE_COLOR_PALETTE);

        previewArch = new ConexionBucle(origen, origen, true);

        textTyper = new TextTyper(previewArch.getBlob().getPos(), 1);
        dialogueballoon = new DialogueBalloon(vafnd, previewArch.getBlob(), "Inserte la condición");

        vafnd.addVArch(previewArch, VAFND.MIN_LAYER);
        vafnd.addComponent(dialogueballoon, VAFND.MIDDLE_LAYER);
        vafnd.getDefaultTextBox().setTitle("Inserte la condición para el estado");
        insertandoCondicion = true;
      }
    }
  }

  private void insertarEstado(VAFND vafnd, KeyEvent keyEvent) {
    textTyper.handleInputEvent(keyEvent);
    previewArch.setCondicion(textTyper.getText());
    vafnd.repaint();
  }

  private boolean addArch(AFNDGraph<String> afndGraph, VAFND vafnd) {
    if (textTyper.getText().isEmpty()) {
      return false;
    }

    String text = textTyper.getText();

    afndGraph.insertConnection(origen.getName(), origen.getName(), text);
    vafnd.addVArch(new ConexionBucle(origen, origen, text), VAFND.MIN_LAYER);
    previousArch = null;
    return true;
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    if (previousArch != null) {
      afndGraph.insertConnection(
          ((VNode) previousArch.getOrigen()).getName(),
          ((VNode) previousArch.getDestino()).getName(),
          previousArch.getCondicion());

      vafnd.addVArch(previousArch, VAFND.MIN_LAYER);
    }

    if (previewArch != null) {
      vafnd.removeVArch(previewArch);
    }

    if (origen != null) {
      origen.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE);
    }

    vafnd.removeComponent(dialogueballoon);

    origen = null;
    previewArch = null;
    dialogueballoon = null;
    insertandoCondicion = false;
    vafnd.getDefaultTextBox().clearTextBox();
    vafnd.repaint();
  }

}
