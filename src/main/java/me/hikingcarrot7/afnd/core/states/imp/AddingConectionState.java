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
  private VNode origen;
  private VNode destino;
  private Movable cursor;
  private VArch previewArch;
  private VArch previousArch;
  private TextTyper textTyper;
  private DialogueBalloon dialogueballoon;

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (event instanceof MouseEvent) {
      MouseEvent mouseEvent = (MouseEvent) event;
      if (event.getID() == MouseEvent.MOUSE_CLICKED) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
          if (origenSeleccionado()) {
            selectDestino(afndGraph, vafnd, mouseEvent);
          } else {
            selectOrigen(afndGraph, vafnd, mouseEvent);
          }
        } else {
          clearState(afndGraph, vafnd, afndStateManager);
        }
        vafnd.repaint();
      }
      if (event.getID() == MouseEvent.MOUSE_MOVED) {
        updateArchPreview(vafnd, mouseEvent);
        vafnd.repaint();
      }
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

  }

  private void selectOrigen(AFNDGraph<String> afndGraph, VAFND vafnd, MouseEvent e) {
    if (origen == null) {
      int verticePresionado = GraphUtils.getVerticePresionado(afndGraph, vafnd.getVNodes(), e.getPoint());
      if (verticePresionado >= 0 && afndGraph.cardinality() > 1) {
        origen = vafnd.getVNode(verticePresionado);
        origen.setColorPalette(VNode.SELECTED_VNODE_COLOR_PALETTE);
        vafnd.getDefaultTextBox().setTitle("Da click derecho a otro estado para crear una conexión.");
      }
    }
  }

  private void selectDestino(AFNDGraph<String> afndGraph, VAFND vafnd, MouseEvent e) {
    int nVerticePresionado = GraphUtils.getVerticePresionado(afndGraph, vafnd.getVNodes(), e.getPoint());

    if (nVerticePresionado >= 0) {
      destino = vafnd.getVNode(nVerticePresionado);

      if (destino != origen) {
        if (afndGraph.existConnection(origen.getName(), destino.getName())) {
          previousArch = vafnd.getVArch(origen, destino);
          afndGraph.removeConnection(origen.getName(), destino.getName());
          vafnd.removeVArch(previousArch);
        }

        destino = vafnd.getVNode(nVerticePresionado);
        previewArch.setDestino(destino);
        previewArch.setPreviewMode(false);

        textTyper = new TextTyper(previewArch.getBlob().getPos(), 1);
        dialogueballoon = new DialogueBalloon(vafnd, previewArch.getBlob(), "Inserte la condición");
        insertandoCondicion = true;

        vafnd.addComponent(dialogueballoon, VAFND.MIDDLE_LAYER);
        vafnd.getDefaultTextBox().setTitle("Asígnale una condición a la conexión.");
      }
    }
  }

  private void updateArchPreview(VAFND vafnd, MouseEvent e) {
    if (origen != null) {
      if (cursor == null) {
        cursor = new VNode("CURSOR_PREVIEW", e.getPoint());
        previewArch = new ConexionNormal(origen, cursor, true);
        vafnd.addVArch(previewArch, VAFND.MIN_LAYER);
      } else {
        cursor.setXCenter(e.getX());
        cursor.setYCenter(e.getY());
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

    afndGraph.insertConnection(origen.getName(), destino.getName(), text);
    vafnd.addVArch(new ConexionNormal(origen, destino, text), VAFND.MIN_LAYER);
    previousArch = null;
    return true;
  }

  private boolean origenSeleccionado() {
    return origen != null;
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

    vafnd.removeVNode((VNode) cursor);

    if (previewArch != null) {
      vafnd.removeVArch(previewArch);
    }

    if (origen != null) {
      origen.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE);
    }

    vafnd.removeComponent(dialogueballoon);

    cursor = null;
    origen = null;
    destino = null;
    previewArch = null;
    dialogueballoon = null;
    insertandoCondicion = false;
    AFNDState.super.clearState(afndGraph, vafnd, afndStateManager);
  }

}
