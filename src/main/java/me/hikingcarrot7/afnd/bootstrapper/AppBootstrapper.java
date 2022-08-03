package me.hikingcarrot7.afnd.bootstrapper;

import me.hikingcarrot7.afnd.controller.AutomataController;
import me.hikingcarrot7.afnd.controller.events.EventHandler;
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

public class AppBootstrapper {
  private final MainView view;

  public AppBootstrapper(MainView view) {
    this.view = view;
    init();
  }

  private void init() {
    loadCustomFont();

    AFNDPanel panel = AFNDPanel.getInstance();
    Menu menu = new Menu(panel);

    AutomataController automataController = new AutomataController(view, panel, menu);
    EventHandler eventHandler = EventHandler.getInstance();

    panel.addKeyListener(eventHandler);
    panel.addMouseListener(eventHandler);
    panel.addMouseMotionListener(eventHandler);

    addBindings(automataController);

    eventHandler.addObserver(automataController);

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

  private void addBindings(AutomataController automataController) {
    automataController.addBinding(Menu.BUTTON_ADD_ESTADO_INICIAL, AddingNodeState.getInstance());
    automataController.addBinding(Menu.BUTTON_ADD_ESTADO_NORMAL, AddingNodeState.getInstance());
    automataController.addBinding(Menu.BUTTON_ADD_ESTADO_INICIAL_FINAL, AddingNodeState.getInstance());
    automataController.addBinding(Menu.BUTTON_ADD_ESTADO_FINAL, AddingNodeState.getInstance());

    automataController.addBinding(Menu.BUTTON_MOVER_ESTADO, MovingNodeState.getInstance());
    automataController.addBinding(Menu.BUTTON_ADD_CONEXION_NORMAL, AddingNormalConnectionState.getInstance());
    automataController.addBinding(Menu.BUTTON_ELIMINAR_ESTADO, DeletingNodeState.getInstance());
    automataController.addBinding(Menu.BUTTON_ADD_CONEXION_BUCLE, AddingLoopConnectionState.getInstance());
    automataController.addBinding(Menu.BUTTON_ELIMINAR_CONEXION, DeletingConnectionState.getInstance());
    automataController.addBinding(Menu.BUTTON_COMPROBAR_AUTOMATA, VerifyingInputState.getInstance());
    automataController.addBinding(Menu.BUTTON_COMPROBACION_PASOS_AUTOMATA, ResolvingStepByStepState.getInstance());
  }

}
