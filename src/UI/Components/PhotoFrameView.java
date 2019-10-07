package UI.Components;

import UI.Tools.Rectangle;
import UI.Tools.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;

class PhotoFrameView implements MouseListener, MouseMotionListener, KeyListener {

    private PhotoFrame photoFrame;

    private BufferedImage workingCanvas;

    private EnumMap<ToolID, Tool> tools;
    private ToolSettings toolSettings;
    private ToolID currentToolID;
    private Tool currentTool;

    public PhotoFrameView(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;

        workingCanvas = null;

        tools = new EnumMap<>(ToolID.class);
        toolSettings = new ToolSettings();
        currentToolID = ToolID.NONE;
        currentTool = null;

        initTools();
    }

    private void initTools() {
        tools.put(ToolID.PEN, new Pen(this.photoFrame));
        tools.put(ToolID.RECTANGLE, new Rectangle(this.photoFrame));
        tools.put(ToolID.ELLIPSIS, new Ellipsis(this.photoFrame));
        tools.put(ToolID.TEXT, new Text(this.photoFrame));

        setDefaultTool();
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

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            photoFrame.flipPhoto();
        }

        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.mouseExited(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.mouseMoved(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isPhotoFlipped()) {
            currentTool.keyReleased(e);
        }
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

    public void paint(Graphics2D g) {
        if (!photoFrame.model.isPhotoLoaded()) {
            return;
        }

        setDefaultRenderingHints(g);

        // Either draw the photo or the photo back + the working canvas at its back
        if (photoFrame.isPhotoFlipped()) {
            paintPhotoBack(g, photoFrame.model.getPhotoBack());
            paintWorkingCanvas(g);
        }
        else {
            paintPhoto(g, photoFrame.model.getPhoto());
        }
    }
}
