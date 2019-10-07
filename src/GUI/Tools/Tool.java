package GUI.Tools;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface Tool extends MouseListener, MouseMotionListener, KeyListener {
    void toolSelected();
    void toolDeselected();
}
