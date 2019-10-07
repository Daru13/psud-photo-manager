package Events;

import Events.Event;
import UI.Tools.ToolID;

/**
 * A custom type of event for tool changes.
 * It should be fired whenever the tool is changed.
 *
 * @see Events.EventManager
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
