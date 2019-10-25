package GUI.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CRectangularShape;

import java.awt.*;

public abstract class RectangularShapeAnnotation implements Annotation {

    private PhotoFrame photoFrame;

    CRectangularShape shape;
    Color color;

    private Point firstCorner;


    RectangularShapeAnnotation(PhotoFrame photoFrame, Point corner) {
        this.photoFrame = photoFrame;

        shape = createShape();
        firstCorner = corner;

        ToolSettings settings = photoFrame.getToolSettings();
        color = settings.getColor();
    }

    abstract CRectangularShape createShape();

    public void setOppositeCorner(Point corner) {
        int width = Math.abs(corner.x - firstCorner.x);
        int height = Math.abs(corner.y - firstCorner.y);
        int x = Math.min(firstCorner.x, corner.x);
        int y = Math.min(firstCorner.y, corner.y);

        shape.setBoundingBox(x, y, width, height);

        photoFrame.repaint();
    }

    @Override
    public CRectangularShape getCanvasShape() {
        return shape;
    }
}
