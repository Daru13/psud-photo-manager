package GUI.Tools;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CRectangularShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

import java.awt.*;


/**
 * An abstract tool for drawing geometric shapes.
 *
 * It provides support for detecting the position of a first click,
 * waiting for the user to move the mouse to define a bounding box,
 * and detecting the position of a second click (release).
 *
 * @see Tool
 */
public abstract class RectangularShapeTool<A extends CRectangularShape> extends Tool {
    private CRectangularShape annotation;
    Point firstCorner;

    RectangularShapeTool(PhotoFrame photoFrame) {
        super(photoFrame);
        this.annotation = null;
        this.firstCorner = null;
    }

    abstract A createAnnotation();

    public final State waiting = new State() {
        Transition startDrawing = new Press(BUTTON1, "drawing") {
            @Override
            public void action() {
                firstCorner = getMouseEvent().getPoint();
                annotation = createAnnotation();
                photoFrame.addAnnotation(annotation);
            }
        };
    };

    public final State drawing = new State() {
        Transition draw = new Drag(BUTTON1, "drawing") {
            @Override
            public void action() {
                Point corner = getMouseEvent().getPoint();

                int width = Math.abs(corner.x - firstCorner.x);
                int height = Math.abs(corner.y - firstCorner.y);
                int x = Math.min(firstCorner.x, corner.x);
                int y = Math.min(firstCorner.y, corner.y);

                annotation.setBoundingBox(x, y, width, height);
                photoFrame.repaint();
            }
        };

        Transition stopDrawing = new Release(BUTTON1, "waiting") {
            @Override
            public void action() {
                annotation = null;
                firstCorner = null;
            }
        };
    };
}
