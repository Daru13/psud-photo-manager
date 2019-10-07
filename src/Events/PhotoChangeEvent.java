package Events;

import java.awt.image.BufferedImage;

/**
 * A custom type of event for photo changes.
 *
 * @see Event
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
