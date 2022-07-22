package me.hikingcarrot7.afnd.view.components.automata.conexiones;

import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.VArch;
import me.hikingcarrot7.afnd.view.components.VNode;
import me.hikingcarrot7.afnd.view.graphics.ColorPalette;
import me.hikingcarrot7.afnd.view.graphics.GraphicsUtils;
import me.hikingcarrot7.afnd.view.graphics.Movable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;

/**
 *
 * @author HikingCarrot7
 */
public class ConexionBucle extends VArch {

    public ConexionBucle(Movable origen, Movable destino, String condicion, ColorPalette colorPalette) {
        super(origen, destino, condicion, colorPalette);
    }

    public ConexionBucle(Movable origen, Movable destino, String condicion, boolean previewMode) {
        super(origen, destino, condicion, previewMode);
    }

    public ConexionBucle(Movable origen, Movable destino, String condicion) {
        super(origen, destino, condicion);
    }

    public ConexionBucle(Movable origen, Movable destino, boolean previewMode) {
        super(origen, destino, previewMode);
    }

    @Override public void draw(Graphics2D g) {
        Stroke defaultStroke = g.getStroke();
        Color defaultColor = g.getColor();

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
        g.setStroke(new BasicStroke(STROKE_WIDTH));

        Point puntoInicioDerecho = getPuntoInicioDerecho();
        Point puntoInicioIzquierdo = getPuntoInicioIzquierdo();
        Point puntoControlDerecho = getPuntoControlDerecho();
        Point puntoControlIzquierdo = getPuntoControlIzquierdo();

        GeneralPath path = new GeneralPath();
        path.moveTo(origen.getXCenter() + puntoInicioIzquierdo.x, origen.getYCenter() + puntoInicioIzquierdo.y);
        path.curveTo(
                origen.getXCenter() + puntoControlIzquierdo.x,
                origen.getYCenter() + puntoControlIzquierdo.y,
                origen.getXCenter() + puntoControlDerecho.x,
                origen.getYCenter() + puntoControlDerecho.y,
                origen.getXCenter() + puntoInicioDerecho.x,
                origen.getYCenter() + puntoInicioDerecho.y);

        g.draw(path);

        updateTrianglePosition(g, puntoControlDerecho, puntoInicioDerecho, ALTURA_CURVATURA);
        updateBlobPosition(g, puntoInicioIzquierdo, puntoInicioDerecho);

        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

    @Override public void updateTrianglePosition(Graphics2D g, Point origen, Point destino, int alturaCurvatura) {
        triangle.setOrigenX(this.origen.getXCenter() + origen.x);
        triangle.setOrigenY(this.origen.getYCenter() + origen.y);
        triangle.setDestinoX(this.destino.getXCenter() + destino.x);
        triangle.setDestinoY(this.destino.getYCenter() + destino.y);
        triangle.setLength(TRIANGLE_LENGTH);
        triangle.setOffset(VNode.STROKE_WIDTH);
    }

    @Override public void updateBlobPosition(Graphics2D g, Point origen, Point destino) {
        Point puntoControlDerecho = getPuntoControlDerecho();

        g.setColor(Menu.GRAY_TEXT_COLOR);
        int textWidth = GraphicsUtils.getStringWidth(g, condicion);
        int blobRadio = textWidth / 2 + BLOB_PADDING * 2;

        blob.setName(condicion);
        blob.setRadio(blobRadio);
        blob.setXCenter(this.origen.getXCenter());
        blob.setYCenter(this.origen.getYCenter() + puntoControlDerecho.y);
    }

    private Point getPuntoInicioDerecho() {
        return new Point((int) (VNode.RADIO_NODO * Math.cos(-Math.PI / 4)),
                (int) (VNode.RADIO_NODO * Math.sin(-Math.PI / 4)));
    }

    private Point getPuntoInicioIzquierdo() {
        return new Point((int) (VNode.RADIO_NODO * Math.cos(Math.PI + Math.PI / 4)),
                (int) ((VNode.RADIO_NODO) * Math.sin(Math.PI + Math.PI / 4)));
    }

    private Point getPuntoControlDerecho() {
        Point puntoInicioDerecho = getPuntoInicioDerecho();
        return new Point(puntoInicioDerecho.x, puntoInicioDerecho.y - ALTURA_CURVATURA - 15);
    }

    private Point getPuntoControlIzquierdo() {
        Point puntoInicioIzquierdo = getPuntoInicioIzquierdo();
        return new Point(puntoInicioIzquierdo.x, puntoInicioIzquierdo.y - ALTURA_CURVATURA - 15);
    }

}
