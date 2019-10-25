package GUI.Tools;

import GUI.Annotations.Ellipsis;
import GUI.Components.PhotoFrame;


/**
 * A tool to draw an ellipsis.
 *
 * @see Tool
 */
public class EllipsisTool extends RectangularShapeTool<Ellipsis> {

    public EllipsisTool(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    Ellipsis createAnnotation() {
        return new Ellipsis(photoFrame, firstCorner);
    }

}
