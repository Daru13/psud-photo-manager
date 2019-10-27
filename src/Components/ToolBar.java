package Components;

import Events.EventManager;

import javax.swing.*;
import java.awt.*;


/**
 * The main toolbar of the GUI.
 *
 * It offers options to control and modify the displayed content, such as tools and filters.
 */
public class ToolBar extends JScrollPane {

    private final static int MAX_WIDTH = 300; // px
    private final static Color BACKGROUND_COLOR = new Color(0xDDDDDD);

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
        viewport.setBackground(BACKGROUND_COLOR);

        viewport.setMaximumSize(new Dimension(MAX_WIDTH, Integer.MAX_VALUE));
    }
}
