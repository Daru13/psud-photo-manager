package GUI.Tools;

import GUI.Annotations.Rectangle;
import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CRectangle;


/**
 * A tool to draw an rectangle.
 *
 * @see Tool
 */
public class RectangleTool extends RectangularShapeTool<CRectangle, Rectangle> {

    public RectangleTool(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    GUI.Annotations.Rectangle createAnnotation() {
        return new GUI.Annotations.Rectangle(photoFrame, firstCorner);
    }

}
