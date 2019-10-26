package GUI.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CEllipse;

import java.awt.*;

public class Ellipsis extends CEllipse implements Annotation<CEllipse> {
    private PhotoFrame photoFrame;

    private Point firstCorner;
    Color color;

    public Ellipsis(PhotoFrame photoFrame, Point corner) {
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

    @Override
    public CEllipse getCanvasShape() {
        return this;
    }
}
