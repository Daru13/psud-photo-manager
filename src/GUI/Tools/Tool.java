package GUI.Tools;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


/**
 * Interface of a tool.
 *
 * It requires two methods, which are lifecycle callbacks.
 * They should be used to perform certain actions before or after the tools can be used.
 */
public interface Tool extends MouseListener, MouseMotionListener, KeyListener {
    void toolSelected();
    void toolDeselected();
}
