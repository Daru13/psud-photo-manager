package UI.Views;

import Events.EventManager;
import UI.Components.PhotoFrame;
import UI.Events.PhotoChangeEvent;
import UI.MainWindow;
import UI.ToolBar;

import javax.swing.*;
import java.awt.*;

public class SinglePhotoView implements View {

    private final EventManager eventManager;

    private PhotoFrame photoFrame;

    public SinglePhotoView(EventManager eventManager) {
        this.eventManager = eventManager;

        photoFrame = new PhotoFrame();
    }

    private void installMainContent(MainWindow window) {
        // Put the photo component in a JPanel to center it
        JPanel photoFrameContainer = new JPanel(new GridBagLayout());
        photoFrameContainer.setBackground(Color.DARK_GRAY);
        photoFrameContainer.add(photoFrame);

        // Add the photo container into the scroll pane
        JScrollPane mainContainer = window.getMainContainer();
        mainContainer.setViewportView(photoFrameContainer);
        mainContainer.revalidate();
    }

    private void uninstallMainContent(MainWindow window) {
        JScrollPane mainContainer = window.getMainContainer();
        mainContainer.setViewportView(null);
    }

    private void installToolBar(MainWindow window) {
        ToolBar toolbar = window.getToolbar();
    }

    private void uninstallToolBar(MainWindow window) {

    }

    private void installAllEventHandlers() {
        eventManager.addHandler("photo:change", this::handlePhotoChangeEvent);
    }

    private void uninstallAllEventHandlers() {
        eventManager.removeHandler("photo:change", this::handlePhotoChangeEvent);
    }

    private void handlePhotoChangeEvent(PhotoChangeEvent event) {
        photoFrame.setPhoto(event.photo);

        Dimension photoSize = new Dimension(event.photo.getWidth(), event.photo.getHeight());
        photoFrame.setSize(photoFrame.getParent().getSize());
    }

    @Override
    public void install(MainWindow window) {
        installMainContent(window);
        installToolBar(window);

        installAllEventHandlers();
    }

    @Override
    public void uninstall(MainWindow window) {
        uninstallMainContent(window);
        uninstallToolBar(window);

        uninstallAllEventHandlers();
    }
}
