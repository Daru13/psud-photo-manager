package UI.Views;

import Events.EventManager;
import UI.MainWindow;

public class PhotoBrowserView implements View {

    private final EventManager eventManager;

    public PhotoBrowserView(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void install(MainWindow window) {

    }

    @Override
    public void uninstall(MainWindow window) {

    }
}
