package org.es.minigames.common.drawelement;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Cyril Leroux on 24/09/13.
 */
public interface GameElement {

    void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight);

    void draw(Canvas canvas);
}
