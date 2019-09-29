package UI.Components;

import UI.Tools.Pen;
import UI.Tools.Text;
import UI.Tools.Tool;
import UI.Tools.ToolID;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;

public class PhotoFrame extends JComponent {
    public final PhotoFrameModel model;
    public final PhotoFrameView view;

    private EnumMap<ToolID, Tool> tools;
    private ToolID currentToolID;
    private Tool currentTool;

    public PhotoFrame() {
        model = new PhotoFrameModel();
        view = new PhotoFrameView();

        tools = new EnumMap<>(ToolID.class);
        currentToolID = ToolID.NONE;
        currentTool = null;

        initTools();
        configureComponent();
    }

    private void initTools() {
        tools.put(ToolID.PEN, new Pen(this));
        tools.put(ToolID.TEXT, new Text(this));

        setDefaultTool();
    }

    void configureComponent() {
        configureMouseListeners();
        configureMouseMotionListeners();
    }

    void configureMouseListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    flipPhoto();
                }

                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.mouseClicked(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.mousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.mouseReleased(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.mouseEntered(e);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.mouseExited(e);
                }
            }
        });
    }

    void configureMouseMotionListeners() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.mouseDragged(e);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.mouseMoved(e);
                }
            }
        });
    }

    void configureKeyListener() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.keyTyped(e);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.keyPressed(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (currentToolID != ToolID.NONE && model.isPhotoFlipped()) {
                    currentTool.keyReleased(e);
                }
            }
        });
    }

    public BufferedImage getPhoto() {
        return this.model.getPhoto();
    }

    public BufferedImage getPhotoBack() {
        return this.model.getPhotoBack();
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

    public ToolID getTool() {
        return currentToolID;
    }

    void setTool(ToolID toolID) {
        if (currentToolID == toolID) {
            return;
        }

        if (!tools.containsKey(toolID)) {
            return;
        }

        currentToolID = toolID;
        currentTool = tools.get(toolID);
    }

    private void setDefaultTool() {
        setTool(ToolID.PEN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        view.paint((Graphics2D)g, model);
    }
}
