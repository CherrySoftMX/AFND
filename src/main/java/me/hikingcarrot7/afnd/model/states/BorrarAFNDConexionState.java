package me.hikingcarrot7.afnd.model.states;

import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.view.components.Blob;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;
import me.hikingcarrot7.afnd.model.utils.GraphUtils;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author HikingCarrot7
 */
public class BorrarAFNDConexionState implements AFNDState {

    private static BorrarAFNDConexionState instance;

    public synchronized static BorrarAFNDConexionState getInstance() {
        if (instance == null)
            instance = new BorrarAFNDConexionState();

        return instance;
    }

    private BorrarAFNDConexionState() {
    }

    private VNode origen;

    @Override public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID) {
        if (event.getID() == MouseEvent.MOUSE_PRESSED) {
            MouseEvent e = (MouseEvent) event;
            if (origen != null) {
                removeArch(afnd, vafnd, afndStateManager, e);
                if (e.getButton() != MouseEvent.BUTTON1)
                    clearState(afnd, vafnd, afndStateManager);
            } else {
                clearState(afnd, vafnd, afndStateManager);
                selectNode(afnd, vafnd, afndStateManager, (MouseEvent) event);
            }
        }
    }

    private void removeArch(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, MouseEvent e) {
        int nVerticePresionado = GraphUtils.getVerticePresionado(afnd, vafnd.getVNodes(), e.getPoint());

        if (nVerticePresionado >= 0) {
            VNode destino = vafnd.getVNode(nVerticePresionado);
            afnd.removeArch(origen.getName(), destino.getName());
            vafnd.removeVArch(vafnd.getVArch(origen, destino));
            clearState(afnd, vafnd, afndStateManager);
        }
    }

    private void selectNode(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, MouseEvent e) {
        int nVerticePresionado = GraphUtils.getVerticePresionado(afnd, vafnd.getVNodes(), e.getPoint());

        if (nVerticePresionado >= 0) {
            VNode pressedNode = vafnd.getVNode(nVerticePresionado);
            List<VArch> adjacentArchs = GraphUtils.getAdjacentVArchs(pressedNode, vafnd);

            if (!adjacentArchs.isEmpty()) {
                origen = pressedNode;
                origen.setColorPalette(VNode.SELECTED_VNODE_COLOR_PALETTE);
                markAdjacentArchs(origen, vafnd);
                vafnd.getDefaultTextBox().setTitle("Presione click izquierdo sobre algún estado adyacente para borrar la conexión");
                vafnd.repaint();
            } else
                clearState(afnd, vafnd, afndStateManager);
        }
    }

    private void markAdjacentArchs(VNode vnode, VAFND vgraph) {
        List<VArch> adjacentArchs = GraphUtils.getAdjacentVArchs(vnode, vgraph);
        adjacentArchs.forEach(varch -> {
            varch.setColorPalette(VArch.RED_VARCH_COLOR_PALETTE);
            varch.getTriangle().setColorPalette(Triangle.RED_TRIANGLE_COLOR_PALETTE);
            vgraph.setVArchZIndex(varch, VAFND.MAX_LAYER);
        });
    }

    @Override public void clearState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager) {
        vafnd.getVArchs().forEach(varch -> {
            varch.setColorPalette(VArch.DEFAULT_VARCH_COLOR_PALETTE);
            varch.getBlob().setColorPalette(Blob.DEFAULT_BLOB_COLOR_PALETTE);
            varch.getTriangle().setColorPalette(Triangle.VARCH_TRIANGLE_COLOR_PALETTE);
            vafnd.setVArchZIndex(varch, VAFND.MIN_LAYER);
        });

        if (origen != null)
            origen.setColorPalette(VNode.DEFAULT_VNODE_COLOR_PALETTE);

        origen = null;
        AFNDState.super.clearState(afnd, vafnd, afndStateManager);
    }

}
