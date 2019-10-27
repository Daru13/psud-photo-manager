package Events;

import GUI.Views.ViewID;

/**
 * A custom type of event for view changes.
 *
 * @see Event
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
