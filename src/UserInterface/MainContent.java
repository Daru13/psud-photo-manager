package UserInterface;

import Events.EventManager;

import javax.swing.*;
import java.awt.*;

/**
 * The main content of the GUI.
 * It can be very different depending on the current view.
 */
class MainContent {
    private EventManager eventManager;
    private JPanel container;

    JPanel getContainer() {
        return container;
    }

    MainContent(EventManager eventManager) {
        this.eventManager = eventManager;

        container = new JPanel();
        configureContainer();
    }

    private void configureContainer() {
        container.setBackground(new Color(0xFFFFFF));
    }
}
