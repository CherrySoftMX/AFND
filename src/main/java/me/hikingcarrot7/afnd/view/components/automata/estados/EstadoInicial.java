package me.hikingcarrot7.afnd.view.components.automata.estados;

import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.VNode;
import java.awt.Graphics2D;

/**
 *
 * @author HikingCarrot7
 */
public class EstadoInicial extends VNode {

    public static final int TRIANGLE_LENGTH = 25;
    public static final int TRIANGLE_STROKE_WIDTH = 4;

    private final Triangle triangle;

    public EstadoInicial(String name, int xCenter, int yCenter) {
        super(name, xCenter, yCenter, VNode.DEFAULT_VNODE_COLOR_PALETTE);
        this.triangle = new Triangle();
        this.triangle.setStrokeWidth(TRIANGLE_STROKE_WIDTH);
    }

    @Override public void draw(Graphics2D g) {
        super.draw(g);
        updateTriangleCoords();
        triangle.draw(g);
    }

    private void updateTriangleCoords() {
        triangle.setOrigenX(xCenter - TRIANGLE_LENGTH - VNode.RADIO_NODO - VNode.STROKE_WIDTH);
        triangle.setOrigenY(yCenter);
        triangle.setDestinoX(xCenter);
        triangle.setDestinoY(yCenter);
        triangle.setLength(TRIANGLE_LENGTH);
        triangle.setOffset(VNode.RADIO_NODO + VNode.STROKE_WIDTH);
    }

    public Triangle getTriangle() {
        return triangle;
    }

}
