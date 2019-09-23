package Events;

/**
 * Interface of an event handler.
 * It only requires one method, which is called when the expected event is caught by the manager.
 *
 * @param <E> The type of the event to handle.
 *
 * @see EventManager
 */
public interface EventHandler<E extends Event> {
    public void handleEvent (E event);
}
