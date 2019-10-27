package GUI.Views;

import Events.EventHandler;
import Events.EventManager;
import Events.ViewChangeEvent;
import GUI.PhotoBrowserGUI;

import java.util.EnumMap;


/**
 * The manager of the views.
 *
 * It is responsible for creating, installing and uninstalling views.
 *
 * @see View
 */
public class ViewManager {

    private final PhotoBrowserGUI gui;
    private final EventManager eventManager;

    private final EnumMap<ViewID, View> views;
    private ViewID currentViewID;
    private View currentView;


    public ViewManager(PhotoBrowserGUI gui, EventManager eventManager) {
        this.gui = gui;
        this.eventManager = eventManager;

        views = new EnumMap<>(ViewID.class);
        currentViewID = ViewID.None;
        currentView = null;

        addDefaultEventHandlers();
        initViews();
    }

    private void initViews() {
        views.put(ViewID.SinglePhoto, new SinglePhotoView(gui, eventManager));
        views.put(ViewID.PhotoBrowser, new PhotoBrowserView(gui, eventManager));
    }

    private void switchToView(ViewID id) {
        if (currentViewID == id) {
            return;
        }

        if (currentViewID != ViewID.None) {
            currentView.uninstall();
        }

        currentView = views.get(id);
        currentViewID = id;

        currentView.install();
    }

    public void switchToInitialView() {
        switchToView(ViewID.SinglePhoto);
    }

    private void addDefaultEventHandlers() {
        eventManager.addHandler("view:change", new EventHandler<ViewChangeEvent>() {
            @Override
            public void handleEvent(ViewChangeEvent e) {
                switchToView(e.nextView);
            }
        });
    }
}
