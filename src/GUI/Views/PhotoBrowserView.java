package GUI.Views;

import Events.CategoryFilterChangeEvent;
import Events.EventManager;
import GUI.PhotoBrowserGUI;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;


/**
 * View displaying a collection of photos.
 *
 * @see View
 */
public class PhotoBrowserView implements View {

    private final PhotoBrowserGUI gui;
    private final EventManager eventManager;


    PhotoBrowserView(PhotoBrowserGUI gui, EventManager eventManager) {
        this.gui = gui;
        this.eventManager = eventManager;
    }

    private void installToolBar() {
        JComponent container = gui.getToolbar().getContainer();

        JLabel photoCategoriesLabel = new JLabel("Categories");
        photoCategoriesLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        container.add(photoCategoriesLabel);

        List<String> photoCategories = Arrays.asList(
                "People",
                "Parties",
                "Family",
                "Vacations",
                "Sports",
                "Landscapes"
        );

        for (String category : photoCategories) {
            JToggleButton categoryButton = new JCheckBox(category);
            categoryButton.setSelected(true);
            categoryButton.setOpaque(false);

            categoryButton.addActionListener(
                    (e) -> eventManager.emit(new CategoryFilterChangeEvent(category, categoryButton.isSelected()))
            );

            container.add(categoryButton);
            gui.setToolbarDisplay(true);
        }
    }

    private void uninstallToolBar() {
        JComponent container = gui.getToolbar().getContainer();
        container.removeAll();
    }

    @Override
    public void install() {
        installToolBar();
    }

    @Override
    public void uninstall() {
        uninstallToolBar();
    }
}
