package UI.Tools;

public enum ToolID {
    NONE(""),
    PEN("Pen"),
    TEXT("Text");

    private String toolName;

    ToolID(String toolName) {
        this.toolName = toolName;
    }

    String getName() {
        return toolName;
    }
}
