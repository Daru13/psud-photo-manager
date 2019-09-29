package UI;

import Events.EventHandler;
import Events.EventManager;
import UI.Events.CategoryFilterChangeEvent;
import UI.Events.ToolChangeEvent;
import UI.Events.ViewChangeEvent;

import javax.swing.*;
import java.awt.*;

/**
 * The main status bar of the GUI.
 * It displays informative messages about the current action or tool.
 */
class StatusBar {
    private EventManager eventManager;
    private JPanel container;

    private JLabel statusLabel;

    JPanel getContainer() {
        return container;
    }

    StatusBar(EventManager eventManager) {
        this.eventManager = eventManager;

        container = new JPanel();
        configureContainer();

        createStatusLabel();
        addStatusUpdateEventHandler();
    }

    private void configureContainer() {
        container.setBackground(new Color(0xCCCCCC));
    }

    private void addStatusUpdateEventHandler() {
        eventManager.addHandler("file:import:end",
                (e) -> statusLabel.setText("File imported.")
        );

        eventManager.addHandler("file:delete",
                (e) -> statusLabel.setText("File deleted.")
        );

        eventManager.addHandler("view:change",
                (ViewChangeEvent e) -> statusLabel.setText("Changed view to " + e.nextView + ".")
        );

        eventManager.addHandler("filter:category:change", new EventHandler<CategoryFilterChangeEvent>() {
                    @Override
                    public void handleEvent(CategoryFilterChangeEvent e) {
                        statusLabel.setText("Category " + e.category + " is now " +
                                            (e.isEnabled ? "visible." : "hidden."));
                    }
                }
        );

        eventManager.addHandler("tool:change",
                (ToolChangeEvent e) -> statusLabel.setText("Changed tool to " + e.toolID.getName() + ".")
        );;
    }

    private void createStatusLabel() {
        statusLabel = new JLabel(" ");
        container.add(statusLabel);
    }
}
