package GUI.Components;

import javax.swing.*;
import java.awt.*;


/**
 * An abstract toolbar panel.
 *
 * It should be extended by any toolbar panel.
 */
abstract class ToolBarPanel extends JPanel {

    final static int TITLE_SIZE = 16;

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
