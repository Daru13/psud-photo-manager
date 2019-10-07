package GUI.Components;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * The model of a photo frame.
 *
 * It contains the state of the photo, including the front and back images.
 *
 * @see PhotoFrame
 */
class PhotoFrameModel {

    private BufferedImage photo;
    private BufferedImage photoBack;

    private boolean photoIsLoaded;
    private boolean photoIsFlipped;


    PhotoFrameModel() {
        photo = null;
        photoBack = null;

        photoIsLoaded = false;
        photoIsFlipped = false;
    }

    BufferedImage getPhoto() {
        return photo;
    }

    void setPhoto(BufferedImage photo) {
        this.photo = photo;

        this.photoBack = new BufferedImage(photo.getWidth(), photo.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = this.photoBack.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, photoBack.getWidth(), photoBack.getHeight());

        this.photoIsLoaded = true;
    }

    BufferedImage getPhotoBack() { return photoBack; }

    boolean isPhotoLoaded() {
        return photoIsLoaded;
    }

    boolean isPhotoFlipped() {
        return this.photoIsFlipped;
    }

    void switchPhotoIsFlipped() {
        photoIsFlipped = ! photoIsFlipped;
    }


    void removePhoto() {
        this.photo = null;
        this.photoBack = null;

        this.photoIsLoaded = false;
        this.photoIsFlipped = false;
    }

}
