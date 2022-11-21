package com.cherrysoft.afnd.bootstrapper;

import com.cherrysoft.afnd.controller.AutomataController;
import com.cherrysoft.afnd.controller.events.EventHandler;
import com.cherrysoft.afnd.core.states.imp.*;
import com.cherrysoft.afnd.view.MainView;
import com.cherrysoft.afnd.view.components.Menu;
import com.cherrysoft.afnd.view.components.afnd.AutomataPanel;

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

    AutomataPanel panel = AutomataPanel.getInstance();
    com.cherrysoft.afnd.view.components.Menu menu = new com.cherrysoft.afnd.view.components.Menu(panel);

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
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.ADD_INITIAL_STATE_BUTTON, AddingNodeState.getInstance());
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.ADD_NORMAL_STATE_BUTTON, AddingNodeState.getInstance());
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.ADD_INITIAL_FINAL_STATE_BUTTON, AddingNodeState.getInstance());
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.ADD_FINAL_STATE_BUTTON, AddingNodeState.getInstance());

    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.MOVE_STATE_BUTTON, MovingNodeState.getInstance());
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.ADD_NORMAL_CONNECTION_BUTTON, AddingNormalConnectionState.getInstance());
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.DELETE_STATE_BUTTON, DeletingNodeState.getInstance());
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.ADD_LOOP_CONNECTION_BUTTON, AddingLoopConnectionState.getInstance());
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.DELETE_CONNECTION_BUTTON, DeletingConnectionState.getInstance());
    automataController.addBinding(com.cherrysoft.afnd.view.components.Menu.VERIFY_AUTOMATA_BUTTON, VerifyingInputState.getInstance());
    automataController.addBinding(Menu.STEP_VERIFICATION_BUTTON, ResolvingStepByStepState.getInstance());
  }

}
