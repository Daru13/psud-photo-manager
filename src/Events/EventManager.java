package Events;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The custom event manager of the application.
 *
 * Emitted events (Event) trigger handlers (EventHandler) which expect them.
 *
 * This manager allows different parts of the application to communicate through events,
 * such as two unconnected elements of the GUI, or some GUI element and the related back-end object.
 *
 * @see Event
 * @see EventHandler
 */
public class EventManager {

    private Map<String, LinkedList<EventHandler>> eventNamesToHandlers;


    public EventManager() {
        eventNamesToHandlers = new LinkedHashMap<>();
    }

    public <E extends Event> void emit(E event) {
        handle(event);
    }

    public void emit(String name) {
        emit(() -> name);
    }

    private <E extends Event> void handle(E event) {
        String eventName = event.getName();
        if (! eventNamesToHandlers.containsKey(eventName)) {
            return;
        }

        List<EventHandler> handlers = eventNamesToHandlers.get(eventName);
        for (EventHandler handler : handlers) {
            handler.handleEvent(event);
        }
    }

    public <E extends Event> void addHandler(String eventName, EventHandler<E> handler) {
        if (! eventNamesToHandlers.containsKey(eventName)) {
            eventNamesToHandlers.put(eventName, new LinkedList<>());
        }

        eventNamesToHandlers
                .get(eventName)
                .add(handler);
    }

    public <E extends Event> void removeHandler(String eventName, EventHandler<E> handler) {
        if (! eventNamesToHandlers.containsKey(eventName)) {
            return;
        }

        LinkedList<EventHandler> handlers = eventNamesToHandlers.get(eventName);
        handlers.removeLastOccurrence(handler);

        // If there is no more handler in the linked list, it can safely be removed from the map
        if (handlers.isEmpty()) {
            eventNamesToHandlers.remove(eventName);
        }
    }
}
