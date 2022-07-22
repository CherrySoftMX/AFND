package me.hikingcarrot7.afnd.model.states;

import me.hikingcarrot7.afnd.model.automata.AFND;
import me.hikingcarrot7.afnd.view.components.automata.VAFND;

import java.awt.event.InputEvent;

/**
 *
 * @author HikingCarrot7
 */
public class AFNDStateManager {

    private AFNDState currentState;
    private final AFND afnd;

    public AFNDStateManager(AFND afnd) {
        this.afnd = afnd;
        currentState = IdleState.getInstance();
    }

    public void dispatchInputEvent(int buttonID, InputEvent event) {
        currentState.updateGraphState(afnd, VAFND.getInstance(), this, event, buttonID);
    }

    public void exitCurrentState() {
        currentState.exitState(afnd, VAFND.getInstance(), this);
    }

    public void setCurrentState(AFNDState newState) {
        currentState = newState;
    }

}
