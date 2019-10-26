package GUI.Components;

import GUI.Tools.ToolID;
import GUI.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CShape;

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
        add(model.getAnnotationCanvas());
        //addMouseListener(this.view);
    }

    private void configureComponent() {
        setFocusable(true);
    }

    public BufferedImage getPhoto() {
        return model.getPhoto();
    }

    public void setPhoto(BufferedImage photo) {
        Dimension photoDimensions = new Dimension(photo.getWidth(), photo.getHeight());

        model.setPhoto(photo);
        setPreferredSize(photoDimensions);

        repaint();
        revalidate();
    }

    public void removePhoto() {
        Dimension zeroDimensions = new Dimension(0, 0);

        model.removePhoto();
        setPreferredSize(zeroDimensions);

        repaint();
        revalidate();
    }

    public void addAnnotation(CShape annotation) {
        model.addAnnotation(annotation);

        repaint();
        revalidate();
    }

    public void removeAnnotation(CShape annotation) {
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
