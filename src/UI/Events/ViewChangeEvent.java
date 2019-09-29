package UI.Events;

import Events.Event;
import UI.Views.ViewID;

/**
 * A custom type of event for view changes.
 * It should be fired whenever the view is about to change.
 *
 * @see Events.EventManager
 */
public class ViewChangeEvent implements Event {
    public final ViewID nextView;

    public ViewChangeEvent(ViewID nextView) {
        this.nextView = nextView;
    }

    @Override
    public String getName() {
        return "view:change";
    }
}
