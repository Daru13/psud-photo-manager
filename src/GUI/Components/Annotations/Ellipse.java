package GUI.Components.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Components.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CEllipse;

import java.awt.*;

/**
 * An annotation with the shape of an ellipse.
 * The color of the shape can be personalised.
 *
 * @see Annotation
 */
public class Ellipse extends CEllipse implements Annotation<CEllipse> {
    private PhotoFrame photoFrame;

    private Point firstCorner;
    Color color;

    public Ellipse(PhotoFrame photoFrame, Point corner) {
        super();

        this.photoFrame = photoFrame;
        firstCorner = corner;

        updateStyleFromToolSettings();
        applyStyleToCanvasShape();
    }

    @Override
    public void updateStyleFromToolSettings() {
        ToolSettings settings = photoFrame.getToolSettings();
        color = settings.getColor();
    }

    @Override
    public void applyStyleToCanvasShape() {
        setOutlined(false);
        setStroke(new BasicStroke(0));

        setFilled(true);
        setFillPaint(color);
    }

    public void setOppositeCorner(Point corner) {
        int width = Math.abs(corner.x - firstCorner.x);
        int height = Math.abs(corner.y - firstCorner.y);
        int x = Math.min(firstCorner.x, corner.x);
        int y = Math.min(firstCorner.y, corner.y);

        setBoundingBox(x, y, width, height);
        photoFrame.repaint();
    }

    @Override
    public CEllipse getCanvasShape() {
        return this;
    }
}
