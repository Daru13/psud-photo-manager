package GUI.Tools;

import GUI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class AbstractGeometricShapeTool extends AbstractTool {
    private int firstClickX;
    private int firstClickY;

    public AbstractGeometricShapeTool(PhotoFrame photoFrame) {
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

    private void configureGraphics(Graphics2D g, boolean draft) {
        // Rendering quality
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                draft ? RenderingHints.VALUE_RENDER_SPEED : RenderingHints.VALUE_RENDER_QUALITY);

        // Drawing style
        ToolSettings settings = photoFrame.getToolSettings();
        g.setColor(settings.getColor());
        g.setStroke(new BasicStroke(settings.getThickness()));
    }

    abstract protected void drawShape(Graphics2D g, int originX, int originY, int width, int height);

    private void drawEllipsis(int secondClickX, int secondClickY, boolean useWorkingCanvas) {
        Graphics2D g = useWorkingCanvas
                ? (Graphics2D)photoFrame.getWorkingCanvas().getGraphics()
                : (Graphics2D)photoFrame.getPhotoBack().getGraphics();

        if (useWorkingCanvas) {
            photoFrame.clearWorkingCanvas();
        }

        int originX = Math.min(firstClickX, secondClickX);
        int originY = Math.min(firstClickY, secondClickY);
        int width = Math.max(firstClickX, secondClickX) - originX;
        int height = Math.max(firstClickY, secondClickY) - originY;

        configureGraphics(g, useWorkingCanvas);
        drawShape(g, originX, originY, width, height);
        photoFrame.repaint();
    }
}
