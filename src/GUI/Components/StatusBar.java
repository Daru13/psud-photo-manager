package GUI.Components;

import Events.CategoryFilterChangeEvent;
import Events.EventManager;
import Events.ToolChangeEvent;
import Events.ViewChangeEvent;

import javax.swing.*;
import java.awt.*;

/**
 * The main status bar of the GUI.
 * It displays informative messages about the current action or tool.
 */
public class StatusBar extends JPanel {

    private EventManager eventManager;
    private JLabel statusLabel;


    public StatusBar(EventManager eventManager) {
        super();

        this.eventManager = eventManager;

        configureContainer();

        createStatusLabel();
        addStatusUpdateEventHandler();
    }

    private void configureContainer() {
        setBackground(new Color(0xCCCCCC));
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

        eventManager.addHandler("filter:category:change", (CategoryFilterChangeEvent e) ->
                statusLabel.setText("Category " + e.category + " is now " + (e.isEnabled ? "visible." : "hidden."))
        );

        eventManager.addHandler("tool:change",
                (ToolChangeEvent e) -> statusLabel.setText("Changed tool to " + e.toolID.getName() + ".")
        );
    }

    private void createStatusLabel() {
        statusLabel = new JLabel(" ");
        add(statusLabel);
    }
}
