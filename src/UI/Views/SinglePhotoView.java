package UI.Views;

import Events.Event;
import Events.EventManager;
import UI.Components.PhotoFrame;
import UI.Events.PhotoChangeEvent;
import UI.Events.ToolChangeEvent;
import UI.MainWindow;
import UI.Tools.ToolID;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class SinglePhotoView implements View {

    private final EventManager eventManager;
    private PhotoFrame photoFrame;

    public SinglePhotoView(EventManager eventManager) {
        this.eventManager = eventManager;

        photoFrame = new PhotoFrame();
    }

    private void installMainContent(MainWindow window) {
        // Put the photo component in a JPanel to center it
        JPanel photoFrameContainer = new JPanel(new GridBagLayout());
        photoFrameContainer.setBackground(Color.DARK_GRAY);
        photoFrameContainer.add(photoFrame);

        // Add the photo container into the scroll pane
        JScrollPane mainContainer = window.getMainContainer();
        mainContainer.setViewportView(photoFrameContainer);
        mainContainer.revalidate();
    }

    private void uninstallMainContent(MainWindow window) {
        JScrollPane mainContainer = window.getMainContainer();
        mainContainer.setViewportView(null);
    }

    private void installToolBar(MainWindow window) {
        installToolBarTools(window);
        installToolBarToolSettings(window);
    }

    private void installToolBarTools(MainWindow window) {
        JComponent container = window.getToolbar().getContainer();

        JLabel toolsLabel = new JLabel("Tools");
        toolsLabel.setFont(new Font(toolsLabel.getFont().getName(), Font.BOLD, 16));
        toolsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        container.add(toolsLabel);

        JRadioButton penToolButton = new JRadioButton("Pen");
        penToolButton.setOpaque(false);
        penToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.PEN)));
        container.add(penToolButton);

        JRadioButton rectangleToolButton = new JRadioButton("Rectangle");
        rectangleToolButton.setOpaque(false);
        rectangleToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.RECTANGLE)));
        container.add(rectangleToolButton);

        JRadioButton ellipsisToolButton = new JRadioButton("Ellipsis");
        ellipsisToolButton.setOpaque(false);
        ellipsisToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.ELLIPSIS)));
        container.add(ellipsisToolButton);

        JRadioButton textToolButton = new JRadioButton("Text");
        textToolButton.setOpaque(false);
        textToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.TEXT)));
        container.add(textToolButton);

        // Group the buttons together
        ButtonGroup toolButtonsGroup = new ButtonGroup();
        toolButtonsGroup.add(penToolButton);
        toolButtonsGroup.add(rectangleToolButton);
        toolButtonsGroup.add(ellipsisToolButton);
        toolButtonsGroup.add(textToolButton);

        // Set the current tool button as selected
        switch (photoFrame.getTool()) {
            default:
            case PEN:
                penToolButton.setSelected(true);
                break;

            case RECTANGLE:
                rectangleToolButton.setSelected(true);
                break;

            case ELLIPSIS:
                ellipsisToolButton.setSelected(true);
                break;

            case TEXT:
                textToolButton.setSelected(true);
                break;
        }
    }

    private void installToolBarToolSettings(MainWindow window) {
        JComponent container = window.getToolbar().getContainer();

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font(settingsLabel.getFont().getName(), Font.BOLD, 16));
        settingsLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        container.add(settingsLabel);

        JSlider thicknessSlider = new JSlider(JSlider.HORIZONTAL,
                1, 20, 5);
        thicknessSlider.setMajorTickSpacing(5);
        thicknessSlider.setMinorTickSpacing(1);
        thicknessSlider.setPaintTicks(true);
        thicknessSlider.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        thicknessSlider.setOpaque(false);
        thicknessSlider.addChangeListener((e) -> {
            int newThickness = thicknessSlider.getValue();
            photoFrame.getToolSettings().setThickness(newThickness);
        });
        container.add(thicknessSlider);

        Hashtable thicknessSliderLabels = new Hashtable();
        thicknessSliderLabels.put(1, new JLabel(Integer.toString((1))));
        thicknessSliderLabels.put(10, new JLabel(Integer.toString((10))));
        thicknessSliderLabels.put(20, new JLabel(Integer.toString((20))));
        thicknessSlider.setLabelTable(thicknessSliderLabels);
        thicknessSlider.setPaintLabels(true);


        JButton chooseColorButton = new JButton("Select color");
        chooseColorButton.addActionListener((e) -> {
            Color newColor = JColorChooser.showDialog(container, "Select the main color", Color.BLACK);

            if (newColor != null) {
                photoFrame.getToolSettings().setColor(newColor);
            }
        });
        container.add(chooseColorButton);
    }


    private void uninstallToolBar(MainWindow window) {
        JComponent container = window.getToolbar().getContainer();
        container.removeAll();
    }

    private void installAllEventHandlers() {
        eventManager.addHandler("photo:change", this::handlePhotoChangeEvent);
        eventManager.addHandler("file:delete", this::handlePhotoDeleteEvent);
        eventManager.addHandler("tool:change", this::handleToolChangeEvent);
    }

    private void uninstallAllEventHandlers() {
        eventManager.removeHandler("photo:change", this::handlePhotoChangeEvent);
        eventManager.removeHandler("file:delete", this::handlePhotoDeleteEvent);
        eventManager.removeHandler("tool:change", this::handleToolChangeEvent);

    }

    private void handlePhotoChangeEvent(PhotoChangeEvent event) {
        photoFrame.setPhoto(event.photo);

        Dimension photoSize = new Dimension(event.photo.getWidth(), event.photo.getHeight());
        photoFrame.setSize(photoFrame.getParent().getSize());
    }

    private void handlePhotoDeleteEvent(Event event) {
        photoFrame.removePhoto();
    }

    private void handleToolChangeEvent(ToolChangeEvent event) {
        photoFrame.setTool(event.toolID);
    }

    @Override
    public void install(MainWindow window) {
        installMainContent(window);
        installToolBar(window);

        installAllEventHandlers();
    }

    @Override
    public void uninstall(MainWindow window) {
        uninstallMainContent(window);
        uninstallToolBar(window);

        uninstallAllEventHandlers();
    }
}
