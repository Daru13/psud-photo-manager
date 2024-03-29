package GUI.Components;

import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


/**
 * The view of a photo frame.
 *
 * It contains a Canvas which handles the annotation shapes
 * and handles the double-click events required to toggle the annotation mode.
 *
 * @see PhotoFrame
 */
class PhotoFrameView extends MouseAdapter {

    private PhotoFrame photoFrame;
    private Canvas annotationCanvas;

    PhotoFrameView(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;
        annotationCanvas = new Canvas();

        initAnnotationCanvas();
        initMouseListeners();
    }

    private void initAnnotationCanvas() {
        annotationCanvas.setOpaque(false);
        annotationCanvas.setBackground(new Color(0, 0, 0, 0));

        photoFrame.add(annotationCanvas);
    }

    private void initMouseListeners() {
        photoFrame.addMouseListener(this);
        annotationCanvas.addMouseListener(this);
    }

    Canvas getAnnotationCanvas() {
        return annotationCanvas;
    }

    void addAnnotationShape(CShape shape) {
        annotationCanvas.addShape(shape);
    }

    void removeAnnotationShape(CShape shape) {
        annotationCanvas.removeShape(shape);
    }

    void updateSize() {
        Dimension newSize;

        if (photoFrame.model.isPhotoLoaded()) {
            BufferedImage photo = photoFrame.model.getPhoto();
            newSize = new Dimension(photo.getWidth(), photo.getHeight());

            annotationCanvas.setSize(newSize);
            annotationCanvas.setVisible(true);

            photoFrame.setPreferredSize(newSize);
        }
        else {
            newSize = new Dimension(0, 0);

            annotationCanvas.setSize(newSize);
            annotationCanvas.setVisible(false);

            photoFrame.setPreferredSize(newSize);
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            photoFrame.toggleAnnotable();
        }
    }

    private void paintPhoto(Graphics2D g) {
        g.drawImage(photoFrame.model.getPhoto(), 0, 0, null);
    }

    private void setDefaultRenderingHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    void paint(Graphics2D g) {
        if (! photoFrame.model.isPhotoLoaded()) {
            return;
        }

        setDefaultRenderingHints(g);
        paintPhoto(g);
        annotationCanvas.setVisible(photoFrame.model.isAnnotable());
    }
}
