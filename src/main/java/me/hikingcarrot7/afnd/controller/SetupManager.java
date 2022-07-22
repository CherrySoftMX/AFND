package me.hikingcarrot7.afnd.controller;

import me.hikingcarrot7.afnd.controller.events.EventHandler;
import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.model.states.*;
import me.hikingcarrot7.afnd.view.MainView;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class SetupManager {
  private final MainView view;

  public SetupManager(MainView view) {
    this.view = view;
    init();
  }

  private void init() {
    loadCustomFont();

    VAFND vafnd = VAFND.getInstance();
    AFND afnd = new AFND();
    me.hikingcarrot7.afnd.view.components.Menu menu = new me.hikingcarrot7.afnd.view.components.Menu(vafnd);

    AFNDController afndController = new AFNDController(view, afnd, vafnd, menu);
    EventHandler eventHandler = EventHandler.getInstance();

    vafnd.addKeyListener(eventHandler);
    vafnd.addMouseListener(eventHandler);
    vafnd.addMouseMotionListener(eventHandler);

    afndController.addBinding(Menu.BUTTON_ADD_ESTADO_INICIAL, AddAFNDEstadoState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_ESTADO_NORMAL, AddAFNDEstadoState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_ESTADO_INICIAL_FINAL, AddAFNDEstadoState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_ESTADO_FINAL, AddAFNDEstadoState.getInstance());

    afndController.addBinding(Menu.BUTTON_MOVER_ESTADO, MoverAFNDEstadoState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_CONEXION_NORMAL, AddAFNDConexionNormalState.getInstance());
    afndController.addBinding(Menu.BUTTON_ELIMINAR_ESTADO, BorrarAFNDEstadoState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_CONEXION_BUCLE, AddAFNDConexionBucleState.getInstance());
    afndController.addBinding(Menu.BUTTON_ELIMINAR_CONEXION, BorrarAFNDConexionState.getInstance());
    afndController.addBinding(Menu.BUTTON_COMPROBAR_AUTOMATA, ComprobarAFNDState.getInstance());
    afndController.addBinding(Menu.BUTTON_COMPROBACION_PASOS_AUTOMATA, PasoAPasoAFNDState.getInstance());

    eventHandler.addObserver(afndController);

    vafnd.addComponent(menu, VAFND.MAX_LAYER);

    view.add(vafnd, BorderLayout.CENTER);
    view.validate();
  }

  private void loadCustomFont() {
    try {
      var localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
      InputStream inputStream = getClass().getResourceAsStream("/fonts/OpenSans-SemiBold.ttf");
      File targetFile = new File("font.tmp");
      Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
      localGraphicsEnvironment.registerFont(
          Font.createFont(Font.TRUETYPE_FONT, targetFile)
      );
      inputStream.close();
      targetFile.deleteOnExit();
    } catch (FontFormatException | IOException e) {
      e.printStackTrace();
    }
  }

}
