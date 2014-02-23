package org.es.engine.graphics.utils;

import android.graphics.PointF;

/**
 * @author Cyril Leroux
 *         Created on 16/02/14.
 */
public class DrawingParam {

    private float mCoefficient;
    private final PointF mOffset;

    public DrawingParam() {
        mCoefficient = 1f;
        mOffset = new PointF();
    }

    public float coef() { return mCoefficient; }

    public void setCoef(float coefficient) { mCoefficient = coefficient; }

    public float offsetX() { return mOffset.x; }

    public float offsetY() { return mOffset.y; }

    public void setOffset(float x, float y) { mOffset.set(x, y); }
}