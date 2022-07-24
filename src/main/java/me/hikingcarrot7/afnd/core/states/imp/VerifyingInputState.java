package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.automata.AFNDGraph;
import me.hikingcarrot7.afnd.core.automata.exceptions.CadenaVaciaException;
import me.hikingcarrot7.afnd.core.automata.exceptions.NoExisteEstadoInicialException;
import me.hikingcarrot7.afnd.core.states.AFNDState;
import me.hikingcarrot7.afnd.core.states.AFNDStateManager;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.view.graphics.Box;
import me.hikingcarrot7.afnd.core.graphs.Connection;
import me.hikingcarrot7.afnd.core.utils.Pair;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.TextBox;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;

import java.awt.event.InputEvent;
import java.util.List;

public class VerifyingInputState implements AFNDState {
  private static VerifyingInputState instance;

  public synchronized static VerifyingInputState getInstance() {
    if (instance == null) {
      instance = new VerifyingInputState();
    }
    return instance;
  }

  private boolean comprobado;
  private final TextBox messageBox;

  private VerifyingInputState() {
    messageBox = new TextBox.TextBoxBuilder()
        .setBoxPosition(Box.BoxPosition.TOP_LEFT)
        .build();
  }

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (!comprobado) {
      comprobarAFND(afndGraph, vafnd);
    }
    vafnd.repaint();
  }

  private void comprobarAFND(AFNDGraph<String> afndGraph, VAFND vafnd) {
    String text = Menu.TEXT_FIELD.getText();
    try {
      boolean matches = afndGraph.matches(text);
      vafnd.addComponent(messageBox, VAFND.MAX_LAYER);
      if (matches) {
        messageBox.setTitle("La palabra FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        pintarRecorrido(vafnd, afndGraph.getRecorrido());
      } else {
        messageBox.setTitle("La palabra NO FUE ACEPTADA por el autómata");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
      }

    } catch (CadenaVaciaException | NoExisteEstadoInicialException e) {
      vafnd.getDefaultTextBox().setTitle(e.getMessage());
      vafnd.repaint();
    }
    comprobado = true;
  }

  private void pintarRecorrido(VAFND vafnd, List<Pair<Connection<?>, String>> recorrido) {
    recorrido.forEach(pair -> {
      Connection<?> connection = pair.getLeft();
      VNode origen = vafnd.getVNode(connection.getOrigin().getElement().toString());
      VNode destino = vafnd.getVNode(connection.getDestination().getElement().toString());
      VArch varch = vafnd.getVArch(origen, destino);
      origen.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
      destino.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
      varch.setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    vafnd.getVNodes().forEach(vnode -> vnode.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE));
    vafnd.getVArchs().forEach(varch -> {
      varch.setColorPalette(VArch.DEFAULT_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      vafnd.setVArchZIndex(varch, VAFND.MIN_LAYER);
    });
    comprobado = false;
    afndGraph.clearRecorrido();
    vafnd.removeComponent(messageBox);
    AFNDState.super.clearState(afndGraph, vafnd, afndStateManager);
  }

}
