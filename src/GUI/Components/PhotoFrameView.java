package GUI.Components;

import GUI.Tools.RectangleTool;
import GUI.Tools.*;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.Canvas;

import java.awt.*;
import java.awt.event.*;
import java.util.EnumMap;


/**
 * The view of a photo frame.
 *
 * It contains a temporary canvas and handle the tools used to annotate the photo.
 * It also handle Swing events, which and dispatched to the current tool.
 *
 * @see PhotoFrame
 */
class PhotoFrameView implements MouseListener, MouseMotionListener, KeyListener {

    private PhotoFrame photoFrame;

    private Canvas annotationCanvas;

    private EnumMap<ToolID, Tool> tools;
    private ToolSettings toolSettings;
    private ToolID currentToolID;
    private Tool currentTool;


    PhotoFrameView(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;

        annotationCanvas = new Canvas();
        annotationCanvas.setOpaque(false);
        annotationCanvas.setBackground(new Color(0, 0, 0, 0));

        tools = new EnumMap<>(ToolID.class);
        toolSettings = new ToolSettings();
        currentToolID = ToolID.NONE;
        currentTool = null;

        initTools();
    }

    void addAnnotationShape(CShape shape) {
        annotationCanvas.addShape(shape);
    }

    void removeAnnotationShape(CShape shape) {
        annotationCanvas.removeShape(shape);
    }

    void clearAnnotationCanvas() {
        annotationCanvas.removeAllShapes();
    }

    void setAnnotationCanvasSize(Dimension size) {
        annotationCanvas.setSize(size);
    }

    private void initTools() {
        tools.put(ToolID.PEN, new PenTool(this.photoFrame));
        tools.put(ToolID.RECTANGLE, new RectangleTool(this.photoFrame));
        tools.put(ToolID.ELLIPSIS, new EllipsisTool(this.photoFrame));
        tools.put(ToolID.TEXT, new TextTool(this.photoFrame));

        setDefaultTool();
    }

    ToolID getTool() {
        return currentToolID;
    }

    void setTool(ToolID toolID) {
        if (currentToolID == toolID) {
            return;
        }

        if (! tools.containsKey(toolID)) {
            return;
        }

        if (currentToolID != ToolID.NONE) {
            currentTool.toolDeselected();
        }

        currentToolID = toolID;
        currentTool = tools.get(toolID);

        if (currentToolID != ToolID.NONE) {
            currentTool.toolSelected();
        }
    }

    private void setDefaultTool() {
        setTool(ToolID.PEN);
    }

    ToolSettings getToolSettings() {
        return toolSettings;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            photoFrame.switchAnnotableState();
        }

        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.mouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.mouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.mouseEntered(e);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.mouseExited(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.mouseMoved(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (currentToolID != ToolID.NONE && photoFrame.isAnnotable()) {
            currentTool.keyReleased(e);
        }
    }

    private void paintPhoto(Graphics2D g) {
        g.drawImage(photoFrame.model.getPhoto(), 0, 0, null);
    }

    private void paintAnnotations(Graphics2D g) {
        annotationCanvas.paintComponent(g);
    }

    private void setDefaultRenderingHints(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    void paint(Graphics2D g) {
        if (! photoFrame.model.isPhotoLoaded()) {
            return;
        }

        setDefaultRenderingHints(g);
        paintPhoto(g);
        paintAnnotations(g);
    }
}
