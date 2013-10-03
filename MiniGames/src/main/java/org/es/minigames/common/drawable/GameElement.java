package org.es.minigames.common.drawable;

import android.graphics.Canvas;

/**
 * Created by Cyril Leroux on 24/09/13.
 */
public interface GameElement {

    void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight);

    void draw(Canvas canvas);
}