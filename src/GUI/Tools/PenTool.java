package GUI.Tools;

import GUI.Components.PhotoFrame;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Release;

import java.awt.*;


/**
 * A tool to freely draw using a pen.
 *
 * @see Tool
 */
public class PenTool extends Tool {
    private GUI.Annotations.Stroke annotation;

    public PenTool(PhotoFrame photoFrame) {
        super(photoFrame);
        annotation = null;
    }

    public final State waiting = new State() {
        Transition startDrawing = new Press(BUTTON1, "drawing") {
            @Override
            public void action() {
                Point origin = getMouseEvent().getPoint();
                annotation = new GUI.Annotations.Stroke(photoFrame, origin);
                photoFrame.addAnnotation(annotation);
            }
        };
    };

    public final State drawing = new State() {
        Transition draw = new Drag(BUTTON1, "drawing") {
            @Override
            public void action() {
                Point mousePosition = getMouseEvent().getPoint();
                annotation.addStep(mousePosition);
            }
        };

        Transition stopDrawing = new Release(BUTTON1, "waiting") {
            @Override
            public void action() {
                annotation = null;
            }
        };
    };
}
