package GUI.Tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class ToolAdapter implements Tool {

    // Tool selection/deselection
    @Override
    public void toolSelected() { }

    @Override
    public void toolDeselected() { }

    // Mouse events
    @Override
    public void mouseClicked(MouseEvent mouseEvent) { }

    @Override
    public void mousePressed(MouseEvent mouseEvent) { }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) { }

    @Override
    public void mouseExited(MouseEvent mouseEvent) { }

    // Mouse move events
    @Override
    public void mouseDragged(MouseEvent mouseEvent) { }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) { }

    // Keyboard events
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyPressed(KeyEvent keyEvent) { }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }
}
