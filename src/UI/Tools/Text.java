package UI.Tools;

import UI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Text extends AbstractTool {

    private StringBuilder stringBuilder;
    private boolean currentlyEditing;

    private int firstClickX;
    private int firstClickY;

    public Text(PhotoFrame photoFrame) {
        super(photoFrame);

        stringBuilder = new StringBuilder();
        currentlyEditing = false;

        firstClickX = 0;
        firstClickY = 0;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getClickCount() != 1) {
            return;
        }

        if (currentlyEditing) {
            drawString(false);

            stringBuilder = new StringBuilder();
            currentlyEditing = false;
        }
        else {
            if (!photoFrame.hasFocus()) {
                photoFrame.requestFocusInWindow();
            }

            firstClickX = event.getX();
            firstClickY = event.getY();
            currentlyEditing = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
        if (currentlyEditing) {
            stringBuilder.append(event.getKeyChar());
            drawString(true);
        }
    }

    private void drawString(boolean useWorkingCanvas) {
        Graphics2D g = useWorkingCanvas
                ? (Graphics2D)photoFrame.getWorkingCanvas().getGraphics()
                : (Graphics2D)photoFrame.getPhotoBack().getGraphics();

        if (useWorkingCanvas) {
            photoFrame.clearWorkingCanvas();
        }

        ToolSettings settings = photoFrame.getToolSettings();
        g.setColor(settings.getColor());
        //g.setStroke(new BasicStroke(settings.getThickness()));

        g.drawString(stringBuilder.toString(), firstClickX, firstClickY);
        photoFrame.repaint();
    }
}
