package GUI.Tools;

import GUI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.MouseEvent;


/**
 * An abstract tool for drawing geometric shapes.
 *
 * It provides support for detecting the position of a first click,
 * waiting for the user to move the mouse to define a bounding box,
 * and detecting the position of a second click (release).
 *
 * @see Tool
 */
public abstract class GeometricShapeTool extends ToolAdapter {
    private PhotoFrame photoFrame;

    private int firstClickX;
    private int firstClickY;


    GeometricShapeTool(PhotoFrame photoFrame) {
        super();

        this.photoFrame = photoFrame;

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
        firstClickX = 0;
        firstClickY = 0;
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
}
