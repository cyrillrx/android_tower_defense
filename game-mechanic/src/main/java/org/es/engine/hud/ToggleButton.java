package org.es.engine.hud;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import org.es.engine.graphics.utils.DrawingParam;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public abstract class ToggleButton extends Button {

    protected final Bitmap mNormalBitmap2;
    protected final Bitmap mPressedBitmap2;

    private boolean mSwitch;

    public ToggleButton(float xCoef, float yCoef, float coefWidth, float coefHeight,
                        Resources resources,
                        int resIdNormal, int resIdPressed,
                        int resIdNormal2, int resIdPressed2) {
        super(xCoef, yCoef, coefWidth, coefHeight, resources, resIdNormal, resIdPressed);

        mNormalBitmap2 = BitmapFactory.decodeResource(resources, resIdNormal2);
        mPressedBitmap2 = BitmapFactory.decodeResource(resources, resIdPressed2);

        mSwitch = true;
    }

    /**
     * Draw the HUD element.
     *
     * @param canvas The canvas on which to draw.
     * @param param  Not used for the main HUD. Can not be null for other HUD elements.
     */
    @Override
    public void draw(Canvas canvas, DrawingParam param) {
        if (mSwitch) {
            super.draw(canvas, param);
        } else {
            canvas.drawBitmap((isPressed() ? mPressedBitmap2 : mNormalBitmap2), null, getBounds(), null);
        }
    }

    @Override
    protected void release(boolean inBounds) {
        if (inBounds && mPressed) {
            if (mSwitch) {
                onClick();
            } else {
                onClick2();
            }
            mSwitch = !mSwitch;
        }
        mPressed = false;
    }

    protected abstract void onClick2();

    /** Used to notify a change from outside. */
    public void setState1() { mSwitch = true; }

    /** Used to notify a change from outside. */
    public void setState2() { mSwitch = false; }
}
