package GUI.Components;

import GUI.Tools.ToolID;
import GUI.Tools.ToolSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PhotoFrame extends JComponent {
    public final PhotoFrameModel model;
    public final PhotoFrameView view;

    public PhotoFrame() {
        model = new PhotoFrameModel();
        view = new PhotoFrameView(this);

        configureComponent();
    }

    void configureComponent() {
        setFocusable(true);

        configureEventListeners();
    }

    void configureEventListeners() {
        addMouseListener(this.view);
        addMouseMotionListener(this.view);
        addKeyListener(this.view);
    }

    public BufferedImage getPhoto() {
        return this.model.getPhoto();
    }

    public BufferedImage getPhotoBack() {
        return this.model.getPhotoBack();
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

    public void flipPhoto() {
        model.switchPhotoIsFlipped();
        repaint();
    }

    public boolean isPhotoFlipped() {
        return model.isPhotoFlipped();
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
