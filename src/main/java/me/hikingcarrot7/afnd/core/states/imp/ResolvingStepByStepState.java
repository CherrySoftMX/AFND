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
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class ResolvingStepByStepState implements AFNDState {
  private static ResolvingStepByStepState instance;

  public synchronized static ResolvingStepByStepState getInstance() {
    if (instance == null) {
      instance = new ResolvingStepByStepState();
    }
    return instance;
  }

  private boolean comprobado = false;
  private boolean palabraAceptada = false;
  private Iterator<Pair<Connection<?>, String>> recorridoIterator;
  private final TextBox messageBox;

  private ResolvingStepByStepState() {
    messageBox = new TextBox.TextBoxBuilder()
        .setBoxPosition(Box.BoxPosition.TOP_LEFT)
        .build();
  }

  @Override
  public void updateGraphState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
    if (event.getID() == MouseEvent.MOUSE_CLICKED) {
      if (palabraAceptada) {
        pintarSiguientePaso(afndGraph, vafnd);
      } else {
        comprobarAutomata(afndGraph, vafnd);
      }
    }
    if (!comprobado) {
      comprobarAutomata(afndGraph, vafnd);
    }
    vafnd.repaint();
  }

  private boolean comprobarAutomata(AFNDGraph<String> afndGraph, VAFND vafnd) {
    String text = Menu.TEXT_FIELD.getText();
    try {
      boolean matches = afndGraph.matches(text);
      vafnd.addComponent(messageBox, VAFND.MAX_LAYER);
      if (matches) {
        messageBox.setTitle("Palabra ACEPTADA, presiona sobre el aútomata para iniciar el recorrido");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        palabraAceptada = true;
      } else {
        messageBox.setTitle("Palabra NO ACEPTADA, no podemos iniciar el recorrido");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
        palabraAceptada = false;
      }
      comprobado = true;
      return matches;
    } catch (CadenaVaciaException | NoExisteEstadoInicialException e) {
      vafnd.getDefaultTextBox().setTitle(e.getMessage());
      comprobado = true;
      return false;
    }
  }

  private void pintarSiguientePaso(AFNDGraph<String> afndGraph, VAFND vafnd) {
    if (recorridoIterator == null) {
      recorridoIterator = afndGraph.getRecorrido().iterator();
    }
    if (recorridoIterator.hasNext()) {
      Pair<Connection<?>, String> pair = recorridoIterator.next();
      Connection<?> connection = pair.getLeft();
      clearAllMarks(vafnd);

      VNode origen = vafnd.getVNode(connection.getOrigin().getElement().toString());
      VNode destino = vafnd.getVNode(connection.getDestination().getElement().toString());
      VArch varch = vafnd.getVArch(origen, destino);

      origen.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
      destino.setColorPalette(VNode.SELECTED_RUTA_VNODE_COLOR_PALETTE);
      varch.setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(VArch.SELECTED_VARCH_COLOR_PALETTE);

      messageBox.setTitle("Palabra por ser consumida: " + pair.getRight());
    } else {
      clearAllMarks(vafnd);
      messageBox.setTitle("La palabra ha sido consumida");
    }

    vafnd.repaint();
  }

  private void clearAllMarks(VAFND vafnd) {
    vafnd.getVNodes().forEach(vnode -> vnode.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE));
    vafnd.getVArchs().forEach(varch -> {
      varch.setColorPalette(VArch.DEFAULT_VARCH_COLOR_PALETTE);
      varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
      vafnd.setVArchZIndex(varch, VAFND.MIN_LAYER);
    });
  }

  @Override
  public void clearState(AFNDGraph<String> afndGraph, VAFND vafnd, AFNDStateManager afndStateManager) {
    comprobado = false;
    palabraAceptada = false;
    recorridoIterator = null;
    afndGraph.clearRecorrido();
    vafnd.removeComponent(messageBox);
    clearAllMarks(vafnd);
    AFNDState.super.clearState(afndGraph, vafnd, afndStateManager);
  }

}