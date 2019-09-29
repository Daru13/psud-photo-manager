package UI.Components;

import javax.swing.*;
import java.awt.image.BufferedImage;

class PhotoFrameModel {
    private BufferedImage photo;
    private boolean photoIsLoaded;

    private boolean photoIsFlipped;
    private JPanel photoBack;

    PhotoFrameModel() {
        photo = null;
        photoIsLoaded = false;

        photoIsFlipped = false;
        photoBack = new JPanel();
    }

    BufferedImage getPhoto() {
        return photo;
    }

    void setPhoto(BufferedImage photo) {
        this.photo = photo;
        this.photoIsLoaded = true;
    }

    void removeImage() {
        this.photo = null;
        this.photoIsLoaded = false;
    }

    boolean isPhotoLoaded() {
        return photoIsLoaded;
    }

    boolean isPhotoFlipped() {
        return this.photoIsFlipped;
    }

    public void switchPhotoIsFlipped() {
        photoIsFlipped = !photoIsFlipped;
    }
}
