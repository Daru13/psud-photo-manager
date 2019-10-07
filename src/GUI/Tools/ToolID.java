package GUI.Tools;

public enum ToolID {
    NONE(""),
    PEN("Pen"),
    RECTANGLE("Rectangle"),
    ELLIPSIS("Ellipsis"),
    TEXT("Text");

    private String toolName;

    ToolID(String toolName) {
        this.toolName = toolName;
    }

    public String getName() {
        return toolName;
    }
}
