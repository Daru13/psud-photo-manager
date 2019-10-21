package GUI.Tools;

import GUI.Annotations.Text;
import GUI.Components.PhotoFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


/**
 * A tool to draw text.
 *
 * A caret is displayed at the current edit position.
 * Moreover, the text is automatically split into several lines
 * when it reaches the right edge of the photo frame.
 *
 * Certain keys have special actions (see keyPressed method).
 *
 * @see Tool
 */
public class TextTool extends ToolAdapter {

    private PhotoFrame photoFrame;
    private Text annotation;


    public TextTool(PhotoFrame photoFrame) {
        this.photoFrame = photoFrame;
        this.annotation = null;
    }

    private boolean hasEditableAnnotation() {
        return annotation != null && annotation.isEditable();
    }

    @Override
    public void toolSelected() {

    }

    @Override
    public void toolDeselected() {

    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (event.getClickCount() != 1) {
            return;
        }

        if (hasEditableAnnotation()) {
            annotation.stopEditing();
        }
        else {
            annotation = new Text(new Point(event.getX(), event.getY()), photoFrame);
            photoFrame.addAnnotation(annotation);

            annotation.startEditing();
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (! hasEditableAnnotation()) {
            return;
        }

        switch (event.getKeyCode()) {
            case KeyEvent.VK_BACK_SPACE:
                annotation.eraseCharacter();
                break;

            case KeyEvent.VK_LEFT:
                annotation.moveCaretLeft();
                break;

            case KeyEvent.VK_RIGHT:
                annotation.moveCaretRight();
                break;

            case KeyEvent.VK_DOWN:
                annotation.moveCaretBeforeFirstCharacter();
                break;

            case KeyEvent.VK_UP:
                annotation.moveCaretAfterLastCharacter();
                break;

            case KeyEvent.VK_ENTER:
                annotation.stopEditing();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
        if (! hasEditableAnnotation()) {
            return;
        }

        char character = event.getKeyChar();
        if (! Character.isISOControl(character)) {
            annotation.insertCharacter(character);
        }
    }
}
