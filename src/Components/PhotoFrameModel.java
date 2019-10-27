package Components;

import Components.Annotations.Annotation;
import fr.lri.swingstates.canvas.CShape;

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

    private boolean annotable;
    private List<Annotation<? extends CShape>> annotations;

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
    }

    void removePhoto() {
        this.photo = null;
        this.photoIsLoaded = false;
    }

    boolean isPhotoLoaded() {
        return photoIsLoaded;
    }

    List<Annotation<? extends CShape>> getAnnotations() {
        return annotations;
    }

    <S extends CShape> void addAnnotation(Annotation<S> annotation) {
        this.annotations.add(annotation);
    }

    <S extends CShape> void removeAnnotation(Annotation<S> annotation) {
        this.annotations.remove(annotation);
    }

    boolean isAnnotable() {
        return this.annotable;
    }

    void setAnnotable(boolean annotable) {
        this.annotable = annotable;
    }

    void toggleAnnotable() {
        annotable = !annotable;
    }
}
