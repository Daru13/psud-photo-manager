package GUI.Views;

import Events.Event;
import Events.EventManager;
import Events.PhotoChangeEvent;
import Events.ToolChangeEvent;
import GUI.Components.PhotoFrame;
import GUI.Components.ToolPanel;
import GUI.Components.ToolSettingPanel;
import GUI.PhotoBrowserGUI;

import javax.swing.*;
import java.awt.*;


/**
 * View displaying a single photo.
 *
 * @see View
 */
public class SinglePhotoView implements View {

    private final EventManager eventManager;
    private PhotoFrame photoFrame;


    SinglePhotoView(EventManager eventManager) {
        this.eventManager = eventManager;

        photoFrame = new PhotoFrame();
    }

    private void installMainContent(PhotoBrowserGUI gui) {
        // Put the photo component in a JPanel to center it
        JPanel photoFrameContainer = new JPanel(new GridBagLayout());
        photoFrameContainer.setBackground(Color.DARK_GRAY);
        photoFrameContainer.add(photoFrame);

        // Add the photo container into the scroll pane
        JScrollPane mainContainer = gui.getMainContainer();
        mainContainer.setViewportView(photoFrameContainer);
        mainContainer.revalidate();
    }

    private void uninstallMainContent(PhotoBrowserGUI gui) {
        JScrollPane mainContainer = gui.getMainContainer();
        mainContainer.setViewportView(null);
    }

    private void installToolBar(PhotoBrowserGUI gui) {
        installToolBarTools(gui);
        installToolBarToolSettings(gui);
    }

    private void installToolBarTools(PhotoBrowserGUI gui) {
        JComponent container = gui.getToolbar().getContainer();
        container.add(new ToolPanel(photoFrame, eventManager));
    }

    private void installToolBarToolSettings(PhotoBrowserGUI gui) {
        JComponent container = gui.getToolbar().getContainer();
        container.add(new ToolSettingPanel(photoFrame));
    }


    private void uninstallToolBar(PhotoBrowserGUI gui) {
        JComponent container = gui.getToolbar().getContainer();
        container.removeAll();
    }

    private void installAllEventHandlers() {
        eventManager.addHandler("photo:change", this::handlePhotoChangeEvent);
        eventManager.addHandler("file:delete", this::handlePhotoDeleteEvent);
        eventManager.addHandler("tool:change", this::handleToolChangeEvent);
    }

    private void uninstallAllEventHandlers() {
        eventManager.removeHandler("photo:change", this::handlePhotoChangeEvent);
        eventManager.removeHandler("file:delete", this::handlePhotoDeleteEvent);
        eventManager.removeHandler("tool:change", this::handleToolChangeEvent);

    }

    private void handlePhotoChangeEvent(PhotoChangeEvent event) {
        photoFrame.setPhoto(event.photo);
        photoFrame.setSize(photoFrame.getParent().getSize());
    }

    private void handlePhotoDeleteEvent(Event event) {
        photoFrame.removePhoto();
    }

    private void handleToolChangeEvent(ToolChangeEvent event) {
        photoFrame.setTool(event.toolID);
    }

    @Override
    public void install(PhotoBrowserGUI gui) {
        installMainContent(gui);
        installToolBar(gui);

        installAllEventHandlers();
    }

    @Override
    public void uninstall(PhotoBrowserGUI gui) {
        uninstallMainContent(gui);
        uninstallToolBar(gui);

        uninstallAllEventHandlers();
    }
}
