package GUI.Tools;

import GUI.Components.PhotoFrame;

import java.awt.*;


/**
 * A tool to draw an rectangle.
 *
 * @see Tool
 */
public class Rectangle extends GeometricShapeTool {

    public Rectangle(PhotoFrame photoFrame) {
        super(photoFrame);
    }


    @Override
    protected void drawShape(Graphics2D g, int originX, int originY, int width, int height) {
        g.fillRect(originX, originY, width, height);
    }
}
