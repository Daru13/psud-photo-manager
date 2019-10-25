package GUI.Annotations;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CRectangularShape;

import java.awt.*;

public class Rectangle extends RectangularShapeAnnotation {

    CRectangle shape;

    public Rectangle(PhotoFrame photoFrame, Point corner) {
        super(photoFrame, corner);
    }

    @Override
    CRectangularShape createShape() {
        return new CRectangle();
    }
}
