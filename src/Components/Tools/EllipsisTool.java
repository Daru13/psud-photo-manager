package Components.Tools;

import Components.Annotations.Ellipsis;
import Components.PhotoFrame;
import fr.lri.swingstates.canvas.CEllipse;


/**
 * A tool to draw an ellipsis.
 *
 * @see Tool
 */
public class EllipsisTool extends RectangularShapeTool<CEllipse, Ellipsis> {

    public EllipsisTool(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    Components.Annotations.Ellipsis createAnnotation() {
        return new Ellipsis(photoFrame, firstCorner);
    }

}
