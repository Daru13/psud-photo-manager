package GUI.Components.Tools;

import GUI.Components.Annotations.Annotation;
import GUI.Components.PhotoFrame;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.transitions.ClickOnShape;
import fr.lri.swingstates.canvas.transitions.DragOnShape;
import fr.lri.swingstates.canvas.transitions.DragOnTag;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

class SelectedTag extends CExtensionalTag {
    SelectedTag() {
        super();
    }

    public void added(CShape shape) {
        // Apply a certain style to show that the shape is part of the current selection
        shape.setOutlined(true)
            .setStroke(new BasicStroke(3))
            .setOutlinePaint(Color.BLUE);
    }

    public void removed(CShape shape) {
        shape.setOutlined(false)
            .setStroke(new BasicStroke(0));

        // Re-apply the style of the annotation to replace the style imposed the tag
        // by the (possibly new) style of the annotation
        ((Annotation<? extends CShape>) shape).applyStyleToCanvasShape();
    }
};

public class SelectionTool extends Tool {
    public final static CExtensionalTag SELECTED_TAG = new SelectedTag();

    private List<Annotation<? extends CShape>> selectedAnnotations;
    private Point lastMousePosDuringDrag;

    SelectionTool(PhotoFrame photoFrame) {
        super(photoFrame);

        selectedAnnotations = new LinkedList<>();
        lastMousePosDuringDrag = null;
    }

    private void selectAnnotation(Annotation<? extends CShape> annotation) {
        selectedAnnotations.add(annotation);
        annotation.getCanvasShape().addTag(SELECTED_TAG);
        System.out.println(annotation + " selected");
    }

    private void selectAnnotation(CShape annotationShape) {
        selectAnnotation((Annotation<? extends CShape>) annotationShape);
    }

    private void deselectAnnotation(Annotation<? extends CShape> annotation) {
        selectedAnnotations.remove(annotation);
        annotation.getCanvasShape().removeTag(SELECTED_TAG);
        System.out.println(annotation + " deselected");
    }

    private void deselectAnnotation(CShape annotationShape) {
        deselectAnnotation((Annotation<? extends CShape>) annotationShape);
    }

    private void toggleAnnotationSelection(Annotation<? extends CShape> annotation) {
        if (selectedAnnotations.contains(annotation)) {
            deselectAnnotation(annotation);
        }
        else {
            selectAnnotation(annotation);
        }
    }

    private void toggleAnnotationSelection(CShape annotationShape) {
        toggleAnnotationSelection((Annotation<? extends CShape>) annotationShape);
    }

    private void deselectAllAnnotations() {
        for (Annotation<? extends CShape> a : selectedAnnotations) {
            a.getCanvasShape().removeTag(SELECTED_TAG);
        }

        selectedAnnotations.clear();
        System.out.println("all annotations have been deselected");
    }

    private void translateSelectedAnnotations(Point mousePosition) {
        if (lastMousePosDuringDrag == null) {
            lastMousePosDuringDrag = mousePosition;
        }

        int dx = mousePosition.x - lastMousePosDuringDrag.x;
        int dy = mousePosition.y - lastMousePosDuringDrag.y;

        selectedAnnotations.forEach(a -> {
            a.getCanvasShape().translateBy(dx, dy);
        });

        lastMousePosDuringDrag = mousePosition;
    }

    @Override
    public void deselect() {
        super.deselect();
        deselectAllAnnotations();
    }

    public final State shiftUp = new State() {
        Transition shiftPress = new KeyPress("shiftDown") {
            @Override
            public boolean guard() {
                return getKeyCode() == KeyEvent.VK_SHIFT;
            }
        };

        Transition clickOnShape = new ClickOnShape(BUTTON1, "shiftUp") {
            @Override
            public void action() {
                deselectAllAnnotations();
                selectAnnotation(getShape());
            }
        };

        Transition clickOnBackground = new Click(BUTTON1, "shiftUp") {
            @Override
            public void action() {
                deselectAllAnnotations();
            }
        };

        Transition pressOnShape = new PressOnShape(BUTTON1, "shiftUp") {
            @Override
            public void action() {
                lastMousePosDuringDrag = getMouseEvent().getPoint();
            }
        };

        Transition dragOnSelectedShape = new DragOnTag(SELECTED_TAG, BUTTON1, "dragWithShiftUp") { };

        Transition dragOnShape = new DragOnShape(BUTTON1, "dragWithShiftUp") {
            @Override
            public void action() {
                deselectAllAnnotations();
                selectAnnotation(getShape());
            }
        };
    };

    public final State shiftDown = new State() {
        Transition shiftRelease = new KeyRelease("shiftUp") {
            @Override
            public boolean guard() {
                return getKeyCode() == KeyEvent.VK_SHIFT;
            }
        };

        Transition clickOnShape = new ClickOnShape(BUTTON1, "shiftDown") {
            @Override
            public void action() {
                toggleAnnotationSelection(getShape());
            }
        };

        Transition clickOnBackground = new Click(BUTTON1, "shiftDown") {
            @Override
            public void action() {
                deselectAllAnnotations();
            }
        };

        Transition pressOnShape = new PressOnShape(BUTTON1, "shiftDown") {
            @Override
            public void action() {
                lastMousePosDuringDrag = getMouseEvent().getPoint();
            }
        };

        Transition dragOnShape = new DragOnShape(BUTTON1, "dragWithShiftDown") { };
    };

    public final State dragWithShiftUp = new State() {
        Transition shiftPress = new KeyPress("dragWithShiftDown") {
            @Override
            public boolean guard() {
                return getKeyCode() == KeyEvent.VK_SHIFT;
            }
        };

        Transition clickRelease = new Release("shiftUp") { };

        Transition drag = new Drag(BUTTON1, "dragWithShiftUp") {
            @Override
            public void action() {
                Point mousePosition = getMouseEvent().getPoint();
                translateSelectedAnnotations(mousePosition);
            }
        };
    };

    public final State dragWithShiftDown = new State() {
        Transition shiftRelease = new KeyRelease("dragWithShiftUp") {
            @Override
            public boolean guard() {
                return getKeyCode() == KeyEvent.VK_SHIFT;
            }
        };

        Transition clickRelease = new Release("shiftDown") { };

        Transition drag = new Drag(BUTTON1, "dragWithShiftDown") {
            @Override
            public void action() {
                Point mousePosition = getMouseEvent().getPoint();
                translateSelectedAnnotations(mousePosition);
            }
        };
    };
}
