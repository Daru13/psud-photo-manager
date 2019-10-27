package Components.Tools;

import Components.Annotations.Rectangle;
import Components.PhotoFrame;
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
    Components.Annotations.Rectangle createAnnotation() {
        return new Components.Annotations.Rectangle(photoFrame, firstCorner);
    }

}
