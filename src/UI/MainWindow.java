package UI;

import Events.Event;
import Events.EventHandler;
import Events.EventManager;
import UI.Events.PhotoChangeEvent;
import UI.Views.ViewManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The main component of the GUI.
 *
 * This is the entry point of the GUI of the application. It creates and manage the main window.
 * The different parts of the GUI are delegated to more specific classes, such as MenuBar.
 *
 * @see MenuBar
 * @see ToolBar
 * @see StatusBar
 */
public class MainWindow {

    private final EventManager eventManager;
    private final ViewManager viewManager;

    private final JFrame window;
    private JScrollPane mainContainer;
    private MenuBar menuBar;
    private ToolBar toolbar;
    private StatusBar statusBar;

    public MainWindow(EventManager eventManager) {
        this.eventManager = eventManager;
        this.viewManager = new ViewManager(this, eventManager);

        window = new JFrame();
        createMainContainer();
        createMenuBar();
        createToolbar();
        createStatusBar();

        configureWindow();
        viewManager.switchToInitialView();
        addDefaultEventHandlers();

        showWindow();
    }

    private void configureWindow() {
        // Title
        window.setTitle("Photo browser");

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

    public JScrollPane getMainContainer() {
        return mainContainer;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public ToolBar getToolbar() {
        return toolbar;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    private void createMainContainer() {
        mainContainer = new JScrollPane();
        window.add(mainContainer, BorderLayout.CENTER);
    }

    private void createMenuBar() {
        menuBar = new MenuBar(eventManager);
        window.setJMenuBar(menuBar.getContainer());
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

                try {
                    File file = fileChooser.getSelectedFile();
                    BufferedImage photo = ImageIO.read(file);

                    eventManager.emit(new PhotoChangeEvent(photo));
                }
                catch (IOException exception) {
                    System.err.println("Error: the file could not be opened as an image.");
                    System.err.print(exception);
                }

            }
        });
    }
}
