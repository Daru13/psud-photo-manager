package GUI.Tools;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.Canvas;

import java.util.EnumMap;

public class ToolManager {
    private PhotoFrame photoFrame;

    private EnumMap<ToolID, Tool> tools;
    private ToolID currentToolID;
    private Tool currentTool;

    private ToolSettings toolSettings;

    public ToolManager(PhotoFrame photoFrame, Canvas annotationCanvas) {
        this.photoFrame = photoFrame;

        tools = new EnumMap<>(ToolID.class);
        currentToolID = ToolID.NONE;
        currentTool = null;

        toolSettings = new ToolSettings();

        initTools(annotationCanvas);
    }

    private void initTools(Canvas annotationCanvas) {
        tools.put(ToolID.PEN, new PenTool(this.photoFrame));
        tools.put(ToolID.RECTANGLE, new RectangleTool(this.photoFrame));
        tools.put(ToolID.ELLIPSIS, new EllipsisTool(this.photoFrame));
        tools.put(ToolID.TEXT, new TextTool(this.photoFrame));

        for (Tool t : tools.values()) {
            annotationCanvas.attachSM(t, true);
        }

        setDefaultTool();
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

    public ToolSettings getToolSettings() {
        return toolSettings;
    }
}
