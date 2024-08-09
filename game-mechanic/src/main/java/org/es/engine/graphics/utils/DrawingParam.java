package org.es.engine.graphics.utils;

import android.graphics.PointF;

/**
 * @author Cyril Leroux
 *         Created on 16/02/14.
 */
public class DrawingParam {

    private float mCoefficient;
    private final PointF mOffset;

    /** Maximum value for x or y offset. */
    private static final float MAX_OFFSET = 0f;

    private float mViewportWidth;
    private float mViewportHeight;

    private float mDrawWidth;
    private float mDrawHeight;

    public DrawingParam() {
        mCoefficient = 1f;
        mOffset = new PointF();
    }

    public float coef() { return mCoefficient; }

    public void setCoef(float coefficient) {
        mCoefficient = coefficient;
        fixOffsetIntegrity();
    }

    public void setCoef(float coefficient, float dx, float dy) {
        mCoefficient = coefficient;
        mOffset.x += dx;
        mOffset.y += dy;
        fixOffsetIntegrity();
    }

    public void setViewport(float viewportWidth, float viewportHeight) {
        mViewportWidth = viewportWidth;
        mViewportHeight = viewportHeight;
    }

    public void setDrawSize(float drawWidth, float drawHeight) {
        mDrawWidth = drawWidth;
        mDrawHeight = drawHeight;
    }

    public float offsetX() { return mOffset.x; }

    public float offsetY() { return mOffset.y; }

    /**
     * Set the current offset.
     * The new offset values are limited by
     * the lower bounds getMinX and getMinY and
     * the upper bound MAX_OFFSET (same value for x and y).
     */
    public void setOffset(float x, float y) {
        // Check offset overstep X
        mOffset.x = Math.max(getMinX(), Math.min(x, MAX_OFFSET));

        // Check offset overstep Y
        mOffset.y = Math.max(getMinY(), Math.min(y, MAX_OFFSET));
    }

    /**
     * Adds dx and dy to the current offset.
     * The new offset values are limited by
     * the lower bounds getMinX and getMinY and
     * the upper bound MAX_OFFSET (same value for x and y).
     *
     * @param dx
     * @param dy
     */
    public void offset(float dx, float dy) {
        setOffset(mOffset.x + dx, mOffset.y + dy);
    }

    private boolean fixOffsetIntegrity() {

        boolean updateNeeded = false;

        // Check offset overstep X
        if (mOffset.x < getMinX() || mOffset.x > MAX_OFFSET) {
            mOffset.x = Math.max(getMinX(), Math.min(mOffset.x, MAX_OFFSET));
            updateNeeded = true;
        }

        // Check offset overstep Y
        if (mOffset.y < getMinY() || mOffset.y > MAX_OFFSET) {
            mOffset.y = Math.max(getMinY(), Math.min(mOffset.y, MAX_OFFSET));
            updateNeeded = true;
        }

        return updateNeeded;
    }

    /** @return The min possible value for offset.x. */
    private float getMinX() {
        return mViewportWidth - mDrawWidth * mCoefficient;
    }

    /** @return The min possible value for offset.y. */
    private float getMinY() {
        return mViewportHeight - mDrawHeight * mCoefficient;
    }
}