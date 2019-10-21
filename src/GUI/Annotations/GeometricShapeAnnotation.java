package GUI.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Tools.ToolSettings;

import java.awt.Rectangle;
import java.awt.*;

public abstract class GeometricShapeAnnotation implements Annotation {

    private PhotoFrame photoFrame;

    Rectangle boundingBox;
    private Color color;


    GeometricShapeAnnotation(PhotoFrame photoFrame, Point origin) {
        this.photoFrame = photoFrame;

        boundingBox = new Rectangle(origin);

        ToolSettings settings = photoFrame.getToolSettings();
        color = settings.getColor();
    }

    public void setBoundingBoxSize(Point corner) {
        int width = corner.x - boundingBox.x;
        int height = corner.y - boundingBox.y;
        boundingBox.setSize(width, height);

        photoFrame.repaint(boundingBox);
    }

    void configureGraphics(Graphics2D g, boolean draft) {
        // Rendering quality
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                draft ? RenderingHints.VALUE_RENDER_SPEED : RenderingHints.VALUE_RENDER_QUALITY);

        // Drawing style
        g.setColor(color);
    }

    @Override
    public abstract void draw(Graphics2D g);
}
