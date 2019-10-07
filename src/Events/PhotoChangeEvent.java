package Events;

import Events.Event;

import java.awt.image.BufferedImage;

/**
 * A custom type of event for photo changes.
 * It should be fired whenever the photo is changed.
 *
 * @see Events.EventManager
 */
public class PhotoChangeEvent implements Event {
    public final BufferedImage photo;

    public PhotoChangeEvent(BufferedImage photo) {
        this.photo = photo;
    }

    @Override
    public String getName() {
        return "photo:change";
    }
}
