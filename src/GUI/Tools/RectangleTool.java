package GUI.Tools;

import GUI.Annotations.Rectangle;
import GUI.Components.PhotoFrame;

import java.awt.*;


/**
 * A tool to draw an rectangle.
 *
 * @see Tool
 */
public class RectangleTool extends RectangularShapeTool<Rectangle> {

    public RectangleTool(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    GUI.Annotations.Rectangle createAnnotation(Point origin) {
        return new GUI.Annotations.Rectangle(photoFrame, origin);
    }

}
