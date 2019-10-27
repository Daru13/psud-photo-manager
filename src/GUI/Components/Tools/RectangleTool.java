package GUI.Components.Tools;

import GUI.Components.Annotations.Rectangle;
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
    GUI.Components.Annotations.Rectangle createAnnotation() {
        return new GUI.Components.Annotations.Rectangle(photoFrame, firstCorner);
    }

}
