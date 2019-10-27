package GUI.Components.Annotations;

import fr.lri.swingstates.canvas.CShape;

public interface Annotation<S extends CShape> {
    S getCanvasShape();

    void applyStyleToCanvasShape();
    void updateStyleFromToolSettings();
}
