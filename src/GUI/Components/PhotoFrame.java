package GUI.Components;

import GUI.Annotations.Annotation;
import GUI.Tools.ToolID;
import GUI.Tools.ToolSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * A photo frame to display a single photo.
 *
 * The photo can be flipped and annotated on its back using various tools.
 */
public class PhotoFrame extends JComponent {

    final PhotoFrameModel model;
    final PhotoFrameView view;


    public PhotoFrame() {
        model = new PhotoFrameModel();
        view = new PhotoFrameView(this);

        configureComponent();
        configureEventListeners();

        //addAnnotation(new CRectangle(0,0,100,100).setFillPaint(Color.BLACK));
        //addAnnotation(new CText.newText(10, 20, "AAAAAA BBBB\nCCCCC"));
    }

    private void configureComponent() {
        setFocusable(true);
    }

    private void configureEventListeners() {
        addMouseListener(this.view);
        addMouseMotionListener(this.view);
        addKeyListener(this.view);
    }

    public BufferedImage getPhoto() {
        return model.getPhoto();
    }

    public void setPhoto(BufferedImage photo) {
        Dimension photoDimensions = new Dimension(photo.getWidth(), photo.getHeight());

        model.setPhoto(photo);
        view.setAnnotationCanvasSize(photoDimensions);
        setPreferredSize(photoDimensions);

        repaint();
        revalidate();
    }

    public void removePhoto() {
        Dimension zeroDimensions = new Dimension(0, 0);

        model.removePhoto();
        view.setAnnotationCanvasSize(zeroDimensions);
        setPreferredSize(zeroDimensions);

        repaint();
        revalidate();
    }

    public void addAnnotation(Annotation annotation) {
        model.addAnnotation(annotation);
        view.addAnnotationShape(annotation.getCanvasShape());

        repaint();
        revalidate();
    }

    public void removeAnnotation(Annotation annotation) {
        model.removeAnnotation(annotation);
        view.removeAnnotationShape(annotation.getCanvasShape());

        repaint();
        revalidate();
    }

    boolean isAnnotable() {
        return model.isAnnotable();
    }

    void switchAnnotableState() {
        model.setAnnotable(! model.isAnnotable());
        System.out.println("Photo is editable = " + model.isAnnotable());
    }

    public ToolID getTool() {
        return view.getTool();
    }

    public void setTool(ToolID toolID) {
        view.setTool(toolID);
    }

    public ToolSettings getToolSettings() {
        return view.getToolSettings();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        view.paint((Graphics2D)g);
    }
}
