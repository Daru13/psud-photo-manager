package GUI.Components.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Components.Tools.ToolSettings;
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

        updateStyleFromToolSettings();
        applyStyleToCanvasShape();
    }

    @Override
    public void updateStyleFromToolSettings() {
        ToolSettings settings = photoFrame.getToolSettings();
        color = settings.getColor();
        thickness = settings.getThickness();
    }

    @Override
    public void applyStyleToCanvasShape() {
        setOutlined(true);
        setStroke(new BasicStroke(thickness));
        setOutlinePaint(color);

        setFilled(false);
    }

    public void addStep(Point point) {
        lineTo(point);
        photoFrame.repaint();
    }

    @Override
    public CPolyLine getCanvasShape() {
        return this;
    }
}
