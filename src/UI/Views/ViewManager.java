package UI.Views;

import Events.EventHandler;
import Events.EventManager;
import UI.Events.ViewChangeEvent;
import UI.MainWindow;

import java.util.EnumMap;

public class ViewManager {

    private final MainWindow window;
    private final EventManager eventManager;

    private final EnumMap<ViewID, View> views;
    private ViewID currentViewID;
    private View currentView;

    public ViewManager(MainWindow window, EventManager eventManager) {
        this.window = window;
        this.eventManager = eventManager;

        views = new EnumMap<>(ViewID.class);
        currentViewID = ViewID.None;
        currentView = null;

        addDefaultEventHandlers();
        initViews();
    }

    private void initViews() {
        views.put(ViewID.SinglePhoto, new SinglePhotoView(eventManager));
        views.put(ViewID.PhotoBrowser, new PhotoBrowserView(eventManager));
    }

    private void switchToView(ViewID id) {
        if (currentViewID == id) {
            return;
        }

        if (currentViewID != ViewID.None) {
            currentView.uninstall(window);
        }

        currentView = views.get(id);
        currentViewID = id;

        currentView.install(window);
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
