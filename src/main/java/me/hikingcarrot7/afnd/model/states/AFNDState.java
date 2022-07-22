package me.hikingcarrot7.afnd.model.states;

import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;

import java.awt.event.InputEvent;

/**
 *
 * @author HikingCarrot7
 */
public interface AFNDState {

    public void updateGraphState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager, InputEvent event, int buttonID);

    public default void clearState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager) {
        vafnd.getDefaultTextBox().clearTextBox();
        vafnd.repaint();
    }

    public default void exitState(AFND<String> afnd, VAFND vafnd, AFNDStateManager afndStateManager) {
        clearState(afnd, vafnd, afndStateManager);
        afndStateManager.setCurrentState(IdleState.getInstance());
    }

}
