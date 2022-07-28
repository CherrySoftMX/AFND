package me.hikingcarrot7.afnd.view.components.automata.conexiones;

import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.automata.VisualConnection;
import me.hikingcarrot7.afnd.view.components.automata.VisualNode;
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
public class ConexionBucle extends VisualConnection {

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
        path.moveTo(origin.xCenter() + puntoInicioIzquierdo.x, origin.yCenter() + puntoInicioIzquierdo.y);
        path.curveTo(
                origin.xCenter() + puntoControlIzquierdo.x,
                origin.yCenter() + puntoControlIzquierdo.y,
                origin.xCenter() + puntoControlDerecho.x,
                origin.yCenter() + puntoControlDerecho.y,
                origin.xCenter() + puntoInicioDerecho.x,
                origin.yCenter() + puntoInicioDerecho.y);

        g.draw(path);

        updateTrianglePosition(g, puntoControlDerecho, puntoInicioDerecho, ALTURA_CURVATURA);
        updateBlobPosition(g, puntoInicioIzquierdo, puntoInicioDerecho);

        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

    @Override public void updateTrianglePosition(Graphics2D g, Point origin, Point destination, int alturaCurvatura) {
        triangle.setOrigenX(this.origin.xCenter() + origin.x);
        triangle.setOrigenY(this.origin.yCenter() + origin.y);
        triangle.setDestinoX(this.destination.xCenter() + destination.x);
        triangle.setDestinoY(this.destination.yCenter() + destination.y);
        triangle.setLength(TRIANGLE_LENGTH);
        triangle.setOffset(VisualNode.STROKE_WIDTH);
    }

    @Override public void updateBlobPosition(Graphics2D g, Point origin, Point destination) {
        Point puntoControlDerecho = getPuntoControlDerecho();

        g.setColor(Menu.GRAY_TEXT_COLOR);
        int textWidth = GraphicsUtils.getStringWidth(g, condition);
        int blobRadio = textWidth / 2 + BLOB_PADDING * 2;

        blob.setElement(condition);
        blob.setRadio(blobRadio);
        blob.setXCenter(this.origin.xCenter());
        blob.setYCenter(this.origin.yCenter() + puntoControlDerecho.y);
    }

    private Point getPuntoInicioDerecho() {
        return new Point((int) (VisualNode.NODE_RADIUS * Math.cos(-Math.PI / 4)),
                (int) (VisualNode.NODE_RADIUS * Math.sin(-Math.PI / 4)));
    }

    private Point getPuntoInicioIzquierdo() {
        return new Point((int) (VisualNode.NODE_RADIUS * Math.cos(Math.PI + Math.PI / 4)),
                (int) ((VisualNode.NODE_RADIUS) * Math.sin(Math.PI + Math.PI / 4)));
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
