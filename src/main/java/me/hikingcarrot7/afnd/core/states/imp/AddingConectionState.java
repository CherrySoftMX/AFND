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
import me.hikingcarrot7.afnd.view.components.automata.conexiones.ConexionNormal;
import me.hikingcarrot7.afnd.view.graphics.Movable;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class AddingConectionState implements AFNDState {
  private static AddingConectionState instance;

  public synchronized static AddingConectionState getInstance() {
    if (instance == null) {
      instance = new AddingConectionState();
    }
    return instance;
  }

  private AddingConectionState() {
  }

  private boolean insertandoCondicion;
  private VisualNode origen;
  private VisualNode destino;
  private Movable cursor;
  private VisualConnection previewArch;
  private VisualConnection previousArch;
  private TextTyper textTyper;
  private DialogueBalloon dialogueballoon;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VisualAFND visualAFND, AFNDStateDispatcher afndStateDispatcher, InputEvent event, int buttonID) {
    if (event instanceof MouseEvent) {
      MouseEvent mouseEvent = (MouseEvent) event;
      if (event.getID() == MouseEvent.MOUSE_CLICKED) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
          if (origenSeleccionado()) {
            selectDestino(afndGraph, visualAFND, mouseEvent);
          } else {
            selectOrigen(afndGraph, visualAFND, mouseEvent);
          }
        } else {
          clearState(afndGraph, visualAFND, afndStateDispatcher);
        }
        visualAFND.repaint();
      }
      if (event.getID() == MouseEvent.MOUSE_MOVED) {
        updateArchPreview(visualAFND, mouseEvent);
        visualAFND.repaint();
      }
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

  }

  private void selectOrigen(AFNDGraph<String> afndGraph, VisualAFND visualAFND, MouseEvent e) {
    if (origen == null) {
      int verticePresionado = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());
      if (verticePresionado >= 0 && afndGraph.cardinality() > 1) {
        origen = visualAFND.getVNode(verticePresionado);
        origen.setColorPalette(VisualNode.SELECTED_VNODE_COLOR_PALETTE);
        visualAFND.getDefaultTextBox().setTitle("Da click derecho a otro estado para crear una conexión.");
      }
    }
  }

  private void selectDestino(AFNDGraph<String> afndGraph, VisualAFND visualAFND, MouseEvent e) {
    int nVerticePresionado = GraphUtils.getPressedNode(afndGraph, visualAFND.getVNodes(), e.getPoint());

    if (nVerticePresionado >= 0) {
      destino = visualAFND.getVNode(nVerticePresionado);

      if (destino != origen) {
        if (afndGraph.existConnection(origen.getElement(), destino.getElement())) {
          previousArch = visualAFND.getVArch(origen, destino);
          afndGraph.removeConnection(origen.getElement(), destino.getElement());
          visualAFND.removeVArch(previousArch);
        }

        destino = visualAFND.getVNode(nVerticePresionado);
        previewArch.setDestination(destino);
        previewArch.setPreviewMode(false);

        textTyper = new TextTyper(previewArch.getBlob().getPos(), 1);
        dialogueballoon = new DialogueBalloon(visualAFND, previewArch.getBlob(), "Inserte la condición");
        insertandoCondicion = true;

        visualAFND.addComponent(dialogueballoon, VisualAFND.MIDDLE_LAYER);
        visualAFND.getDefaultTextBox().setTitle("Asígnale una condición a la conexión.");
      }
    }
  }

  private void updateArchPreview(VisualAFND visualAFND, MouseEvent e) {
    if (origen != null) {
      if (cursor == null) {
        cursor = new VisualNode("CURSOR_PREVIEW", e.getPoint());
        previewArch = new ConexionNormal(origen, cursor, true);
        visualAFND.addVArch(previewArch, VisualAFND.MIN_LAYER);
      } else {
        cursor.setXCenter(e.getX());
        cursor.setYCenter(e.getY());
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

    afndGraph.insertConnection(origen.getElement(), destino.getElement(), text);
    visualAFND.addVArch(new ConexionNormal(origen, destino, text), VisualAFND.MIN_LAYER);
    previousArch = null;
    return true;
  }

  private boolean origenSeleccionado() {
    return origen != null;
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

    visualAFND.removeVNode((VisualNode) cursor);

    if (previewArch != null) {
      visualAFND.removeVArch(previewArch);
    }

    if (origen != null) {
      origen.setColorPalette(VisualNode.DEFAULT_VNODE_COLOR_PALETTE);
    }

    visualAFND.removeComponent(dialogueballoon);

    cursor = null;
    origen = null;
    destino = null;
    previewArch = null;
    dialogueballoon = null;
    insertandoCondicion = false;
    AFNDState.super.clearState(afndGraph, visualAFND, afndStateDispatcher);
  }

}
