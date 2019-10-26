package GUI.Components;

import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * The model of a photo frame.
 *
 * It contains the photo and its annotations.
 *
 * @see PhotoFrame
 */
class PhotoFrameModel {

    private BufferedImage photo;
    private boolean photoIsLoaded;

    private boolean annotable;
    private Canvas annotationCanvas;


    PhotoFrameModel() {
        photo = null;
        photoIsLoaded = false;

        annotationCanvas = new Canvas();
        annotationCanvas.setOpaque(false);
        annotationCanvas.setBackground(new Color(0, 0, 0, 0));

        annotable = true;
    }

    BufferedImage getPhoto() {
        return photo;
    }

    void setPhoto(BufferedImage photo) {
        this.photo = photo;
        this.photoIsLoaded = true;

        Dimension photoDimensions = new Dimension(photo.getWidth(), photo.getHeight());
        annotationCanvas.setSize(photoDimensions);
    }

    void removePhoto() {
        this.photo = null;
        this.photoIsLoaded = false;

        annotationCanvas.removeAllShapes();
    }

    boolean isPhotoLoaded() {
        return photoIsLoaded;
    }

    Canvas getAnnotationCanvas() {
        return annotationCanvas;
    }

    void addAnnotation(CShape annotation) {
        this.annotationCanvas.addShape(annotation);
    }

    void removeAnnotation(CShape annotation) {
        this.annotationCanvas.removeShape(annotation);
    }

    boolean isAnnotable() {
        return this.annotable;
    }

    void setAnnotable(boolean annotable) {
        this.annotable = annotable;
    }
}
