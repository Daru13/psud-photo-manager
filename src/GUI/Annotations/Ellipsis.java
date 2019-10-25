package GUI.Annotations;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CEllipse;
import fr.lri.swingstates.canvas.CRectangularShape;

import java.awt.*;

public class Ellipsis extends RectangularShapeAnnotation {

    public Ellipsis(PhotoFrame photoFrame, Point origin) {
        super(photoFrame, origin);
    }

    @Override
    CRectangularShape createShape() {
        return new CEllipse();
    }
}
