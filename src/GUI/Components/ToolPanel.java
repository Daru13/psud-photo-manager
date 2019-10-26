package GUI.Components;

import Events.EventManager;
import Events.ToolChangeEvent;
import GUI.Tools.ToolID;

import javax.swing.*;
import java.awt.*;


/**
 * A toolbar panel for displaying available tools and selecting the current one.
 */
public class ToolPanel extends ToolBarPanel {

    private EventManager eventManager;
    private JPanel toolButtonsContainer;


    public ToolPanel(PhotoFrame photoFrame, EventManager eventManager) {
        super(photoFrame);

        this.eventManager = eventManager;
        toolButtonsContainer= new JPanel();

        configure();
        createContent();
    }

    @Override
    protected void configure() {
        super.configure();

        GridLayout layout = new GridLayout(0, 2, 10, 10);
        toolButtonsContainer.setLayout(layout);
        toolButtonsContainer.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
        toolButtonsContainer.setOpaque(false);
    }

    @Override
    protected void createContent() {
        createTitle();
        createToolButtons();
    }

    private void createTitle() {
        JLabel toolsLabel = new JLabel("Tools");
        toolsLabel.setFont(new Font(toolsLabel.getFont().getName(), Font.BOLD, TITLE_SIZE));
        toolsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        toolsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        add(toolsLabel);
    }

    private void createToolButtons() {
        JToggleButton penToolButton = new JToggleButton("Pen");
        penToolButton.setMaximumSize(new Dimension(0, 50));
        penToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.PEN)));
        toolButtonsContainer.add(penToolButton);

        JToggleButton rectangleToolButton = new JToggleButton("Rectangle");
        rectangleToolButton.setOpaque(false);
        rectangleToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.RECTANGLE)));
        toolButtonsContainer.add(rectangleToolButton);

        JToggleButton ellipsisToolButton = new JToggleButton("Ellipsis");
        ellipsisToolButton.setOpaque(false);
        ellipsisToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.ELLIPSIS)));
        toolButtonsContainer.add(ellipsisToolButton);

        JToggleButton textToolButton = new JToggleButton("Text");
        textToolButton.setOpaque(false);
        textToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.TEXT)));
        toolButtonsContainer.add(textToolButton);

        // Group the buttons together
        ButtonGroup toolButtonsGroup = new ButtonGroup();
        toolButtonsGroup.add(penToolButton);
        toolButtonsGroup.add(rectangleToolButton);
        toolButtonsGroup.add(ellipsisToolButton);
        toolButtonsGroup.add(textToolButton);

        // Set the current tool button as selected
        switch (photoFrame.getCurrentToolID()) {
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

        add(toolButtonsContainer);
    }
}
