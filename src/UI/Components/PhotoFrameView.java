package UI.Components;

import java.awt.*;
import java.awt.image.BufferedImage;

class PhotoFrameView {

    public PhotoFrameView() {

    }

    public void paintPhoto(Graphics2D g, BufferedImage photo) {
        g.drawImage(photo, 0, 0, null);
    }

    public void paintPhotoBack(Graphics2D g, BufferedImage photoBack) {
        g.drawImage(photoBack, 0, 0, null);
    }

    public void paint(Graphics2D g, PhotoFrameModel model) {
        if (!model.isPhotoLoaded()) {
            return;
        }

        // Either draw the photo or the canvas at its back
        if (model.isPhotoFlipped()) {
            paintPhotoBack(g, model.getPhotoBack());
        }
        else {
            paintPhoto(g, model.getPhoto());
        }
    }
}
