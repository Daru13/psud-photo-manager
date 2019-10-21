package GUI.Components;

import GUI.Annotations.Annotation;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;


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

    private List<Annotation> annotations;
    private boolean annotable;


    PhotoFrameModel() {
        photo = null;
        photoIsLoaded = false;

        annotations = new LinkedList<>();
        annotable = false;
    }

    BufferedImage getPhoto() {
        return photo;
    }

    void setPhoto(BufferedImage photo) {
        this.photo = photo;
        this.photoIsLoaded = true;

        this.annotations.clear();
    }

    void removePhoto() {
        this.photo = null;
        this.photoIsLoaded = false;

        this.annotations.clear();
    }

    boolean isPhotoLoaded() {
        return photoIsLoaded;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    void addAnnotation(Annotation annotation) {
        this.annotations.add(annotation);
    }

    void removeAnnotation(Annotation annotation) {
        this.annotations.remove(annotation);
    }

    boolean isAnnotable() {
        return this.annotable;
    }

    void setAnnotable(boolean annotable) {
        this.annotable = annotable;
    }
}
