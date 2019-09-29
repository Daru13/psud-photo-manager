package UI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class PhotoFrame extends JComponent {
    private final PhotoFrameModel model;
    private final PhotoFrameView view;

    public PhotoFrame() {
        model = new PhotoFrameModel();
        view = new PhotoFrameView();

        configureComponent();
    }

    void configureComponent() {
        configureMouseListeners();
    }

    void configureMouseListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    flipPhoto();
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) { }

            @Override
            public void mouseExited(MouseEvent mouseEvent) { }
        });
    }

    public void setPhoto(BufferedImage photo) {
        model.setPhoto(photo);

        Dimension photoSize = new Dimension(photo.getWidth(), photo.getHeight());
        setPreferredSize(photoSize);

        repaint();
        revalidate();
    }

    public void removePhoto() {
        model.removeImage();
        repaint();
    }

    public void flipPhoto() {
        model.switchPhotoIsFlipped();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        view.paint((Graphics2D)g, model);
    }
}
