package GUI.Tools;

import GUI.Annotations.Ellipsis;
import GUI.Components.PhotoFrame;
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
    GUI.Annotations.Ellipsis createAnnotation() {
        return new Ellipsis(photoFrame, firstCorner);
    }

}
