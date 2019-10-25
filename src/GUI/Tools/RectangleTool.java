package GUI.Tools;

import GUI.Annotations.Rectangle;
import GUI.Components.PhotoFrame;


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
    GUI.Annotations.Rectangle createAnnotation() {
        return new GUI.Annotations.Rectangle(photoFrame, firstCorner);
    }

}
