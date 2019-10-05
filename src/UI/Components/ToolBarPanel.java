package UI.Components;

import javax.swing.*;
import java.awt.*;

abstract class ToolBarPanel extends JPanel {

    protected PhotoFrame photoFrame;

    ToolBarPanel(PhotoFrame photoFrame) {
        super();
        this.photoFrame = photoFrame;
    }

    protected void configure() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);
    }

    abstract protected void createContent();
}
