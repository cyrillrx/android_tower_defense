package org.es.towerdefense.component;

import android.graphics.Canvas;
import android.graphics.RectF;

import org.es.engine.graphics.drawable.DrawableElement;
import org.es.engine.graphics.utils.DrawingParam;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public abstract class Control implements DrawableElement {

    protected final RectF mBounds;

    public Control(RectF bounds) {
        mBounds = bounds;
    }

    public RectF getBounds() { return mBounds; }

    public void draw(Canvas canvas) {
        draw(canvas, null);
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) { }

    @Override
    public float getPosX() { return mBounds.left; }

    @Override
    public float getPosY() { return mBounds.top; }

    @Override
    public void setPosition(float x, float y) {
        mBounds.set(x, y, x + mBounds.width(), y + mBounds.height());
    }

    @Override
    public void offsetPosition(float dx, float dy) {
        mBounds.offset(dy, dy);
    }

    @Override
    public float getWidth() { return mBounds.width(); }

    @Override
    public float getHeight() { return mBounds.height(); }
}
