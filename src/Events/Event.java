package Events;

/**
 * Interface of an event.
 *
 * An event is a form of message which can be exchanged between different objects of the software.
 * It allows to communicate an information without knowing
 * which other objects are interested in listening to it.
 *
 * It only requires one method, which must return the name of the event.
 * Note that events can be more complex than just a name if need be:
 * they can carry a payload, offer custom methods, etc.
 *
 * @see EventManager
 */
public interface Event {
    String getName();
}
