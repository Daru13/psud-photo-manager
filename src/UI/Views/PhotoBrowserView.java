package UI.Views;

import Events.EventManager;
import UI.Events.CategoryFilterChangeEvent;
import UI.MainWindow;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class PhotoBrowserView implements View {

    private final EventManager eventManager;

    public PhotoBrowserView(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    private void installToolBar(MainWindow window) {
        JComponent container = window.getToolbar().getContainer();

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
        }
    }

    private void uninstallToolBar(MainWindow window) {
        JComponent container = window.getToolbar().getContainer();
        container.removeAll();
    }

    @Override
    public void install(MainWindow window) {
        installToolBar(window);
    }

    @Override
    public void uninstall(MainWindow window) {
        uninstallToolBar(window);
    }
}
