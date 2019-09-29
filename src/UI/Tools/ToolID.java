package UI.Tools;

public enum ToolID {
    NONE(""),
    PEN("Pen"),
    TEXT("Text");

    private String toolName;

    ToolID(String toolName) {
        this.toolName = toolName;
    }

    public String getName() {
        return toolName;
    }
}
