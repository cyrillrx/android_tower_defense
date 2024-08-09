package org.es.engine.hud;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import org.es.engine.graphics.utils.DrawingParam;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public abstract class Button extends Control {

    protected boolean mPressed;

    private float mWidthCoef;
    private float mHeightCoef;

    private final Bitmap mNormalBitmap;
    protected final Bitmap mPressedBitmap;

    public Button(float xCoef, float yCoef, float coefWidth, float coefHeight,
                  Resources resources, int resIdNormal, int resIdPressed) {
        super(xCoef, yCoef);

        mWidthCoef = coefWidth;
        mHeightCoef = coefHeight;

        mNormalBitmap = BitmapFactory.decodeResource(resources, resIdNormal);
        mPressedBitmap = BitmapFactory.decodeResource(resources, resIdPressed);

        mPressed = false;
    }

    @Override
    public boolean consumeEvent(MotionEvent event) {

        final int action = event.getActionMasked();
        switch (action) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_HOVER_EXIT:
                if (getBounds().contains(event.getX(), event.getY())) {
                    press();
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_HOVER_ENTER:
            case MotionEvent.ACTION_HOVER_MOVE:

                if (getBounds().contains(event.getX(), event.getY())) {
                    release(true);
                    return true;
                } else {
                    release(false);
                }
                break;
        }

        return false;
    }

    /**
     * Draw the HUD element.
     *
     * @param canvas The canvas on which to draw.
     * @param param  Not used for the main HUD. Can not be null for other HUD elements.
     */
    @Override
    public void draw(Canvas canvas, DrawingParam param) {
        float left = canvas.getWidth() * getXCoef();
        float top = canvas.getHeight() * getYCoef();

        // TODO update
        float right = left + canvas.getHeight() * getHeightCoef();
        float bottom = top + canvas.getHeight() * getHeightCoef();

        setBounds(left, top, right, bottom);
        canvas.drawBitmap((isPressed() ? mPressedBitmap : mNormalBitmap), null, getBounds(), null);
    }

    protected void press() {
        mPressed = true;
    }

    protected void release(boolean inBounds) {
        if (inBounds && mPressed) {
            onClick();
        }
        mPressed = false;
    }

    public boolean isPressed() { return mPressed; }

    protected abstract void onClick();

    protected float getWidthCoef() { return mWidthCoef; }

    protected float getHeightCoef() { return mHeightCoef; }
}
