package UI.Components;

import Events.EventManager;

import javax.swing.*;
import java.awt.*;

/**
 * The main toolbar of the GUI.
 * It offers options to control the displayed content, such as filters.
 */
public class ToolBar extends JScrollPane {
    private EventManager eventManager;

    private JPanel viewport;

    public ToolBar(EventManager eventManager) {
        super();

        this.eventManager = eventManager;
        viewport = new JPanel();

        configureScrollPane();
        configureViewport();
    }

    public JPanel getContainer() {
        return viewport;
    }

    private void configureScrollPane() {
        setViewportView(viewport);

        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


    }

    private void configureViewport() {
        viewport.setLayout(new BoxLayout(viewport, BoxLayout.PAGE_AXIS));
        //viewport.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        viewport.setBackground(new Color(0xDDDDDD));
        //viewport.setAlignmentX(Component.LEFT_ALIGNMENT);

        int maxWidth = 300;
        viewport.setMaximumSize(new Dimension(maxWidth, Integer.MAX_VALUE));
    }
}
