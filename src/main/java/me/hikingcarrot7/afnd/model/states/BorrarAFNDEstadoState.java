package me.hikingcarrot7.afnd.model.states;

import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.model.utils.GraphUtils;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author HikingCarrot7
 */
public class BorrarAFNDEstadoState implements AFNDState {

    private static BorrarAFNDEstadoState instance;

    public synchronized static BorrarAFNDEstadoState getInstance() {
        if (instance == null)
            instance = new BorrarAFNDEstadoState();

        return instance;
    }

    private BorrarAFNDEstadoState() {
    }

    @Override public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            eliminarVertice(afnd, vafnd, event);
            vafnd.repaint();
        }
    }

    private void eliminarVertice(AFND<String> afnd, VAFND vafnd, InputEvent event) {
        MouseEvent e = (MouseEvent) event;
        int nVerticeSeleccionado = GraphUtils.getVerticePresionado(afnd, vafnd.getVNodes(), e.getPoint());

        if (nVerticeSeleccionado >= 0) {
            VNode vnode = vafnd.getVNode(nVerticeSeleccionado);
            afnd.removeNode(vnode.getName());
            vafnd.removeVNode(vnode);
            removeAllAdjacentVArchs(vnode, vafnd);
        }

    }

    private void removeAllAdjacentVArchs(VNode vnode, VAFND vafnd) {
        List<VArch> varchs = vafnd.getVArchs();

        List<VArch> adjacentVArchs = varchs.stream()
                .filter(varch -> ((VNode) varch.getOrigen()).getName().equals(vnode.getName())
                || ((VNode) varch.getDestino()).getName().equals(vnode.getName()))
                .collect(Collectors.toList());

        for (int i = varchs.size() - 1; i >= 0; i--) {
            VArch varch = varchs.get(i);
            if (adjacentVArchs.contains(varch))
                vafnd.removeVArch(varch);
        }
    }

}
