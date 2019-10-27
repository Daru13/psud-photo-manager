package GUI.Views;

import Events.*;
import Events.Event;
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

    private final PhotoBrowserGUI gui;
    private final EventManager eventManager;
    private PhotoFrame photoFrame;


    SinglePhotoView(PhotoBrowserGUI gui, EventManager eventManager) {
        this.gui = gui;
        this.eventManager = eventManager;

        photoFrame = new PhotoFrame(eventManager);
    }

    private void installMainContent() {
        // Put the photo component in a JPanel to center it
        JPanel photoFrameContainer = new JPanel(new GridBagLayout());
        photoFrameContainer.setBackground(Color.DARK_GRAY);
        photoFrameContainer.add(photoFrame);

        // Add the photo container into the scroll pane
        JScrollPane mainContainer = gui.getMainContainer();
        mainContainer.setViewportView(photoFrameContainer);
        mainContainer.revalidate();
    }

    private void uninstallMainContent() {
        JScrollPane mainContainer = gui.getMainContainer();
        mainContainer.setViewportView(null);
    }

    private void installToolBar() {
        installToolBarTools();
        installToolBarToolSettings();

        gui.setToolbarDisplay(photoFrame.isAnnotable());
    }

    private void installToolBarTools() {
        JComponent container = gui.getToolbar().getContainer();
        container.add(new ToolPanel(photoFrame, eventManager));
    }

    private void installToolBarToolSettings() {
        JComponent container = gui.getToolbar().getContainer();
        container.add(new ToolSettingPanel(photoFrame));
    }


    private void uninstallToolBar() {
        gui.setToolbarDisplay(true);

        JComponent container = gui.getToolbar().getContainer();
        container.removeAll();

    }

    private void installAllEventHandlers() {
        eventManager.addHandler("photo:change", this::handlePhotoChangeEvent);
        eventManager.addHandler("photo:annotable:change", this::handleAnnotableStateChangeEvent);
        eventManager.addHandler("file:delete", this::handlePhotoDeleteEvent);
        eventManager.addHandler("tool:change", this::handleToolChangeEvent);
    }

    private void uninstallAllEventHandlers() {
        eventManager.removeHandler("photo:change", this::handlePhotoChangeEvent);
        eventManager.removeHandler("photo:annotable:change", this::handleAnnotableStateChangeEvent);
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

    private void handleAnnotableStateChangeEvent(AnnotableStateChangeEvent event) {
        gui.setToolbarDisplay(event.isAnnotable);
        System.out.println("is annotable = " + event.isAnnotable);
    }

    @Override
    public void install() {
        installMainContent();
        installToolBar();

        installAllEventHandlers();
    }

    @Override
    public void uninstall() {
        uninstallMainContent();
        uninstallToolBar();

        uninstallAllEventHandlers();
    }
}
