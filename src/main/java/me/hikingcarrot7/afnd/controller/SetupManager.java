package me.hikingcarrot7.afnd.controller;

import me.hikingcarrot7.afnd.controller.events.EventHandler;
import me.hikingcarrot7.afnd.core.afnd.AFNDGraph;
import me.hikingcarrot7.afnd.core.states.imp.*;
import me.hikingcarrot7.afnd.view.MainView;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.afnd.AFNDPanel;

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

    AFNDPanel panel = AFNDPanel.getInstance();
    AFNDGraph afndGraph = new AFNDGraph();
    Menu menu = new Menu(panel);

    AFNDController afndController = new AFNDController(view, afndGraph, panel, menu);
    EventHandler eventHandler = EventHandler.getInstance();

    panel.addKeyListener(eventHandler);
    panel.addMouseListener(eventHandler);
    panel.addMouseMotionListener(eventHandler);

    afndController.addBinding(Menu.BUTTON_ADD_ESTADO_INICIAL, AddingNodeState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_ESTADO_NORMAL, AddingNodeState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_ESTADO_INICIAL_FINAL, AddingNodeState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_ESTADO_FINAL, AddingNodeState.getInstance());

    afndController.addBinding(Menu.BUTTON_MOVER_ESTADO, MovingNodeState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_CONEXION_NORMAL, AddingConnectionState.getInstance());
    afndController.addBinding(Menu.BUTTON_ELIMINAR_ESTADO, DeletingNodeState.getInstance());
    afndController.addBinding(Menu.BUTTON_ADD_CONEXION_BUCLE, AddingLoopConnectionState.getInstance());
    afndController.addBinding(Menu.BUTTON_ELIMINAR_CONEXION, DeletingConnectionState.getInstance());
    afndController.addBinding(Menu.BUTTON_COMPROBAR_AUTOMATA, VerifyingInputState.getInstance());
    afndController.addBinding(Menu.BUTTON_COMPROBACION_PASOS_AUTOMATA, ResolvingStepByStepState.getInstance());

    eventHandler.addObserver(afndController);

    panel.addComponent(menu);

    view.add(panel, BorderLayout.CENTER);
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
