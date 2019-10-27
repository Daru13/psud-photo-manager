package Views;


/**
 * Interface of a view.
 *
 * A view represents a logical part of the GUI, which should be associated with precise goals.
 * It can adapt several components of the GUI to offer specific features to the user.
 *
 * It requires two methods, which are lifecycle callbacks.
 * They should be used to perform certain actions before or after the view is displayed.
 */
public interface View {
    void install(PhotoBrowserGUI gui);
    void uninstall(PhotoBrowserGUI gui);
}
