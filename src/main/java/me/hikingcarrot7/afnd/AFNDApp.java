package me.hikingcarrot7.afnd;

import me.hikingcarrot7.afnd.bootstrapper.AppBootstrapper;
import me.hikingcarrot7.afnd.view.MainView;

import java.awt.*;

public class AFNDApp {
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      MainView view = new MainView();
      view.setLocationRelativeTo(null);
      view.setVisible(true);
      new AppBootstrapper(view);
    });
  }
}
