package GUI.Views;

import GUI.PhotoBrowserGUI;

public interface View {
    void install(PhotoBrowserGUI gui);
    void uninstall(PhotoBrowserGUI gui);
}
