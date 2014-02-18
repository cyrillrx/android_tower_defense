package org.es.engine.graphics.drawable;

import android.graphics.Canvas;

/**
 * Interface for every elements to draw on screen.
 *
 * @author Cyril Leroux
 *         Created on 24/09/13.
 */
public interface DrawableElement {

    void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight);

    void draw(Canvas canvas);

    /** Get the abscissa of the drawable in the canvas. */
    public float getPosX();

    /** Get the ordinate of the drawable in the canvas. */
    public float getPosY();

    /** Set the position of the drawable in the canvas. */
    public void setPosition(float x, float y);

    /** Set the width of the drawable in the canvas. */
    public float getWidth();

    /** Set the height of the drawable in the canvas. */
    public float getHeight();
}