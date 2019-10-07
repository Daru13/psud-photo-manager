package GUI.Tools;

import GUI.Components.PhotoFrame;

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
        drawCurrentPath(false);
        photoFrame.clearWorkingCanvas();

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

            drawCurrentPath(true);
        }
    }

    private void configureGraphics(Graphics2D g, boolean draft) {
        // Rendering quality
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                draft ? RenderingHints.VALUE_RENDER_SPEED : RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                draft ? RenderingHints.VALUE_ANTIALIAS_OFF : RenderingHints.VALUE_ANTIALIAS_ON);

        // Drawing style
        ToolSettings settings = photoFrame.getToolSettings();
        g.setColor(settings.getColor());
        g.setStroke(new BasicStroke(settings.getThickness()));
    }

    private void drawCurrentPath(boolean useWorkingCanvas) {
        Graphics2D g = useWorkingCanvas
                ? (Graphics2D)photoFrame.getWorkingCanvas().getGraphics()
                : (Graphics2D)photoFrame.getPhotoBack().getGraphics();

        if (useWorkingCanvas) {
            photoFrame.clearWorkingCanvas();
        }

        // Integer list int array conversion using Java 8 streams
        // Source: https://stackoverflow.com/a/39403890
        int[] xCoordinates = currentPathX.stream().mapToInt(Integer::intValue).toArray();
        int[] yCoordinates = currentPathY.stream().mapToInt(Integer::intValue).toArray();

        configureGraphics(g, useWorkingCanvas);
        g.drawPolyline(xCoordinates, yCoordinates, currentPathNbPoints);
        photoFrame.repaint();
    }
}
