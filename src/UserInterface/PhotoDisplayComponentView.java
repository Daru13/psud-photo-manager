package UserInterface;

import java.awt.*;
import java.awt.image.BufferedImage;

class PhotoDisplayComponentView {

    public PhotoDisplayComponentView() {

    }

    public void paintPhoto(Graphics2D g, BufferedImage photo) {
        g.drawImage(photo, 0, 0, null);
    }

    public void paintCanvas(Graphics2D g, PhotoDisplayComponentModel model) {
        BufferedImage photo = model.getPhoto();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, photo.getWidth(), photo.getHeight());
    }

    public void paint(Graphics2D g, PhotoDisplayComponentModel model) {
        if (!model.isPhotoLoaded()) {
            return;
        }

        // Either draw the photo or the canvas at its back
        if (model.isPhotoFlipped()) {
            paintCanvas(g, model);
        }
        else {
            paintPhoto(g, model.getPhoto());
        }
    }
}
