package UI.Components;

import java.awt.*;
import java.awt.image.BufferedImage;

class PhotoFrameView {

    private BufferedImage workingCanvas;

    public PhotoFrameView() {
        workingCanvas = null;
    }

    void createWorkingCanvas(int width, int height) {
        workingCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = workingCanvas.createGraphics();
        //g.setBackground(Color.WHITE);
        //g.clearRect(0, 0, photoBack.getWidth(), photoBack.getHeight());
    }

    BufferedImage getWorkingCanvas() {
        return workingCanvas;
    }

    void clearWorkingCanvas() {
        if (workingCanvas != null) {
            Graphics2D g = (Graphics2D)workingCanvas.getGraphics();
            g.setBackground(new Color(0, 0, 0, 0));
            g.clearRect(0, 0, workingCanvas.getWidth(), workingCanvas.getHeight());
        }
    }

    void removeWorkingCanvas() {
        workingCanvas = null;
    }

    public void paintPhoto(Graphics2D g, BufferedImage photo) {
        g.drawImage(photo, 0, 0, null);
    }

    public void paintPhotoBack(Graphics2D g, BufferedImage photoBack) {
        g.drawImage(photoBack, 0, 0, null);
    }

    public void paintWorkingCanvas(Graphics2D g) {
        g.drawImage(workingCanvas, 0, 0, null);
    }


    public void paint(Graphics2D g, PhotoFrameModel model) {
        if (!model.isPhotoLoaded()) {
            return;
        }

        // Either draw the photo or the photo back + the working canvas at its back
        if (model.isPhotoFlipped()) {
            paintPhotoBack(g, model.getPhotoBack());
            paintWorkingCanvas(g);
        }
        else {
            paintPhoto(g, model.getPhoto());
        }
    }
}
