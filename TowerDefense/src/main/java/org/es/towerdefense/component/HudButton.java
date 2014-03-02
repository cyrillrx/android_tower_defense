package org.es.towerdefense.component;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

import org.es.engine.graphics.utils.DrawingParam;

/**
 * @author Cyril Leroux
 *         Created 02/03/14.
 */
public class HudButton extends Control {

    private final Bitmap mNormalBitmap;
    private final Bitmap mPressedBitmap;
    private boolean mPressed;

    public HudButton(Resources resources, int resIdNormal, int resIdPressed, RectF bounds) {
        super(bounds);

        mNormalBitmap = BitmapFactory.decodeResource(resources, resIdNormal);
        mPressedBitmap = BitmapFactory.decodeResource(resources, resIdPressed);

        mPressed = false;
    }

    /**
     * Draw the HUD element.
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

    private void press() { mPressed = true; }

    private void release() { mPressed = false; }

    public boolean isPressed() { return mPressed; }
}
