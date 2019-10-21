package GUI.Annotations;

import GUI.Components.PhotoFrame;

import java.awt.*;

public class Ellipsis extends GeometricShapeAnnotation {

    public Ellipsis(PhotoFrame photoFrame, Point origin) {
        super(photoFrame, origin);
    }

    @Override
    public void draw(Graphics2D g) {
        configureGraphics(g, false);
        g.fillOval(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
