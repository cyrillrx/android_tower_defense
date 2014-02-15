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
}