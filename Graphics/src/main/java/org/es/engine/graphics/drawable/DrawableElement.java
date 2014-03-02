package org.es.engine.graphics.drawable;

import android.graphics.Canvas;

import org.es.engine.graphics.utils.DrawingParam;

/**
 * Interface for every elements to draw on screen.
 *
 * @author Cyril Leroux
 *         Created on 24/09/13.
 */
public interface DrawableElement {

    void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight);

    void draw(Canvas canvas, DrawingParam param);

    /** Get the abscissa of the drawable in the canvas. */
    float getPosX();

    /** Get the ordinate of the drawable in the canvas. */
    float getPosY();

    /** Set the position of the drawable in the canvas. */
    void setPosition(float x, float y);

    /** Offset the position by adding dx and dy to its coordinates. */
    void offsetPosition(float dx, float dy);

    /** Set the width of the drawable in the canvas. */
    float getWidth();

    /** Set the height of the drawable in the canvas. */
    float getHeight();
}