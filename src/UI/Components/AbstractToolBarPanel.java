package UI.Components;

import javax.swing.*;
import java.awt.*;

abstract class AbstractToolBarPanel extends JPanel {

    protected PhotoFrame photoFrame;

    AbstractToolBarPanel(PhotoFrame photoFrame) {
        super();
        this.photoFrame = photoFrame;

        configure();
        createContent();
    }

    protected void configure() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        setOpaque(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    protected void createSeparator() {
        add(new JSeparator(SwingConstants.HORIZONTAL));
    }

    protected JLabel generateLabel(String text) {
        JLabel label = new JLabel(text);

        label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        //label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setBorder(BorderFactory.createLineBorder(Color.RED));

        return label;
    }

    abstract protected void createContent();
}
