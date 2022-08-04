package me.hikingcarrot7.afnd.core.states.imp;

import me.hikingcarrot7.afnd.core.automata.MatchResult;
import me.hikingcarrot7.afnd.core.automata.MatchResultStep;
import me.hikingcarrot7.afnd.core.states.AutomataState;
import me.hikingcarrot7.afnd.view.components.Menu;
import me.hikingcarrot7.afnd.view.components.TextBox;
import me.hikingcarrot7.afnd.view.components.Triangle;
import me.hikingcarrot7.afnd.view.components.afnd.AutomataPanel;
import me.hikingcarrot7.afnd.view.components.afnd.VisualConnection;
import me.hikingcarrot7.afnd.view.components.afnd.VisualNode;
import me.hikingcarrot7.afnd.view.graphics.Box;

import java.util.Iterator;

import static java.util.Objects.isNull;

public class ResolvingStepByStepState extends AutomataState {
  private static ResolvingStepByStepState instance;

  public synchronized static ResolvingStepByStepState getInstance() {
    if (instance == null) {
      instance = new ResolvingStepByStepState();
    }
    return instance;
  }

  private boolean inputTested = false;
  private boolean inputMatched = false;
  private final TextBox messageBox;
  private Iterator<MatchResultStep> pathIterator;
  private MatchResult result;

  private ResolvingStepByStepState() {
    messageBox = new TextBox.TextBoxBuilder()
        .setBoxPosition(Box.BoxPosition.TOP_LEFT)
        .build();
  }

  @Override
  public void updateGraphState() {
    if (isMouseClicked()) {
      if (inputMatched) {
        markNextStep();
      } else {
        testInput();
      }
    }
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
        messageBox.setTitle("Input ACCEPTED, press on the automaton to start the tour");
        messageBox.setColorPalette(TextBox.GREEN_TEXTBOX_COLOR_PALETTE);
        inputMatched = true;
      } else {
        messageBox.setTitle("Input REJECTED, the tour cannot start");
        messageBox.setColorPalette(TextBox.RED_TEXTBOX_COLOR_PALETTE);
        inputMatched = false;
      }
      inputTested = true;
    } catch (IllegalStateException e) {
      panel.textBox().setTitle(e.getMessage());
      inputTested = true;
    }
  }

  private void markNextStep() {
    if (isNull(pathIterator)) {
      pathIterator = result.pathIterator();
    }
    if (pathIterator.hasNext()) {
      clearAllMarks();
      MatchResultStep step = pathIterator.next();
      VisualConnection connection = (VisualConnection) step.getConnection();
      connection.getOrigin().setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      connection.getDestination().setColorPalette(VisualNode.SELECTED_PATH_NODE_COLOR_PALETTE);
      connection.getTriangle().setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      connection.setColorPalette(VisualConnection.SELECTED_CONNECTION_COLOR_PALETTE);
      messageBox.setTitle("Input left: " + step.inputSnapshot());
    } else {
      clearAllMarks();
      messageBox.setTitle("The tour has ended");
    }
    panel.repaint();
  }

  private void clearAllMarks() {
    visualAutomata.forEachVisualNode(node -> node.setColorPalette(VisualNode.DEFAULT_NODE_COLOR_PALETTE));
    visualAutomata.forEachVisualConnection(conn -> {
      conn.setColorPalette(VisualConnection.DEFAULT_CONNECTION_COLOR_PALETTE);
      conn.getTriangle().setColorPalette(Triangle.CONNECTION_TRIANGLE_COLOR_PALETTE);
      conn.setLayer(AutomataPanel.MIN_LAYER);
    });
  }

  @Override
  public void clearState() {
    inputTested = false;
    inputMatched = false;
    pathIterator = null;
    panel.removeComponent(messageBox);
    clearAllMarks();
    super.clearState();
  }

}
