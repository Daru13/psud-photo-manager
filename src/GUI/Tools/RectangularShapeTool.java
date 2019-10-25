package GUI.Tools;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CRectangularShape;

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
public abstract class RectangularShapeTool<A extends CRectangularShape> extends ToolAdapter {
    PhotoFrame photoFrame;

    private CRectangularShape annotation;
    Point firstCorner;

    RectangularShapeTool(PhotoFrame photoFrame) {
        super();

        this.photoFrame = photoFrame;
        this.annotation = null;
        this.firstCorner = null;
    }

    abstract A createAnnotation();

    @Override
    public void mousePressed(MouseEvent event) {
        firstCorner = new Point(event.getX(), event.getY());
        annotation = createAnnotation();

        photoFrame.addAnnotation(annotation);
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (annotation != null) {
            Point corner = new Point(event.getX(), event.getY());

            int width = Math.abs(corner.x - firstCorner.x);
            int height = Math.abs(corner.y - firstCorner.y);
            int x = Math.min(firstCorner.x, corner.x);
            int y = Math.min(firstCorner.y, corner.y);

            annotation.setBoundingBox(x, y, width, height);
            photoFrame.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        annotation = null;
        firstCorner = null;
    }
}
