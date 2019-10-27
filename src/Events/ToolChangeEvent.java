package Events;

import GUI.Components.Tools.ToolID;

/**
 * A custom type of event for tool changes.
 *
 * @see Event
 */
public class ToolChangeEvent implements Event {

    public final ToolID toolID;


    public ToolChangeEvent(ToolID toolID) {
        this.toolID = toolID;
    }

    @Override
    public String getName() {
        return "tool:change";
    }
}
