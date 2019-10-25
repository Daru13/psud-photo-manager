package GUI.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CRectangle;

import java.awt.*;

public class Rectangle extends CRectangle {

    private PhotoFrame photoFrame;

    private Point firstCorner;
    Color color;

    public Rectangle(PhotoFrame photoFrame, Point corner) {
        super();

        this.photoFrame = photoFrame;
        firstCorner = corner;

        initStyle();
    }

    private void initStyle() {
        ToolSettings settings = photoFrame.getToolSettings();
        setColor(settings.getColor());

        // Reset default shape properties
        setStroke(new BasicStroke(0));
    }

    public void setOppositeCorner(Point corner) {
        int width = Math.abs(corner.x - firstCorner.x);
        int height = Math.abs(corner.y - firstCorner.y);
        int x = Math.min(firstCorner.x, corner.x);
        int y = Math.min(firstCorner.y, corner.y);

        setBoundingBox(x, y, width, height);
        photoFrame.repaint();
    }

    public void setColor(Color color) {
        setFillPaint(color);
    }
}
