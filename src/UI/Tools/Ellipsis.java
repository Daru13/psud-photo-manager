package UI.Tools;

import UI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ellipsis extends Tool {

    private int firstClickX;
    private int firstClickY;

    public Ellipsis(PhotoFrame photoFrame) {
        super(photoFrame);

        firstClickX = 0;
        firstClickY = 0;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        firstClickX = event.getX();
        firstClickY = event.getY();
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        drawEllipsis(event.getX(), event.getY(), false);

        firstClickX = 0;
        firstClickY = 0;
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        drawEllipsis(event.getX(), event.getY(), true);
    }

    private void drawEllipsis(int secondClickX, int secondClickY, boolean useWorkingCanvas) {
        Graphics2D g = useWorkingCanvas
                ? (Graphics2D)photoFrame.getWorkingCanvas().getGraphics()
                : (Graphics2D)photoFrame.getPhotoBack().getGraphics();

        if (useWorkingCanvas) {
            photoFrame.clearWorkingCanvas();
        }

        g.setColor(Color.BLACK);

        int originX = Math.min(firstClickX, secondClickX);
        int originY = Math.min(firstClickY, secondClickY);
        int width = Math.max(firstClickX, secondClickX) - originX;
        int height = Math.max(firstClickY, secondClickY) - originY;

        g.fillOval(originX, originY, width, height);
        photoFrame.repaint();
    }
}
