package me.hikingcarrot7.afnd.model.states;

import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.view.components.DialogueBalloon;
import me.hikingcarrot7.afnd.view.components.TextTyper;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.view.components.automata.conexiones.ConexionBucle;
import me.hikingcarrot7.afnd.model.utils.GraphUtils;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author HikingCarrot7
 */
public class AddAFNDConexionBucleState implements AFNDState {

    private static AddAFNDConexionBucleState instance;

    public synchronized static AddAFNDConexionBucleState getInstance() {
        if (instance == null)
            instance = new AddAFNDConexionBucleState();

        return instance;
    }

    private AddAFNDConexionBucleState() {
    }

    private boolean insertandoCondicion;
    private VNode origen;
    private VArch previewArch;
    private VArch previousArch;
    private TextTyper textTyper;
    private DialogueBalloon dialogueballoon;

    @Override public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            clearState(afnd, vafnd, afndStateManager);
            selectOrigen(afnd, vafnd, (MouseEvent) event);
        }

        if (event.getID() == KeyEvent.KEY_PRESSED) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (insertandoCondicion && keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                if (addArch(afnd, vafnd))
                    clearState(afnd, vafnd, afndStateManager);
                else {
                    dialogueballoon.setText("El valor es inválido");
                    vafnd.repaint();
                }

            if (insertandoCondicion)
                insertarEstado(vafnd, keyEvent);
        }

        vafnd.repaint();
    }

    private void selectOrigen(AFND<String> afnd, VAFND vafnd, MouseEvent e) {
        if (origen == null) {
            int verticePresionado = GraphUtils.getVerticePresionado(afnd, vafnd.getVNodes(), e.getPoint());
            if (verticePresionado >= 0) {
                origen = vafnd.getVNode(verticePresionado);

                if (afnd.existeConexion(origen.getName(), origen.getName())) {
                    previousArch = vafnd.getVArch(origen, origen);
                    afnd.removeArch(origen.getName(), origen.getName());
                    vafnd.removeVArch(previousArch);
                }

                origen.setColorPalette(VNode.SELECTED_VNODE_COLOR_PALETTE);

                previewArch = new ConexionBucle(origen, origen, true);

                textTyper = new TextTyper(previewArch.getBlob().getCoords(), 1);
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

    private boolean addArch(AFND<String> afnd, VAFND vafnd) {
        if (textTyper.getText().isEmpty())
            return false;

        String text = textTyper.getText();

        afnd.addArch(origen.getName(), origen.getName(), text);
        vafnd.addVArch(new ConexionBucle(origen, origen, text), VAFND.MIN_LAYER);
        previousArch = null;
        return true;
    }

    @Override public void clearState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager) {
        if (previousArch != null) {
            afnd.addArch(
                    ((VNode) previousArch.getOrigen()).getName(),
                    ((VNode) previousArch.getDestino()).getName(),
                    previousArch.getCondicion());

            vafnd.addVArch(previousArch, VAFND.MIN_LAYER);
        }

        if (previewArch != null)
            vafnd.removeVArch(previewArch);

        if (origen != null)
            ((VNode) origen).setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE);

        vafnd.removeComponent(dialogueballoon);

        origen = null;
        previewArch = null;
        dialogueballoon = null;
        insertandoCondicion = false;
        vafnd.getDefaultTextBox().clearTextBox();
        vafnd.repaint();
    }

}
