package UserInterface;

import Events.Event;
import Events.EventHandler;
import Events.EventManager;

import javax.swing.*;
import java.awt.*;

/**
 * The main component of the GUI.
 *
 * This is the entry point of the GUI of the application. It creates and manage the main window.
 * The different parts of the GUI are delegated to more specific classes, such as MenuBar.
 *
 * @see MenuBar
 * @see MainContent
 * @see ToolBar
 * @see StatusBar
 */
public class MainWindow {

    private JFrame window;
    private EventManager eventManager;

    private MenuBar menuBar;
    private MainContent mainContent;
    private ToolBar toolbar;
    private StatusBar statusBar;

    public MainWindow(EventManager eventManager) {
        this.eventManager = eventManager;

        window = new JFrame();
        configureWindow();

        createMenuBar();
        createMainContent();
        createToolbar();
        createStatusBar();

        showWindow();

        addDefaultEventHandlers();
    }

    private void configureWindow() {
        // Sizes
        window.setMinimumSize(new Dimension(640, 480));
        window.setPreferredSize(new Dimension(800, 600));

        // Initial position
        window.setLocationRelativeTo(null); // centred

        // Default actions
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void showWindow() {
        window.pack();
        window.setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new MenuBar(eventManager);
        window.setJMenuBar(menuBar.getContainer());
    }

    private void createMainContent() {
        mainContent = new MainContent(eventManager);
        window.add(mainContent.getContainer(), BorderLayout.CENTER);
    }

    private void createToolbar() {
        toolbar = new ToolBar(eventManager);
        window.add(toolbar.getContainer(), BorderLayout.WEST);
    }

    private void createStatusBar() {
        statusBar = new StatusBar(eventManager);
        window.add(statusBar.getContainer(), BorderLayout.SOUTH);
    }

    private void addDefaultEventHandlers() {
        // File import
        eventManager.addHandler("file:import:begin", new EventHandler<Event>() {
            public void handleEvent(Event e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(window);

                eventManager.emit("file:import:end");
            }

        });

    }
}
