package GUI.Tools;

import GUI.Components.PhotoFrame;

import java.awt.*;

public class Rectangle extends AbstractGeometricShapeTool {

    public Rectangle(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    protected void drawShape(Graphics2D g, int originX, int originY, int width, int height) {
        g.fillRect(originX, originY, width, height);
    }
}
