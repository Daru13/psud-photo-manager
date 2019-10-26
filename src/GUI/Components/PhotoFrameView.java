package GUI.Components;

import GUI.Tools.*;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.debug.StateMachineVisualization;

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
class PhotoFrameView extends MouseAdapter {

    private PhotoFrame photoFrame;

    private EnumMap<ToolID, Tool> tools;
    private ToolSettings toolSettings;
    private ToolID currentToolID;
    private Tool currentTool;


    PhotoFrameView(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;

        tools = new EnumMap<>(ToolID.class);
        toolSettings = new ToolSettings();
        currentToolID = ToolID.NONE;
        currentTool = null;

        initTools();
    }

    private void initTools() {
        tools.put(ToolID.PEN, new PenTool(this.photoFrame));
        tools.put(ToolID.RECTANGLE, new RectangleTool(this.photoFrame));
        tools.put(ToolID.ELLIPSIS, new EllipsisTool(this.photoFrame));
        tools.put(ToolID.TEXT, new TextTool(this.photoFrame));

        Canvas annotationCanvas = photoFrame.model.getAnnotationCanvas();
        for (Tool t : tools.values()) {
            annotationCanvas.attachSM(t, true);
        }

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
            currentTool.deselect();
        }

        currentToolID = toolID;
        currentTool = tools.get(toolID);

        if (currentToolID != ToolID.NONE) {
            currentTool.select();
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
    }

    private void paintPhoto(Graphics2D g) {
        g.drawImage(photoFrame.model.getPhoto(), 0, 0, null);
    }

    private void paintAnnotations(Graphics2D g) {
        photoFrame.model.getAnnotationCanvas().paintComponent(g);
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
