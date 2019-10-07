package GUI.Tools;

import GUI.Components.PhotoFrame;

import java.awt.*;


/**
 * A tool to draw an ellipsis.
 *
 * @see Tool
 */
public class Ellipsis extends GeometricShapeTool {

    public Ellipsis(PhotoFrame photoFrame) {
        super(photoFrame);
    }


    @Override
    protected void drawShape(Graphics2D g, int originX, int originY, int width, int height) {
        g.fillOval(originX, originY, width, height);
    }
}
