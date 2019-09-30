package UI.Components;

import UI.Tools.Rectangle;
import UI.Tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;

public class PhotoFrame extends JComponent {
    public final PhotoFrameModel model;
    public final PhotoFrameView view;

    private EnumMap<ToolID, Tool> tools;
    private ToolSettings toolSettings;
    private ToolID currentToolID;
    private Tool currentTool;

    public PhotoFrame() {
        model = new PhotoFrameModel();
        view = new PhotoFrameView();

        tools = new EnumMap<>(ToolID.class);
        toolSettings = new ToolSettings();
        currentToolID = ToolID.NONE;
        currentTool = null;

        initTools();
        configureComponent();
    }

    private void initTools() {
        tools.put(ToolID.PEN, new Pen(this));
        tools.put(ToolID.RECTANGLE, new Rectangle(this));
        tools.put(ToolID.ELLIPSIS, new Ellipsis(this));
        tools.put(ToolID.TEXT, new Text(this));

        setDefaultTool();
    }

    void configureComponent() {
        setFocusable(true);

        configureMouseListeners();
        configureMouseMotionListeners();
        configureKeyListener();
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
        int photoWidth = photo.getWidth();
        int photoHeight = photo.getHeight();

        model.setPhoto(photo);
        view.createWorkingCanvas(photoWidth, photoHeight);

        setPreferredSize(new Dimension(photoWidth, photoHeight));

        repaint();
        revalidate();
    }

    public void removePhoto() {
        model.removePhoto();
        view.removeWorkingCanvas();

        setPreferredSize(new Dimension(0, 0));

        repaint();
        revalidate();
    }

    public void flipPhoto() {
        model.switchPhotoIsFlipped();
        repaint();
    }

    public BufferedImage getWorkingCanvas() {
        return view.getWorkingCanvas();
    }

    public void clearWorkingCanvas() {
        this.view.clearWorkingCanvas();
    }

    public ToolID getTool() {
        return currentToolID;
    }

    public void setTool(ToolID toolID) {
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

    public ToolSettings getToolSettings() {
        return toolSettings;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        view.paint((Graphics2D)g, model);
    }
}
