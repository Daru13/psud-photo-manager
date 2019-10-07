package Events;

/**
 * A custom type of event for category filter changes.
 *
 * @see Event
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
