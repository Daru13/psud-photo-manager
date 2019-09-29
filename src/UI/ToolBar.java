package UI;

import Events.EventManager;

import javax.swing.*;
import java.awt.*;

/**
 * The main toolbar of the GUI.
 * It offers options to control the displayed content, such as filters.
 */
public class ToolBar {
    private EventManager eventManager;
    private JToolBar container;

    ToolBar(EventManager eventManager) {
        this.eventManager = eventManager;

        container = new JToolBar();
        configureContainer();
    }

    public JToolBar getContainer() {
        return container;
    }

    private void configureContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        container.setBackground(new Color(0xDDDDDD));
        container.setFloatable(false);

        int maxWidth = Math.max(400, container.getWidth() / 4);
        container.setMaximumSize(new Dimension(maxWidth, Integer.MAX_VALUE));
    }
}
