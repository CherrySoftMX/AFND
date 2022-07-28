package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateDispatcher;
import me.hikingcarrot7.afnd.core.utils.GraphUtils;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.automata.VisualConnection;
import me.hikingcarrot7.afnd.view.components.automata.VisualNode;
import me.hikingcarrot7.afnd.view.components.automata.VisualAFND;
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
  private VisualNode origen;
  private VisualConnection previewArch;
  private VisualConnection previousArch;
  private TextTyper textTyper;
  private DialogueBalloon dialogueballoon;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_PRESSED) {
      clearState(afndGraph, visualAFND, afndStateDispatcher);
      selectOrigen(afndGraph, visualAFND, (MouseEvent) event);
    }

    if (event.getID() == KeyEvent.KEY_PRESSED) {
      KeyEvent keyEvent = (KeyEvent) event;
      if (insertandoCondicion && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
        if (addArch(afndGraph, visualAFND)) {
          clearState(afndGraph, visualAFND, afndStateDispatcher);
        } else {
          dialogueballoon.setText("El valor es inválido");
          visualAFND.repaint();
        }
      }

      if (insertandoCondicion) {
        insertarEstado(visualAFND, keyEvent);
      }
    }

    visualAFND.repaint();
  }

  private void selectOrigen(AFNDGraph<String> afndGraph, VisualAFND visualAFND, MouseEvent e) {
    if (origen == null) {
      int verticePresionado = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
      if (verticePresionado >= 0) {
        origen = visualAFND.getVNode(verticePresionado);

        if (afndGraph.existConnection(origen.getElement(), origen.getElement())) {
          previousArch = visualAFND.getVArch(origen, origen);
          afndGraph.removeConnection(origen.getElement(), origen.getElement());
          visualAFND.removeVArch(previousArch);
        }

        origen.setColorPalette(VisualNode.SELECTED_VNODE_COLOR_PALETTE);

        previewArch = new ConexionBucle(origen, origen, true);

        textTyper = new TextTyper(previewArch.getBlob().getPos(), 1);
        dialogueballoon = new DialogueBalloon(visualAFND, previewArch.getBlob(), "Inserte la condición");

        visualAFND.addVArch(previewArch, VisualAFND.MIN_LAYER);
        visualAFND.addComponent(dialogueballoon, VisualAFND.MIDDLE_LAYER);
        visualAFND.getDefaultTextBox().setTitle("Inserte la condición para el estado");
        insertandoCondicion = true;
      }
    }
  }

  private void insertarEstado(VisualAFND visualAFND, KeyEvent keyEvent) {
    textTyper.handleInputEvent(keyEvent);
    previewArch.setCondition(textTyper.getText());
    visualAFND.repaint();
  }

  private boolean addArch(AFNDGraph<String> afndGraph, VisualAFND visualAFND) {
    if (textTyper.getText().isEmpty()) {
      return false;
    }

    String text = textTyper.getText();

    afndGraph.insertConnection(origen.getElement(), origen.getElement(), text);
    visualAFND.addVArch(new ConexionBucle(origen, origen, text), VisualAFND.MIN_LAYER);
    previousArch = null;
    return true;
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher) {
    if (previousArch != null) {
      afndGraph.insertConnection(
          ((VisualNode) previousArch.getOrigin()).getElement(),
          ((VisualNode) previousArch.getDestination()).getElement(),
          previousArch.getCondition());

      visualAFND.addVArch(previousArch, VisualAFND.MIN_LAYER);
    }

    if (previewArch != null) {
      visualAFND.removeVArch(previewArch);
    }

    if (origen != null) {
      origen.setColorPalette(VisualNode.DEFAULT_VNODE_COLOR_PALETTE);
    }

    visualAFND.removeComponent(dialogueballoon);

    origen = null;
    previewArch = null;
    dialogueballoon = null;
    insertandoCondicion = false;
    visualAFND.getDefaultTextBox().clearTextBox();
    visualAFND.repaint();
  }

}
