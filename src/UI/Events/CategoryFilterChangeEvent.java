package UI.Events;

import Events.Event;

/**
 * A custom type of event for category filter changes.
 * It should be fired whenever a category filter has been updated.
 *
 * @see Events.EventManager
 */
public class CategoryFilterChangeEvent implements Event {
    public final String category;
    public final boolean isEnabled;

    public CategoryFilterChangeEvent(String category, boolean isEnabled) {
        this.category = category;
        this.isEnabled = isEnabled;
    }

    @Override
    public String getName() {
        return "filter:category:change";
    }
}
