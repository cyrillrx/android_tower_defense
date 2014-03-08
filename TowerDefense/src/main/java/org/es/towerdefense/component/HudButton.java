package org.es.towerdefense.component;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import org.es.engine.graphics.utils.DrawingParam;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public abstract class HudButton extends Control {

    protected final Bitmap mNormalBitmap;
    protected final Bitmap mPressedBitmap;

    protected boolean mPressed;

    public HudButton(Resources resources, int resIdNormal, int resIdPressed, RectF bounds) {
        super(bounds);

        mNormalBitmap = BitmapFactory.decodeResource(resources, resIdNormal);
        mPressedBitmap = BitmapFactory.decodeResource(resources, resIdPressed);

        mPressed = false;
    }

    @Override
    public boolean consumeEvent(MotionEvent event) {
        if (!getBounds().contains(event.getX(), event.getY())) {
            return false;
        }

        final int action = event.getActionMasked();
        switch (action) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                press();
                break;

            default:
                release();
        }

        return true;
    }

    /**
     * Draw the HUD element.
     *
     * @param canvas The canvas on which to draw.
     * @param param Not used for the main HUD. Can not be null for other HUD elements.
     */
    @Override
    public void draw(Canvas canvas, DrawingParam param) {
        canvas.drawBitmap((isPressed() ? mPressedBitmap : mNormalBitmap), null, mBounds, null);
    }

    public void setBounds(RectF bounds) {
        mBounds.set(bounds);
    }

    protected void press() {
        mPressed = true;
        onClick();
    }

    protected void release() { mPressed = false; }

    public boolean isPressed() { return mPressed; }

    protected abstract void onClick();
}
