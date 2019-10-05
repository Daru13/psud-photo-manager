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

    private JScrollPane scrollPane;
    private JPanel innerContainer;

    ToolBar(EventManager eventManager) {
        this.eventManager = eventManager;
        scrollPane = new JScrollPane();
        innerContainer = new JPanel();

        configureScrollPane();
        configureInnerContainer();
    }

    public JScrollPane getToolbar() {
        return scrollPane;
    }

    public JPanel getContainer() {
        return innerContainer;
    }

    private void configureScrollPane() {
        scrollPane.setViewportView(innerContainer);

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


    }

    private void configureInnerContainer() {
        innerContainer.setLayout(new BoxLayout(innerContainer, BoxLayout.PAGE_AXIS));
        //innerContainer.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        innerContainer.setBackground(new Color(0xDDDDDD));
        //innerContainer.setAlignmentX(Component.LEFT_ALIGNMENT);

        int maxWidth = Math.min(250, innerContainer.getWidth() / 4);
        innerContainer.setMaximumSize(new Dimension(maxWidth, Integer.MAX_VALUE));
    }
}
