package UI.Views;

import Events.EventManager;
import UI.MainWindow;

public class PhotoCollectionView implements View {

    private final EventManager eventManager;

    public PhotoCollectionView(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void install(MainWindow window) {

    }

    @Override
    public void uninstall(MainWindow window) {

    }
}
