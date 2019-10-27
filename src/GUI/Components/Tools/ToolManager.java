package GUI.Components.Tools;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.Canvas;

import java.util.EnumMap;

public class ToolManager {
    private PhotoFrame photoFrame;
    private boolean enabled;

    private EnumMap<ToolID, Tool> tools;
    private ToolID currentToolID;
    private Tool currentTool;

    private ToolSettings toolSettings;

    public ToolManager(PhotoFrame photoFrame, Canvas annotationCanvas) {
        this.photoFrame = photoFrame;
        enabled = photoFrame.isAnnotable();

        tools = new EnumMap<>(ToolID.class);
        currentToolID = ToolID.NONE;
        currentTool = null;

        toolSettings = new ToolSettings();

        initTools(annotationCanvas);
    }

    private void initTools(Canvas annotationCanvas) {
        tools.put(ToolID.SELECTION, new SelectionTool(this.photoFrame));
        tools.put(ToolID.PEN, new PenTool(this.photoFrame));
        tools.put(ToolID.RECTANGLE, new RectangleTool(this.photoFrame));
        tools.put(ToolID.ELLIPSE, new EllipseTool(this.photoFrame));
        tools.put(ToolID.TEXT, new TextTool(this.photoFrame));

        for (Tool t : tools.values()) {
            annotationCanvas.attachSM(t, true);
            t.setActive(false);
        }

        setDefaultTool();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (currentToolID != ToolID.NONE) {
            currentTool.setActive(enabled);
        }
    }

    public void toggleEnabled() {
        setEnabled(!enabled);
    }

    public ToolID getCurrentToolID() {
        return currentToolID;
    }

    public void setTool(ToolID toolID) {
        if (currentToolID == toolID
        ||  ! tools.containsKey(toolID)) {
            return;
        }

        if (currentToolID != ToolID.NONE) {
            currentTool.deselect();
            currentTool.setActive(false);
        }

        currentToolID = toolID;
        currentTool = tools.get(toolID);

        if (currentToolID != ToolID.NONE) {
            currentTool.select();
            currentTool.setActive(enabled);
        }
    }

    private void setDefaultTool() {
        setTool(ToolID.PEN);
    }

    public ToolSettings getToolSettings() {
        return toolSettings;
    }
}
