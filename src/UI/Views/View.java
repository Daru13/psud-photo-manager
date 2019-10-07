package UI.Views;

import UI.PhotoBrowserGUI;

public interface View {
    void install(PhotoBrowserGUI gui);
    void uninstall(PhotoBrowserGUI gui);
}
