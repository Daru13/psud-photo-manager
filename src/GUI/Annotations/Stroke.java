package GUI.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CPolyLine;

import java.awt.*;

public class Stroke extends CPolyLine implements Annotation<CPolyLine> {

    private PhotoFrame photoFrame;

    private Color color;
    private int thickness;


    public Stroke(PhotoFrame photoFrame, Point origin) {
        this.photoFrame = photoFrame;
        moveTo(origin);

        ToolSettings settings = photoFrame.getToolSettings();
        color = settings.getColor();
        thickness = settings.getThickness();

        addStep(origin);
        initStyle();
    }

    private void initStyle() {
        ToolSettings settings = photoFrame.getToolSettings();
        setColor(settings.getColor());
        setThickness(settings.getThickness());

        // Reset default shape properties
        filled = false;
    }

    public void addStep(Point point) {
        lineTo(point);
        photoFrame.repaint();
    }

    public void setColor(Color color) {
        setOutlinePaint(color);
    }

    public void setThickness(int thickness) {
        setStroke(new BasicStroke(thickness));
    }

    @Override
    public CPolyLine getCanvasShape() {
        return this;
    }
}
