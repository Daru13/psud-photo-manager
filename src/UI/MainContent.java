package UI;

import Events.EventManager;

import javax.swing.*;

/**
 * The main content of the GUI.
 * It can be very different depending on the current view.
 */
public class MainContent {
    private final EventManager eventManager;
    private final JPanel container;

    MainContent(EventManager eventManager) {
        this.eventManager = eventManager;
        container = new JPanel();
    }

    public JComponent getContainer() {
        return container;
    }
}
