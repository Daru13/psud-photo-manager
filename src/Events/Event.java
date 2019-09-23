package Events;

/**
 * Interface of an event.
 * It only requires one method, which must return the name of the event.
 *
 * Note that events can be more complex than just a name if need be:
 * they can carry a payload, offer custom methods, etc.
 *
 * @see EventManager
 */
public interface Event {
    public String getName();
}
