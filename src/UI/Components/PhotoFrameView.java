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

    private void setDefaultRenderingHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    public void paint(Graphics2D g, PhotoFrameModel model) {
        if (!model.isPhotoLoaded()) {
            return;
        }

        setDefaultRenderingHints(g);

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
