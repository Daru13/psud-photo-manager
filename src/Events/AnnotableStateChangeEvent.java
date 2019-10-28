package Events;

/**
 * A custom type of event for a change of the annotation state of a PhotoFrame instance.
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

