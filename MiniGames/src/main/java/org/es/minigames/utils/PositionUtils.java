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

    /**
     *
     * @param xa Abscissa of the first point (A).
     * @param ya Ordinate of the first point (A).
     * @param xb Abscissa of the second point (B).
     * @param yb Ordinate of the second point (B).
     * @return The distance between the two points.
     */
    public static final double distance(double xa, double ya, double xb, double yb) {
        return Math.sqrt(Math.pow(xb - xa, 2d) + Math.pow(yb - ya, 2d));
    }

    public static final double angleInRadiant(double xa, double ya, double xb, double yb) {
        return Math.atan2(yb - ya, xb - xa);
    }

    public static final double angleInDegrees(double xa, double ya, double xb, double yb) {
        return Math.toDegrees(angleInRadiant(xa, ya, xb, yb));
    }
}
