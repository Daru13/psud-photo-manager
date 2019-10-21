package GUI.Annotations;

import GUI.Components.PhotoFrame;

import java.awt.*;

public class Rectangle extends GeometricShapeAnnotation {

    public Rectangle(PhotoFrame photoFrame, Point origin) {
        super(photoFrame, origin);
    }

    @Override
    public void draw(Graphics2D g) {
        configureGraphics(g, false);
        g.fillRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
