package GUI.Components.Annotations;

import fr.lri.swingstates.canvas.CShape;

/**
 * Interface of a photo annotation.
 *
 * An annotation can be added to and removed from a photo frame.
 * They are associated with a shape, which can be drawn on top of the photo.
 *
 * @param <S> The shape associated with the association.
 */
public interface Annotation<S extends CShape> {
    /**
     * Return the shape associated with the association.
     */
    S getCanvasShape();

    /**
     * Apply the style of the annotation to its associated shape.
     */
    void applyStyleToCanvasShape();

    /**
     * Update the style of the annotation from the shared tool settings.
     */
    void updateStyleFromToolSettings();
}
