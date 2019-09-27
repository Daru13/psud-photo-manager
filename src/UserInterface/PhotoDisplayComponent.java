package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

class PhotoDisplayComponent extends JComponent {
    private final PhotoDisplayComponentModel model;
    private final PhotoDisplayComponentView view;

    public PhotoDisplayComponent() {
        model = new PhotoDisplayComponentModel();
        view = new PhotoDisplayComponentView();

        configureComponent();
    }

    void configureComponent() {
        setBackground(Color.red);
        configureMouseListeners();
    }

    void configureMouseListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse click on photo display: " + e.getClickCount());

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
            public void mouseEntered(MouseEvent mouseEvent) {
                System.out.println("entered");
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    public void setPhoto(BufferedImage photo) {
        model.setPhoto(photo);

        Dimension photoSize = new Dimension(photo.getWidth(), photo.getHeight());
        setPreferredSize(photoSize);

        System.out.println(photoSize);

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
        view.paint((Graphics2D) g, model);
    }
}
