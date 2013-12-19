package org.es.minigames.utils;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Cyril Leroux on 24/09/13.
 */
public class PositionUtils {

    public static final boolean pointIsInRect(RectF rect, PointF point) {
        return (point.x >= rect.left &&
                point.x <= rect.right &&
                point.y >= rect.top &&
                point.y <= rect.bottom);
    }
}
