package org.es.engine.hud;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import org.es.engine.graphics.drawable.DrawableElement;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public abstract class Control implements DrawableElement {

    /** The bounds of the control on screen. */
    private final RectF mBounds;

    private float mXCoef;
    private float mYCoef;

    public Control(float xCoef, float yCoef) {
        mBounds = new RectF();
        mXCoef = xCoef;
        mYCoef = yCoef;
    }

    /**
     * Intercepts events.
     * Returns true if the event is consumed. and false otherwise.
     * By default a control does not consume the event.
     *
     * @param event the event to intercepts.
     * @return true if the event is consumed. False otherwise.
     */
    public boolean consumeEvent(MotionEvent event) {
        return false;
    }

    public RectF getBounds() { return mBounds; }

    protected void setBounds(RectF bounds) { mBounds.set(bounds); }

    protected void setBounds(float left, float top, float right, float bottom) {
        mBounds.set(left, top, right, bottom);
    }

    public void draw(Canvas canvas) {
        draw(canvas, null);
    }

    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) { }

    @Override
    public float getPosX() { return mBounds.left; }

    @Override
    public float getPosY() { return mBounds.top; }

    /**
     * Define the position coefficient for abscissa and ordinates.
     *
     * @param xCoef coefficient for abscissa.
     * @param yCoef coefficient for ordinates.
     */
    @Override
    public void setPosition(float xCoef, float yCoef) {
        mXCoef = xCoef;
        mYCoef = yCoef;
    }

    @Override
    public void offsetPosition(float dx, float dy) {
        mXCoef += dx;
        mYCoef += dy;
    }

    @Override
    public float getWidth() { return mBounds.width(); }

    @Override
    public float getHeight() { return mBounds.height(); }

    protected float getXCoef() { return mXCoef; }

    protected float getYCoef() { return mYCoef; }
}
