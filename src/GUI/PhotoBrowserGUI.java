package GUI;

import Events.EventManager;
import GUI.Components.MenuBar;
import GUI.Components.StatusBar;
import GUI.Components.ToolBar;
import Events.PhotoChangeEvent;
import GUI.Views.ViewManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The main component of the GUI.
 *
 * This is the entry point of the GUI of the application. It creates and manage the main window.
 * The different parts of the GUI are delegated to more specific classes.
 *
 * @see GUI.Views.View
 */
public class PhotoBrowserGUI {

    private final EventManager eventManager;
    private final ViewManager viewManager;

    private final JFrame window;
    private JScrollPane mainContainer;
    private GUI.Components.MenuBar menuBar;
    private ToolBar toolbar;
    private StatusBar statusBar;

    private boolean displayToolbar;
    private boolean displayStatusBar;


    public PhotoBrowserGUI(EventManager eventManager) {
        this.eventManager = eventManager;
        this.viewManager = new ViewManager(this, eventManager);

        window = new JFrame();
        createMainContainer();
        createMenuBar();
        createToolbar();
        createStatusBar();

        displayToolbar = true;
        displayStatusBar = true;

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
        window.setPreferredSize(new Dimension(1024, 680));

        // Default actions
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void showWindow() {
        window.pack();
        window.setLocationRelativeTo(null); // Center the window
        window.setVisible(true);
    }

    public JScrollPane getMainContainer() {
        return mainContainer;
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
        window.setJMenuBar(menuBar);
    }

    private void createToolbar() {
        toolbar = new ToolBar(eventManager);
        updateToolbarDisplay();
    }

    private void createStatusBar() {
        statusBar = new StatusBar(eventManager);
        updateStatusBarDisplay();
    }

    public boolean isToolbarDisplayed() {
        return displayToolbar;
    }

    public boolean isStatusBarDisplayed() {
        return displayStatusBar;
    }

    public void setToolbarDisplay(boolean displayToolbar) {
        this.displayToolbar = displayToolbar;
        updateToolbarDisplay();
    }

    public void setStatusBarDisplay(boolean displayStatusBar) {
        this.displayStatusBar = displayStatusBar;
        updateStatusBarDisplay();
    }

    private void updateToolbarDisplay() {
        window.remove(toolbar);

        if (displayToolbar) {
            window.add(toolbar, BorderLayout.WEST);
        }

        window.revalidate();
    }

    private void updateStatusBarDisplay() {
        window.remove(statusBar);

        if (displayStatusBar) {
            window.add(statusBar, BorderLayout.SOUTH);
        }

        window.revalidate();
    }

    private void openImportFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        FileFilter imageFileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }

                String regex = "(.*)\\.(jpg|jpeg|png|bmp|gif)";
                return file.getPath().toLowerCase().matches(regex);
            }

            @Override
            public String getDescription() {
                return "Image files (jpg, png, bmp, gif)";
            }
        };

        fileChooser.setFileFilter(imageFileFilter);
        fileChooser.showOpenDialog(window);

        try {
            File file = fileChooser.getSelectedFile();
            BufferedImage photo = ImageIO.read(file);

            if (photo != null) {
                eventManager.emit(new PhotoChangeEvent(photo));
                eventManager.emit("file:import:end");
            } else {
                System.err.println("Error: the file could not be decoded as an image.");
                JOptionPane.showMessageDialog(null,
                        "Error while decoding the file as an image.",
                        "Import failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (IOException exception) {
            System.err.println("Error: the file could not be read correctly.");
            JOptionPane.showMessageDialog(null,
                    "Error while reading the content of the file.",
                    "Import failed",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalArgumentException exception) {
            // Ignore this type of exception here

        }
    }

    private void addDefaultEventHandlers() {
        eventManager.addHandler("file:import:begin", (e) -> openImportFileChooser());
    }
}
