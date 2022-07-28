package me.hikingcarrot7.afnd.view.components.automata.conexiones;

import me.hikingcarrot7.afnd.core.utils.MathHelper;
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
public class ConexionNormal extends VisualConnection {

    public ConexionNormal(Movable origen, Movable destino, String condicion, ColorPalette colorPalette) {
        super(origen, destino, condicion, colorPalette);
    }

    public ConexionNormal(Movable origen, Movable destino, String condicion, boolean previewMode) {
        super(origen, destino, condicion, previewMode);
    }

    public ConexionNormal(Movable origen, Movable condicion, String character) {
        super(origen, condicion, character);
    }

    public ConexionNormal(Movable origen, Movable destino, boolean previewMode) {
        super(origen, destino, previewMode);
    }

    @Override public void draw(Graphics2D g) {
        Stroke defaultStroke = g.getStroke();
        Color defaultColor = g.getColor();

        g.setColor(colorPalette.getSpecificColor(ColorPalette.ColorKey.FILL_COLOR_KEY));
        g.setStroke(new BasicStroke(STROKE_WIDTH));

        if (previewMode)
            g.drawLine(origin.xCenter(), origin.yCenter(), destination.xCenter(), destination.yCenter());
        else {
            GeneralPath path = new GeneralPath();
            path.moveTo(origin.xCenter(), origin.yCenter());

            Point puntoMedio = MathHelper.puntoMedio(origin.getPos(), destination.getPos());
            Point puntoControl = MathHelper.puntoControl(origin.getPos(), destination.getPos(), ALTURA_CURVATURA);

            path.curveTo(
                    puntoMedio.x + puntoControl.x,
                    puntoMedio.y + puntoControl.y,
                    puntoMedio.x + puntoControl.x,
                    puntoMedio.y + puntoControl.y,
                    destination.xCenter(),
                    destination.yCenter());

            g.draw(path);

            updateBlobPosition(g, origin.getPos(), destination.getPos());
            updateTrianglePosition(g, origin.getPos(), destination.getPos(), ALTURA_CURVATURA);
        }

        g.setStroke(defaultStroke);
        g.setColor(defaultColor);
    }

    @Override public void updateTrianglePosition(Graphics2D g, Point origin, Point destination, int alturaCurvatura) {
        Point puntoMedio = MathHelper.puntoMedio(origin, destination);
        Point puntoControl = MathHelper.puntoControl(origin, destination, alturaCurvatura);

        triangle.setOrigenX(puntoMedio.x + puntoControl.x);
        triangle.setOrigenY(puntoMedio.y + puntoControl.y);
        triangle.setDestinoX(destination.x);
        triangle.setDestinoY(destination.y);
        triangle.setLength(TRIANGLE_LENGTH);
        triangle.setOffset(VisualNode.NODE_RADIUS + STROKE_WIDTH);
    }

    @Override public void updateBlobPosition(Graphics2D g, Point origin, Point destination) {
        Point puntoMedio = MathHelper.puntoMedio(origin, destination);
        Point puntoControl = MathHelper.puntoControl(origin, destination, ALTURA_CURVATURA);
        g.setColor(Menu.GRAY_TEXT_COLOR);
        int textWidth = GraphicsUtils.getStringWidth(g, condition);
        int blobRadio = textWidth / 2 + BLOB_PADDING * 2;

        blob.setElement(condition);
        blob.setRadio(blobRadio);
        blob.setXCenter(puntoMedio.x + puntoControl.x);
        blob.setYCenter(puntoMedio.y + puntoControl.y);
    }

}
