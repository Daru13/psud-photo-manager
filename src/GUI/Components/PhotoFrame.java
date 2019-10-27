package GUI.Components;

import GUI.Annotations.Annotation;
import GUI.Tools.SelectionTool;
import GUI.Tools.ToolID;
import GUI.Tools.ToolManager;
import GUI.Tools.ToolSettings;
import fr.lri.swingstates.canvas.CShape;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A photo frame to display a single photo.
 *
 * The photo can be flipped and annotated on its back using various tools.
 */
public class PhotoFrame extends JComponent {

    final PhotoFrameModel model;
    final PhotoFrameView view;
    private final ToolManager toolManager;

    public PhotoFrame() {
        model = new PhotoFrameModel();
        view = new PhotoFrameView(this);
        toolManager = new ToolManager(this, view.getAnnotationCanvas());

        configureComponent();
        //addMouseListener(this.view);
    }

    private void configureComponent() {
        setFocusable(true);
    }

    public BufferedImage getPhoto() {
        return model.getPhoto();
    }

    public void setPhoto(BufferedImage photo) {
        model.setPhoto(photo);
        view.updateSize();

        repaint();
        revalidate();
    }

    public void removePhoto() {
        model.removePhoto();
        view.updateSize();

        repaint();
        revalidate();
    }

    public <S extends CShape> void addAnnotation(Annotation<S> annotation) {
        model.addAnnotation(annotation);
        view.addAnnotationShape(annotation.getCanvasShape());

        repaint();
        revalidate();
    }

    public <S extends CShape> void removeAnnotation(Annotation<S> annotation) {
        model.removeAnnotation(annotation);
        view.removeAnnotationShape(annotation.getCanvasShape());

        repaint();
        revalidate();
    }

    List<Annotation<? extends CShape>> getSelectedAnnotations() {
        return model
            .getAnnotations()
            .stream()
            .filter(a -> a.getCanvasShape().hasTag(SelectionTool.SELECTED_TAG))
            .collect(Collectors.toList());
    }

    boolean isAnnotable() {
        return model.isAnnotable();
    }

    void switchAnnotableState() {
        model.setAnnotable(! model.isAnnotable());
        System.out.println("Photo is editable = " + model.isAnnotable());
    }

    public ToolID getCurrentToolID() {
        return toolManager.getCurrentToolID();
    }

    public void setTool(ToolID toolID) {
        toolManager.setTool(toolID);
    }

    public ToolSettings getToolSettings() {
        return toolManager.getToolSettings();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        view.paint((Graphics2D)g);
    }
}
