package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.VNode;
import java.awt.Point;

/**
 *
 * @author HikingCarrot7
 */
public interface AFNDEstadoFactory {

    public VNode createEstado(int id, String data, Point centerCoords);

}
