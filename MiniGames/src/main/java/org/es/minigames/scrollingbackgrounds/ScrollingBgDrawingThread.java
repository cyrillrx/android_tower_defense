package org.es.minigames.scrollingbackgrounds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.es.minigames.R;
import org.es.minigames.common.DrawingThread;
import org.es.minigames.common.GameEvent;

/**
 * Created by Cyril on 18/09/13.
 */
public class ScrollingBgDrawingThread extends DrawingThread {

    private Bitmap mFarBackground = null;
    private Bitmap mNearBackground = null;
    // right to left scroll tracker for near and far BG
    private int mFarBg1Left = 0;
    private int mFarBg2Left = 0;
    private int mNearBg1Left = 0;
    private int mNearBg2Left = 0;

    public ScrollingBgDrawingThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        // two background since we want them moving at different speeds
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inSampleSize = 2;
        mFarBackground = BitmapFactory.decodeResource(mResources, R.drawable.background_far, bmpOptions);
        mNearBackground = BitmapFactory.decodeResource(mResources, R.drawable.background_near, bmpOptions);
    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        
        // Resize the background image
        // Image is larger than the screen => adapt height
        float farBgCoef = (float) surfaceHeight / (float) mFarBackground.getHeight();
        float nearBgCoef = (float) surfaceHeight / (float) mNearBackground.getHeight();
        float newFarBgWidth = mFarBackground.getWidth() * farBgCoef;
        float newNearBgWidth = mNearBackground.getWidth() * nearBgCoef;

        mFarBackground = Bitmap.createScaledBitmap(mFarBackground, (int) newFarBgWidth, surfaceHeight, true);
        mNearBackground = Bitmap.createScaledBitmap(mNearBackground, (int) newNearBgWidth, surfaceHeight, true);
    }

    /** Update data. */
    protected void update() {

        // decrement the far and near backgrounds
        mFarBg1Left -= 1;
        mNearBg1Left -= 4;

        int farBg1Right = mFarBg1Left + mFarBackground.getWidth();
        int nearBg1Right = mNearBg1Left + mNearBackground.getWidth();

        // if we have scrolled all the way, reset to start
        if (farBg1Right <= 0) {
            mFarBg1Left = 0;
        }
        if (nearBg1Right <= 0) {
            mNearBg1Left = 0;
        }

        // calculate the wrap factor for matching image draw
        mFarBg2Left = mFarBg1Left + mFarBackground.getWidth();
        mNearBg2Left = mNearBg1Left + mNearBackground.getWidth();
    }

    @Override
    protected void processEvent(GameEvent event) { }

    /**
     * Draws current state of the game Canvas.
     */
    protected void doDraw(Canvas canvas) {

        canvas.drawBitmap(mFarBackground, mFarBg1Left, 0, null);
        if (mFarBg2Left <= canvas.getWidth()) {
            // Draw second background only if necessary
            canvas.drawBitmap(mFarBackground, mFarBg2Left, 0, null);
        }

        canvas.drawBitmap(mNearBackground, mNearBg1Left, 0, null);
        if (mNearBg2Left <= canvas.getWidth()) {
            // Draw second only if necessary
            canvas.drawBitmap(mNearBackground, mNearBg2Left, 0, null);
        }
    }
}