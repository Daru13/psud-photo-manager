package Events;

/**
 * A custom type of event for annotable state changes.
 *
 * @see Event
 */
public class AnnotableStateChangeEvent implements Event {

    public final boolean isAnnotable;


    public AnnotableStateChangeEvent(boolean isAnnotable) {
        this.isAnnotable = isAnnotable;
    }

    @Override
    public String getName() {
        return "photo:annotable:change";
    }
}

