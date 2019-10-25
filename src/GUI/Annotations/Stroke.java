package GUI.Annotations;

import GUI.Components.PhotoFrame;
import GUI.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CPolyLine;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Stroke implements Annotation {

    private PhotoFrame photoFrame;

    private CPolyLine shape;
    private List<Point> points;
    private int nbPoints;
    private Color color;
    private int thickness;


    public Stroke(PhotoFrame photoFrame, Point origin) {
        this.photoFrame = photoFrame;

        shape = new CPolyLine().moveTo(origin);
        points = new LinkedList<>();
        nbPoints = 0;

        ToolSettings settings = photoFrame.getToolSettings();
        color = settings.getColor();
        thickness = settings.getThickness();

        addStep(origin);
    }

    public void addStep(Point point) {
        shape.lineTo(point);
        points.add(point);
        nbPoints++;

        photoFrame.repaint();
    }

    private void configureGraphics(Graphics2D g, boolean draft) {
        // Rendering quality
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                draft ? RenderingHints.VALUE_RENDER_SPEED : RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                draft ? RenderingHints.VALUE_ANTIALIAS_OFF : RenderingHints.VALUE_ANTIALIAS_ON);

        // Drawing style
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
    }

    //@Override
    public void draw(Graphics2D g) {
        configureGraphics(g, false);

        int[] pointsX = new int[nbPoints];
        int[] pointsY = new int[nbPoints];

        for (int i = 0; i < nbPoints; i++) {
            Point point = points.get(i);
            pointsX[i] = point.x;
            pointsY[i] = point.y;
        }

        g.drawPolyline(pointsX, pointsY, nbPoints);
    }

    @Override
    public CPolyLine getCanvasShape() {
        return shape;
    }
}
