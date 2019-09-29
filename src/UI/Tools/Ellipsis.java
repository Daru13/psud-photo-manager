package UI.Tools;

import UI.Components.PhotoFrame;

import java.awt.*;

public class Ellipsis extends AbstractGeometricShapeTool {

    public Ellipsis(PhotoFrame photoFrame) {
        super(photoFrame);
    }

    @Override
    protected void drawShape(Graphics2D g, int originX, int originY, int width, int height) {
        g.fillOval(originX, originY, width, height);
    }
}
