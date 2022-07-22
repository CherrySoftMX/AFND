package me.hikingcarrot7.afnd.model.states;

import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.view.components.automata.estados.AFNDEstadoFactory;
import me.hikingcarrot7.afnd.view.components.automata.estados.AFNDEstadoFactoryImp;
import me.hikingcarrot7.afnd.model.graph.Node;
import me.hikingcarrot7.afnd.model.graph.exceptions.GrafoLlenoException;
import me.hikingcarrot7.afnd.model.graph.exceptions.NodoYaExisteException;
import me.hikingcarrot7.afnd.model.utils.GraphUtils;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author HikingCarrot7
 */
public class AddAFNDEstadoState implements AFNDState {

    private static AddAFNDEstadoState instance;

    public synchronized static AddAFNDEstadoState getInstance() {
        if (instance == null)
            instance = new AddAFNDEstadoState();

        return instance;
    }

    private AddAFNDEstadoState() {
        factory = AFNDEstadoFactoryImp.getInstance();
    }

    private final AFNDEstadoFactory factory;
    private TextTyper textTyper;
    private DialogueBalloon dialogueballoon;
    private VNode previewEstado;
    private boolean nombrandoEstado = false;

    @Override public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
        if (event.getID() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                if (addEstado(afnd, vafnd, previewEstado.getCoords(), buttonID))
                    clearState(afnd, vafnd, afndStateManager);
                else {
                    dialogueballoon.setText("Ese estado ya existe!");
                    vafnd.repaint();
                    return;
                }

            if (nombrandoEstado) {
                nombrarEstado(vafnd, keyEvent);
                return;
            }
        }

        if (!nombrandoEstado && event.getID() == MouseEvent.MOUSE_CLICKED)
            previewNewEstado(afnd, vafnd, (MouseEvent) event, buttonID);
    }

    private void previewNewEstado(AFND<String> afnd, VAFND vafnd, MouseEvent e, int buttonID) {
        switch (buttonID) {
            case Menu.ESTADO_INICIAL_ID:
            case Menu.ESTADO_INICIAL_FINAL_ID:
                if (afnd.estadoInicialEstablecido()) {
                    vafnd.getDefaultTextBox().setTitle("Ya has establecido el estado inicial!");
                    vafnd.repaint();
                    return;
                }
        }

        int verticePresionado = GraphUtils.getVerticePresionado(afnd, vafnd.getVNodes(), e.getPoint());

        if (verticePresionado < 0) {
            previewEstado = factory.createEstado(buttonID, "", e.getPoint());
            vafnd.addVNode(previewEstado);

            textTyper = new TextTyper(e.getPoint(), 6);
            vafnd.addComponent(textTyper, VAFND.MIDDLE_LAYER);

            dialogueballoon = new DialogueBalloon(vafnd, previewEstado, "Inserte el nombre");
            vafnd.addComponent(dialogueballoon, VAFND.MIDDLE_LAYER);

            nombrandoEstado = true;

            vafnd.getDefaultTextBox().setTitle("Ponle un nombre al estado, acepta con ENTER");
            vafnd.repaint();
        }
    }

    private boolean addEstado(AFND<String> afnd, VAFND vafnd, Point coords, int buttonID) {
        String name = textTyper.getText();
        Node<String> newNode = new Node<>(name);
        try {
            if (name.length() <= 0)
                throw new IllegalStateException();

            switch (buttonID) {
                case Menu.ESTADO_INICIAL_ID:
                    afnd.setEstadoInicial(newNode);
                    afnd.addNode(newNode);
                    break;
                case Menu.ESTADO_INICIAL_FINAL_ID:
                    afnd.setEstadoInicial(newNode);
                    afnd.addEstadoFinal(newNode);
                    afnd.addNode(newNode);
                    break;
                case Menu.ESTADO_FINAL_ID:
                    afnd.addEstadoFinal(newNode);
                    afnd.addNode(newNode);
                    break;
                default:
                    afnd.addNode(newNode);
            }

            vafnd.addVNode(factory.createEstado(buttonID, name, coords));
            vafnd.repaint();
        } catch (IllegalStateException | GrafoLlenoException | NodoYaExisteException e) {
            return false;
        }

        return true;
    }

    private void nombrarEstado(VAFND vafnd, KeyEvent event) {
        textTyper.handleInputEvent(event);
        vafnd.repaint();
    }

    @Override public void clearState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager) {
        vafnd.removeComponent(textTyper);
        vafnd.removeComponent(dialogueballoon);
        vafnd.removeVNode(previewEstado);
        nombrandoEstado = false;
        AFNDState.super.clearState(afnd, vafnd, afndStateManager);
    }

}
