package UserInterface;

import Events.EventHandler;
import Events.EventManager;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;


enum MainContentView {
    PhotoDisplay
}


/**
 * The main content of the GUI.
 * It can be very different depending on the current view.
 */
class MainContent {
    private EventManager eventManager;
    private JScrollPane container;

    private EnumMap<MainContentView, JComponent> viewComponents;
    private MainContentView currentView;
    private JComponent currentViewComponent;

    MainContent(EventManager eventManager) {
        this.eventManager = eventManager;

        container = new JScrollPane();
        configureContainer();

        viewComponents = new EnumMap<MainContentView, JComponent>(MainContentView.class);
        initViews();
        setInitialView();

        addDefaultEventHandlers();
    }

    JScrollPane getContainer() {
        return container;
    }

    private void configureContainer() {
        container.setBackground(new Color(0xFFFFFF));
    }

    private void initViews() {
        // Photo display view
        JPanel centeringContainer = new JPanel();
        centeringContainer.setLayout(new GridBagLayout());
        centeringContainer.add(new PhotoDisplayComponent());

        viewComponents.put(MainContentView.PhotoDisplay, centeringContainer);
    }

    public void setView(MainContentView v) {
        if (viewComponents.containsKey(v)) {
            currentView = v;
            currentViewComponent = viewComponents.get(v);

            //container.removeAll();
            if (container.getParent() != null) {
                container.setSize(container.getParent().getSize());
            }

            container.setViewportView(currentViewComponent);
            container.revalidate();
            System.out.println(container.getSize());
        }
    }

    private void setInitialView() {
        setView(MainContentView.PhotoDisplay);
    }

    private void addDefaultEventHandlers() {
        // Photo change
        eventManager.addHandler("photo:change", new EventHandler<PhotoChangeEvent>() {
            public void handleEvent(PhotoChangeEvent e) {
                if (currentView == MainContentView.PhotoDisplay) {
                    // TODO: move into a class dedicated to the view?
                    ((PhotoDisplayComponent) currentViewComponent.getComponent(0)).setPhoto(e.photo);
                }
            }
        });

    }
}
