package org.es.utils;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Class for position utils.
 *
 * @author Cyril Leroux
 *         Created 24/09/13.
 */
public class PositionUtils {

    /**
     * Indicates whether a point is within a rectangle or not.
     *
     * @param rect  The rectangle in which to look for the point.
     * @param point The point.
     * @return True if the point is in the rectangle.
     */
    public static boolean pointIsInRect(RectF rect, PointF point) {
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
    public static double distance(double xa, double ya, double xb, double yb) {
        return Math.sqrt(Math.pow(xb - xa, 2d) + Math.pow(yb - ya, 2d));
    }

    /**
     * Return the angle between points A and B using trigonometry.<br />
     * A being the center of the unit circle.
     *
     * @param xa      The abscissa of point A.
     * @param ya      The ordinate of point A.
     * @param xb      The abscissa of point B.
     * @param yb      The ordinate of point B.
     * @param reverse True if the ordinates goes down instead of up. False otherwise.
     * @return The angle in radiant.
     */
    public static double angleInRadiant(double xa, double ya, double xb, double yb, boolean reverse) {
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
     * @param xa      The abscissa of point A.
     * @param ya      The ordinate of point A.
     * @param xb      The abscissa of point B.
     * @param yb      The ordinate of point B.
     * @param reverse True if the ordinates goes down instead of up. False otherwise.
     * @return The angle in degrees.
     */
    public static double angleInDegrees(double xa, double ya, double xb, double yb, boolean reverse) {
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
    public static double angleInDegrees(double xa, double ya, double xb, double yb) {
        return angleInDegrees(xa, ya, xb, yb, false);
    }

    /**
     * Convert polar coordinates to cartesian coordinates.
     *
     * @param angle   The angle in degrees.
     * @param radius  The distance from the pole.
     * @param reverse True if the ordinates goes down instead of up. False otherwise.
     * @return The cartesian coordinates.
     */
    public static PointF polarToCartesian(double angle, float radius, boolean reverse) {
        double angleInRadians = Math.toRadians(angle);
        float x = Math.round(radius * Math.cos(angleInRadians) * 100f) / 100f;
        float y = Math.round(radius * Math.sin(angleInRadians) * 100f) / 100f;
        if (reverse) {
            return new PointF(x, y * -1f);
        }
        // Standard formula
        return new PointF(x, y);
    }

    /**
     * Convert polar coordinates to cartesian coordinates.
     *
     * @param angle  The angle
     * @param radius The distance from the pole (called the radial coordinate or radius
     * @return The cartesian coordinates.
     */
    public static PointF polarToCartesian(double angle, float radius) {
        return polarToCartesian(angle, radius, false);
    }

    /**
     * Indicates whether an angle is within a range or not.
     *
     * @param angle         The angle to check in degrees.
     *                      Can be in the range [-180, 180] or [0, 360].
     * @param bisectorAngle The bisector of the range.
     * @param range         the range in degrees.
     * @return True if the angle is in the specified range. False otherwise.
     */
    public static boolean angleInRange(double angle, double bisectorAngle, double range) {
        if (angle < 0) {
            angle += 360.0;
        }

        final double lowerBound = bisectorAngle - range / 2.0;
        final double upperBound = bisectorAngle + range / 2.0;

        if (lowerBound < 0) {
            return angle > lowerBound + 360 || angle < upperBound;
        }

        return angle > lowerBound && angle < upperBound;
    }
}
