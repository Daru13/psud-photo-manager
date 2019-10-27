package GUI.Components.Tools;


/**
 * Unique identifiers for the tools.
 *
 * Each available tool must have an identifier here.
 * Each identifier contains the human-readable name of the tool.
 * The NONE value is reserved to indicate an absence of tool.
 */
public enum ToolID {
    NONE(""),
    SELECTION("Selection"),
    PEN("Pen"),
    RECTANGLE("Rectangle"),
    ELLIPSE("Ellipse"),
    TEXT("Text");

    private String toolName;

    ToolID(String toolName) {
        this.toolName = toolName;
    }

    public String getName() {
        return toolName;
    }
}
