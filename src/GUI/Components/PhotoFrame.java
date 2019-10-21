package GUI.Components;

import GUI.Annotations.Annotation;
import GUI.Tools.ToolID;
import GUI.Tools.ToolSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;


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
        int photoWidth = photo.getWidth();
        int photoHeight = photo.getHeight();

        model.setPhoto(photo);
        view.createWorkingCanvas(photoWidth, photoHeight);

        setPreferredSize(new Dimension(photoWidth, photoHeight));

        repaint();
        revalidate();
    }

    public void removePhoto() {
        model.removePhoto();
        view.removeWorkingCanvas();

        setPreferredSize(new Dimension(0, 0));

        repaint();
        revalidate();
    }

    public List<Annotation> getAnnotations() {
        return model.getAnnotations();
    }

    public void addAnnotation(Annotation annotation) {
        model.addAnnotation(annotation);

        repaint();
        revalidate();
    }

    public void removeAnnotation(Annotation annotation) {
        model.removeAnnotation(annotation);

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

    public BufferedImage getWorkingCanvas() {
        return view.getWorkingCanvas();
    }

    public void clearWorkingCanvas() {
        this.view.clearWorkingCanvas();
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
