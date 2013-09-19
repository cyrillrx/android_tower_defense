package org.es.minigames.scrollingbackgrounds;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.format.Time;
import android.view.SurfaceHolder;

import org.es.minigames.R;

/**
 * Created by Cyril on 18/09/13.
 */
public class DrawingThread extends Thread {

    private static final int FRAME_PER_SECOND   = 20;
    private static final int FRAME_DURATION     = 1000 / FRAME_PER_SECOND;

    private boolean mRun = true;

    /**
     * Current height of the surface/canvas.
     *
     * @see #setSurfaceSize
     */
    private int mCanvasHeight = 1;
    /**
     * Current width of the surface/canvas.
     *
     * @see #setSurfaceSize
     */
    private int mCanvasWidth = 1;

    private SurfaceHolder mSurfaceHolder = null;
    private Resources mResources = null;
    private Bitmap mBackgroundFar = null;
    private Bitmap mBackgroundNear = null;
    // right to left scroll tracker for near and far BG
    private int mBgFarMoveX = 0;
    private int mBgNearMoveX = 0;

    public DrawingThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {

        mSurfaceHolder = surfaceHolder;
        mResources = context.getResources();

        // two background since we want them moving at different speeds
        mBackgroundFar = BitmapFactory.decodeResource(mResources, R.drawable.background_far);
        mBackgroundNear = BitmapFactory.decodeResource(mResources, R.drawable.background_near);

    }

    @Override
    public void run() {
        while (mRun) {

            long start = System.currentTimeMillis();

            input();
            update();
            draw();

            long waitingTimeMillis = System.currentTimeMillis() - start - FRAME_DURATION;
            if (waitingTimeMillis > 0) {
                try {
                    sleep(waitingTimeMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    mRun = false;
                }
            } /* else { We are running late ! }*/

        }
    }

    /* Callback invoked when the surface dimensions change. */
    public void setSurfaceSize(int width, int height) {
        // synchronized to make sure these all change atomically
        synchronized (mSurfaceHolder) {
            mCanvasWidth    = width;
            mCanvasHeight   = height;

            // Resize the background image
            mBackgroundFar  = Bitmap.createScaledBitmap(mBackgroundFar, width * 2, height, true);
            mBackgroundNear = Bitmap.createScaledBitmap(mBackgroundNear, width * 2, height, true);
        }
    }

    /** Check user inputs. */
    private void input() { }

    /** Update data. */
    private void update() {
        // decrement the far background
        mBgFarMoveX -= 1;

        // decrement the near background
        mBgNearMoveX -= 4;
    }

    /** Draw the new frame. */
    private void draw() {
        Canvas canvas = null;
        try {
            canvas = mSurfaceHolder.lockCanvas(null);
            doDraw(canvas);
        } finally {
            if (canvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    /**
     * Draws current state of the game Canvas.
     */
	protected void doDraw(Canvas canvas) {

        // calculate the wrap factor for matching image draw
        int newFarX = mBackgroundFar.getWidth() - (-mBgFarMoveX);

        // if we have scrolled all the way, reset to start
        if (newFarX <= 0) {
            mBgFarMoveX = 0;
            // only need one draw
            canvas.drawBitmap(mBackgroundFar, mBgFarMoveX, 0, null);

        } else {
            // need to draw original and wrap
            canvas.drawBitmap(mBackgroundFar, mBgFarMoveX, 0, null);
            canvas.drawBitmap(mBackgroundFar, newFarX, 0, null);
        }

        // same story different image...
        // TODO possible method call
        int newNearX = mBackgroundNear.getWidth() - (-mBgNearMoveX);

        if (newNearX <= 0) {
            mBgNearMoveX = 0;
            canvas.drawBitmap(mBackgroundNear, mBgNearMoveX, 0, null);

        } else {
            canvas.drawBitmap(mBackgroundNear, mBgNearMoveX, 0, null);
            canvas.drawBitmap(mBackgroundNear, newNearX, 0, null);
        }
    }
}