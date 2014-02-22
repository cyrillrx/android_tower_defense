package org.es.minigames.utils;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by Cyril Leroux on 24/09/13.
 */
public class PositionUtils {

    /**
     * This method indicates whether a point is within a rectangle or not.
     *
     * @param rect The rectangle in which to look for the point.
     * @param point The point.
     * @return True if the point is in the rectangle.
     */
    public static final boolean pointIsInRect(RectF rect, PointF point) {
        return (point.x >= rect.left &&
                point.x <= rect.right &&
                point.y >= rect.top &&
                point.y <= rect.bottom);
    }

    /**
     * Calculate the distance between two points.
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

    /**
     * Return the angle between points A and B using trigonometry.<br />
     * A being the center of the unit circle.
     *
     * @param xa The abscissa of point A.
     * @param ya The ordinate of point A.
     * @param xb The abscissa of point B.
     * @param yb The ordinate of point B.
     * @param reverse True if the ordinates goes down instead of up. False otherwise.
     * @return The angle in radiant.
     */
    public static final double angleInRadiant(double xa, double ya, double xb, double yb, boolean reverse) {
        if (reverse) {
            return Math.atan2(ya - yb, xb - xa);
        }
        // Standard formula
        return Math.atan2(yb - ya, xb - xa);
    }

    /**
     * Return the angle between points A and B using trigonometry.<br />
     * A being the center of the unit circle.
     *
     * @param xa The abscissa of point A.
     * @param ya The ordinate of point A.
     * @param xb The abscissa of point B.
     * @param yb The ordinate of point B.
     * @param reverse True if the ordinates goes down instead of up. False otherwise.
     * @return The angle in degrees.
     */
    public static final double angleInDegrees(double xa, double ya, double xb, double yb, boolean reverse) {
        return Math.toDegrees(angleInRadiant(xa, ya, xb, yb, reverse));
    }

    /**
     * Return the angle between points A and B using trigonometry.<br />
     * A being the center of the unit circle.
     *
     * @param xa The abscissa of point A.
     * @param ya The ordinate of point A.
     * @param xb The abscissa of point B.
     * @param yb The ordinate of point B.
     * @return The angle in radiant.
     */
    public static final double angleInDegrees(double xa, double ya, double xb, double yb) {
        return angleInDegrees(xa, ya, xb, yb, false);
    }

    /**
     * Convert polar coordinates to cartesian coordinates.
     *
     * @param originX The polar abscissa of the pole.
     * @param originY The polar ordinate of the pole.
     * @param angle The angle
     * @param radius The distance from the pole (called the radial coordinate or radius
     * @param reverse True if the ordinates goes down instead of up. False otherwise.
     * @return The cartesian coordinates.
     */
    public PointF polarToCartesian(float originX, float originY, double angle, float radius, boolean reverse) {
        float diffX = (float) (radius * Math.cos(angle));
        float diffY = (float) (radius * Math.sin(angle));
        if (reverse) {
            return new PointF(originX + diffX, originY - diffY);
        }
        // Standard formula
        return new PointF(originX + diffX, originY + diffY);
    }

    /**
     * Convert polar coordinates to cartesian coordinates.
     *
     * @param originX The polar abscissa of the pole.
     * @param originY The polar ordinate of the pole.
     * @param angle The angle
     * @param radius The distance from the pole (called the radial coordinate or radius
     * @return The cartesian coordinates.
     */
    public PointF polarToCartesian(float originX, float originY, double angle, float radius) {
        return polarToCartesian(originX, originY, angle, radius, false);
    }
}
