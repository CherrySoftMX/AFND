package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.VNode;
import java.awt.Point;

/**
 *
 * @author HikingCarrot7
 */
public class AFNDEstadoFactoryImp implements AFNDEstadoFactory {

    private static AFNDEstadoFactoryImp instance;

    public synchronized static AFNDEstadoFactoryImp getInstance() {
        if (instance == null)
            instance = new AFNDEstadoFactoryImp();

        return instance;
    }

    private AFNDEstadoFactoryImp() {
    }

    @Override public VNode createEstado(int id, String data, Point centerCoords) {
        switch (id) {
            case Menu.ESTADO_INICIAL_ID:
                return new EstadoInicial(data, centerCoords.x, centerCoords.y);
            case Menu.ESTADO_NORMAL_ID:
                return new EstadoNormal(data, centerCoords.x, centerCoords.y);
            case Menu.ESTADO_FINAL_ID:
                return new EstadoFinal(data, centerCoords.x, centerCoords.y);
            case Menu.ESTADO_INICIAL_FINAL_ID:
                return new EstadoInicialFinal(data, centerCoords.x, centerCoords.y);
        }

        return null;
    }

}
