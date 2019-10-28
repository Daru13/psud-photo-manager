package GUI.Components.Tools;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CStateMachine;

/**
 * An abstract tool.
 *
 * It provides two lifecycle callbacks (select and deselect), which can be overridden if necessary.
 *
 * All tools should extend this class.
 * They can use the mechanisms provided by the CStateMachine class.
 *
 * @see CStateMachine
 * @see ToolManager
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
