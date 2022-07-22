package me.hikingcarrot7.afnd.controller.events;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;

/**
 *
 * @author HikingCarrot7
 */
public class EventHandler extends Observable implements MouseListener, MouseMotionListener, KeyListener {

    private static EventHandler instance;

    public static EventHandler getInstance() {
        if (instance == null)
            instance = new EventHandler();

        return instance;
    }

    private EventHandler() {
    }

    @Override public void mouseClicked(MouseEvent e) {
        notifyEvent(e);
    }

    @Override public void mousePressed(MouseEvent e) {
        notifyEvent(e);
    }

    @Override public void mouseReleased(MouseEvent e) {
        notifyEvent(e);
    }

    @Override public void mouseEntered(MouseEvent e) {
        notifyEvent(e);
    }

    @Override public void mouseExited(MouseEvent e) {
        notifyEvent(e);
    }

    @Override public void mouseDragged(MouseEvent e) {
        notifyEvent(e);
    }

    @Override public void mouseMoved(MouseEvent e) {
        notifyEvent(e);
    }

    @Override public void keyTyped(KeyEvent e) {
        notifyEvent(e);
    }

    @Override public void keyPressed(KeyEvent e) {
        notifyEvent(e);
    }

    @Override public void keyReleased(KeyEvent e) {
        notifyEvent(e);
    }

    private void notifyEvent(InputEvent event) {
        setChanged();
        notifyObservers(event);
    }

}
