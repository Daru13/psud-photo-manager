package GUI.Components.Tools;

import GUI.Components.Annotations.Ellipse;
import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CEllipse;


/**
 * A tool to draw an ellipsis.
 *
 * @see Tool
 */
public class EllipseTool extends RectangularShapeTool<CEllipse, Ellipse> {

    public EllipseTool(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    Ellipse createAnnotation() {
        return new Ellipse(photoFrame, firstCorner);
    }

}
