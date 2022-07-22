package me.hikingcarrot7.afnd.model.states;

import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.model.utils.GraphUtils;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author HikingCarrot7
 */
public class MoverAFNDEstadoState implements AFNDState {

    private static MoverAFNDEstadoState instance;

    public synchronized static MoverAFNDEstadoState getInstance() {
        if (instance == null)
            instance = new MoverAFNDEstadoState();

        return instance;
    }

    private MoverAFNDEstadoState() {
    }

    private int offsetX;
    private int offsetY;
    private int nEstadoMover = -1;

    @Override public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
        if (event.getID() == MouseEvent.MOUSE_RELEASED) {
            nEstadoMover = -1;
            vafnd.getDefaultTextBox().clearTextBox();
            vafnd.repaint();
            return;
        }

        if (event.getID() == MouseEvent.MOUSE_PRESSED)
            calcularOffsets(afnd, vafnd, event);

        moverEstado(vafnd, event);
        vafnd.repaint();
    }

    private void calcularOffsets(AFND<String> afnd, VAFND vafnd, InputEvent event) {
        if (event instanceof MouseEvent) {
            MouseEvent e = (MouseEvent) event;
            nEstadoMover = GraphUtils.getVerticePresionado(afnd, vafnd.getVNodes(), e.getPoint());
            if (esEstadoValido()) {
                offsetX = e.getX() - vafnd.getVNode(nEstadoMover).getXCenter();
                offsetY = e.getY() - vafnd.getVNode(nEstadoMover).getYCenter();
                vafnd.getDefaultTextBox().setTitle("Moviendo estado");
            }
        }
    }

    private void moverEstado(VAFND vafnd, InputEvent event) {
        if (esEstadoValido() && event instanceof MouseEvent) {
            MouseEvent e = (MouseEvent) event;
            int nuevaCoordenadaX = e.getX() - offsetX;
            int nuevaCoordenadaY = e.getY() - offsetY;

            vafnd.getVNode(nEstadoMover).setXCenter(nuevaCoordenadaX);
            vafnd.getVNode(nEstadoMover).setYCenter(nuevaCoordenadaY);
        }
    }

    private boolean esEstadoValido() {
        return nEstadoMover >= 0;
    }

}
