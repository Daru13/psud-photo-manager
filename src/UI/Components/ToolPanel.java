package UI.Components;

import Events.EventManager;
import UI.Events.ToolChangeEvent;
import UI.Tools.ToolID;

import javax.swing.*;
import java.awt.*;

public class ToolPanel extends AbstractToolBarPanel {

    private EventManager eventManager;

    public ToolPanel(PhotoFrame photoFrame, EventManager eventManager) {
        super(photoFrame);
        this.eventManager = eventManager;
    }

    protected void createContent() {
        JLabel toolsLabel = new JLabel("Tools");
        toolsLabel.setFont(new Font(toolsLabel.getFont().getName(), Font.BOLD, 16));
        toolsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(toolsLabel);

        JRadioButton penToolButton = new JRadioButton("Pen");
        penToolButton.setOpaque(false);
        penToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.PEN)));
        add(penToolButton);

        JRadioButton rectangleToolButton = new JRadioButton("Rectangle");
        rectangleToolButton.setOpaque(false);
        rectangleToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.RECTANGLE)));
        add(rectangleToolButton);

        JRadioButton ellipsisToolButton = new JRadioButton("Ellipsis");
        ellipsisToolButton.setOpaque(false);
        ellipsisToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.ELLIPSIS)));
        add(ellipsisToolButton);

        JRadioButton textToolButton = new JRadioButton("Text");
        textToolButton.setOpaque(false);
        textToolButton.addActionListener((e) -> eventManager.emit(new ToolChangeEvent(ToolID.TEXT)));
        add(textToolButton);

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
}
