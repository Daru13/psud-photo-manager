import Events.EventManager;
import UI.PhotoBrowserGUI;

import java.io.File;

/**
 * The entry point of the photo browser application.
 *
 * It is responsible for initiating the event manager and the GUI,
 * as well as for very general actions (such as closing the app).
 * More specific actions are delegated to other classes.
 */
public class PhotoBrowser {

    private EventManager eventManager;
    private PhotoBrowserGUI mainWindow;

    private PhotoBrowser() {
        eventManager = new EventManager();
        mainWindow = new PhotoBrowserGUI(eventManager);

        addDefaultEventHandlers();
    }

    private void exit() {
        System.exit(0);
    }

    private void importFile(File f) {
        // TODO
    }

    private void addDefaultEventHandlers() {
        // Exit the app
        eventManager.addHandler("app:exit", (e) -> exit());
    }

    public static void main(String[] args) {
        PhotoBrowser mainInstance = new PhotoBrowser();
    }
}
