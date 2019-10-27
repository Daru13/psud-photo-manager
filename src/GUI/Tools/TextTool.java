package GUI.Tools;

import GUI.Annotations.Text;
import GUI.Components.PhotoFrame;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Click;
import fr.lri.swingstates.sm.transitions.KeyPress;
import fr.lri.swingstates.sm.transitions.KeyType;

import java.awt.event.KeyEvent;


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
public class TextTool extends Tool {
    private Text annotation;

    public TextTool(PhotoFrame photoFrame) {
        super(photoFrame);
        this.annotation = null;
    }

    @Override
    public void deselect() {
        if (annotation != null) {
            annotation.stopEditing();
        }
    }

    public final State waiting = new State() {
        Transition startEditing = new Click(BUTTON1, "editing") {
            @Override
            public void action() {
                annotation = new Text(getMouseEvent().getPoint(), photoFrame);
                photoFrame.addAnnotation(annotation);

                annotation.startEditing();
            }
        };
    };

    public final State editing = new State() {
        Transition typeKey = new KeyType("editing") {
            @Override
            public void action() {
                char character = getChar();
                if (! Character.isISOControl(character)) {
                    annotation.insertCharacter(character);
                }
            }
        };

        // Pressing Enter must be detected early since it stops the edition of the text
        Transition pressEnter = new KeyPress("waiting") {
            @Override
            public boolean guard() {
                return getKeyCode() == KeyEvent.VK_ENTER;
            }

            @Override
            public void action() {
                annotation.stopEditing();
            }
        };

        Transition pressKey = new KeyPress("editing") {
            @Override
            public void action() {
                switch (getKeyCode()) {
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
                }
            }
        };

        // Do nothing if the click happened on the text
        // Transition clickInside = new ClickOnShape(BUTTON1, "editing") { };

        // Stop editing if the click happened outside the ext
        Transition clickOutside = new Click("waiting") {
            @Override
            public void action() {
                annotation.stopEditing();
            }
        };
    };
}
