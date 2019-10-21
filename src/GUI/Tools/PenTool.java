package GUI.Tools;

import GUI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.MouseEvent;


/**
 * A tool to freely draw using a pen.
 *
 * @see Tool
 */
public class PenTool extends ToolAdapter {

    private PhotoFrame photoFrame;
    private GUI.Annotations.Stroke annotation;
    private boolean penIsDown;


    public PenTool(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;
        annotation = null;
        penIsDown = false;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        Point origin = new Point(event.getX(), event.getY());
        annotation = new GUI.Annotations.Stroke(photoFrame, origin);

        photoFrame.addAnnotation(annotation);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        penIsDown = false;
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (annotation != null) {
            Point mousePosition = new Point(event.getX(), event.getY());
            annotation.addStep(mousePosition);
        }
    }
}
