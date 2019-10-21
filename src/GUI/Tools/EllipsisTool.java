package GUI.Tools;

import GUI.Annotations.Ellipsis;
import GUI.Components.PhotoFrame;

import java.awt.*;


/**
 * A tool to draw an ellipsis.
 *
 * @see Tool
 */
public class EllipsisTool extends GeometricShapeTool<Ellipsis> {

    public EllipsisTool(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    Ellipsis createAnnotation(Point origin) {
        return new Ellipsis(photoFrame, origin);
    }

}
