package UI.Tools;

import UI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class Pen extends AbstractTool {

    private boolean penIsDown;
    private int currentPathNbPoints;
    private List<Integer> currentPathX;
    private List<Integer> currentPathY;

    public Pen(PhotoFrame photoFrame) {
        super(photoFrame);

        penIsDown = false;
        currentPathNbPoints = 0;
        currentPathX = new LinkedList<>();
        currentPathY = new LinkedList<>();
    }

    @Override
    public void mousePressed(MouseEvent event) {
        penIsDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        drawCurrentPath();

        penIsDown = false;
        currentPathNbPoints = 0;
        currentPathX.clear();
        currentPathY.clear();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (penIsDown) {
            currentPathX.add(new Integer(event.getX()));
            currentPathY.add(new Integer(event.getY()));
            currentPathNbPoints++;

            drawCurrentPath();
        }
    }

    private void drawCurrentPath() {
        Graphics2D g = (Graphics2D)photoFrame.getPhotoBack().getGraphics();

        // Integer list int array conversion using Java 8 streams
        // Source: https://stackoverflow.com/a/39403890
        int[] xCoordinates = currentPathX.stream().mapToInt(Integer::intValue).toArray();
        int[] yCoordinates = currentPathY.stream().mapToInt(Integer::intValue).toArray();

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(5));

        g.drawPolyline(xCoordinates, yCoordinates, currentPathNbPoints);
        photoFrame.repaint();
    }
}
