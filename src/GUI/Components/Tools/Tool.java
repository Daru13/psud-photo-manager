package GUI.Components.Tools;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CStateMachine;

/**
 * Interface of a tool.
 *
 * It requires two methods, which are lifecycle callbacks.
 * They should be used to perform certain actions before or after the tools can be used.
 */
public abstract class Tool extends CStateMachine {
    PhotoFrame photoFrame;

    Tool(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;
        suspend();
    }

    public void select() {
        resume();
    }

    public void deselect() {
        suspend();
    }
}
