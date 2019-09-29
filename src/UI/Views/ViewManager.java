package UI.Views;

import Events.EventManager;
import UI.MainWindow;

import java.util.EnumMap;

enum ViewID {
    None,
    SinglePhoto,
    PhotoCollection
}

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

        initViews();
    }

    private void initViews() {
        views.put(ViewID.SinglePhoto, new SinglePhotoView(eventManager));
        views.put(ViewID.PhotoCollection, new PhotoCollectionView(eventManager));
    }

    private void setView(ViewID id) {
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

    public void setInitialView() {
        setView(ViewID.SinglePhoto);
    }
}
