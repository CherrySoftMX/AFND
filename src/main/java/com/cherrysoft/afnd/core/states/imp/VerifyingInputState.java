package com.cherrysoft.afnd.core.states.imp;

import com.cherrysoft.afnd.core.automata.MatchResult;
import com.cherrysoft.afnd.core.automata.MatchResultStep;
import com.cherrysoft.afnd.core.states.AutomataState;
import com.cherrysoft.afnd.view.components.Menu;
import com.cherrysoft.afnd.view.components.TextBox;
import com.cherrysoft.afnd.view.components.Triangle;
import com.cherrysoft.afnd.view.components.afnd.AutomataPanel;
import com.cherrysoft.afnd.view.components.afnd.VisualConnection;
import com.cherrysoft.afnd.view.components.afnd.VisualNode;
import com.cherrysoft.afnd.view.graphics.Box;

public class VerifyingInputState extends AutomataState {
  private static VerifyingInputState instance;
  private final TextBox messageBox;
  private boolean inputTested;
  private MatchResult result;
  private VerifyingInputState() {
    messageBox = new TextBox.TextBoxBuilder()
        .setBoxPosition(Box.BoxPosition.TOP_LEFT)
        .build();
  }

  public synchronized static VerifyingInputState getInstance() {
    if (instance == null) {
      instance = new VerifyingInputState();
    }
    return instance;
  }

  @Override
  public void updateGraphState() {
    if (!inputTested) {
      testInput();
    }
    panel.repaint();
  }

  private void testInput() {
    String text = Menu.TEXT_FIELD.getText();
    try {
      result = visualAutomata.matches(text);
      panel.addComponent(messageBox);
      if (result.matches()) {
        messageBox.setTitle("Input ACCEPTED by the automata");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        markPath();
      } else {
        messageBox.setTitle("Input REJECTED by the automata");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
      }
    } catch (IllegalStateException e) {
      panel.textBox().setTitle(e.getMessage());
      panel.repaint();
    }
    inputTested = true;
  }

  private void markPath() {
    for (MatchResultStep step : result.getPath()) {
      VisualConnection connection = (VisualConnection) step.getConnection();
      connection.getOrigin().setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      connection.getDestination().setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      connection.getTriangle().setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      connection.setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
    }
  }

  @Override
  public void clearState() {
    visualAutomata.forEachVisualNode(node -> node.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE));
    visualAutomata.forEachVisualConnection(conn -> {
      conn.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      conn.getTriangle().setColorPalette(Triangle.CONNECTION_TRIANGLE_COLOR_PALETTE);
      conn.setLayer(AutomataPanel.MIN_LAYER);
    });
    inputTested = false;
    panel.removeComponent(messageBox);
    super.clearState();
  }

}
