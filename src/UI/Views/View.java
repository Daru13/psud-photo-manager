package UI.Views;

import UI.MainWindow;

public interface View {
    void install(MainWindow window);
    void uninstall(MainWindow window);
}
