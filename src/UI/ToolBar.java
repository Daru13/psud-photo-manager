package UI;

import Events.EventManager;
import UI.Events.CategoryFilterChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * The main toolbar of the GUI.
 * It offers options to control the displayed content, such as filters.
 */
class ToolBar {
    private EventManager eventManager;
    private JToolBar container;

    JToolBar getContainer() {
        return container;
    }

    ToolBar(EventManager eventManager) {
        this.eventManager = eventManager;

        container = new JToolBar();
        configureContainer();

        createFilterByCategory();
    }

    private void configureContainer() {
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        container.setBackground(new Color(0xDDDDDD));
        container.setFloatable(false);
    }

    private void createFilterByCategory() {
        JLabel photoCategoryTitle = new JLabel("Categories:");
        photoCategoryTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        container.add(photoCategoryTitle);

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
}
