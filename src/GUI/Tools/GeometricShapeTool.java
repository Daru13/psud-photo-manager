package GUI.Tools;

import GUI.Annotations.GeometricShapeAnnotation;
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
public abstract class GeometricShapeTool<A extends GeometricShapeAnnotation> extends ToolAdapter {
    PhotoFrame photoFrame;
    private GeometricShapeAnnotation annotation;


    GeometricShapeTool(PhotoFrame photoFrame) {
        super();

        this.photoFrame = photoFrame;
        this.annotation = null;
    }

    abstract A createAnnotation(Point origin);

    @Override
    public void mousePressed(MouseEvent event) {
        Point origin = new Point(event.getX(), event.getY());
        annotation = createAnnotation(origin);

        photoFrame.addAnnotation(annotation);
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (annotation != null) {
            Point corner = new Point(event.getX(), event.getY());
            annotation.setBoundingBoxSize(corner);
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        annotation = null;
    }
}
