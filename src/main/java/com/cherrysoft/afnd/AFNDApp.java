package com.cherrysoft.afnd;

import com.cherrysoft.afnd.bootstrapper.AppBootstrapper;
import com.cherrysoft.afnd.view.MainView;

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
