package me.hikingcarrot7.afnd.model.utils;

import java.awt.Point;

public class MathHelper {

  public static int numeroAleatorio(int minimo, int maximo) {
    return (int) (Math.random() * ((maximo + 1) - minimo)) + minimo;
  }

  public static double distanciaEntreDosPuntos(Point p1, Point p2) {
    return Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
  }

  public static Point puntoMedio(Point origen, Point destino) {
    return new Point((origen.x + destino.x) / 2, (origen.y + destino.y) / 2);
  }

  public static Point puntoControl(Point origen, Point destino, int alturaCurvatura) {
    double angle = Math.atan2(
      destino.y - origen.y,
      destino.x - origen.x) - Math.PI / 2;

    return new Point((int) (alturaCurvatura * Math.cos(angle)),
      (int) (alturaCurvatura * Math.sin(angle)));
  }
}
