package org.es.gameengine.drawable;

import android.graphics.Canvas;

/**
 * Interface for every elements to draw on screen.
 * <p/>
 * Created by Cyril Leroux on 24/09/13.
 */
public interface DrawableElement {

    void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight);

    void draw(Canvas canvas);
}