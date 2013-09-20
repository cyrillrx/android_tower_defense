package org.es.minigames.scrollingbackgrounds;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.SurfaceHolder;

import org.es.minigames.R;

/**
 * Created by Cyril on 18/09/13.
 */
public class DrawingThread extends Thread {

    /** Number of frame we wish to draw per second. */
    private int mFrameRate = 20;
    /** The time a frame is suppose to stay on screen in milliseconds. */
    private int mFrameDurationMillis = 1000 / mFrameRate;
    /** Indicate whether the thread is suppose to draw or not. */
    private boolean mRunning = true;

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
    private Resources mResources    = null;

    private Bitmap mFarBackground   = null;
    private Bitmap mNearBackground  = null;

    // right to left scroll tracker for near and far BG
    private int mFarBg1Left  = 0;
    private int mFarBg2Left  = 0;
    private int mNearBg1Left = 0;
    private int mNearBg2Left = 0;

    public DrawingThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {

        mSurfaceHolder = surfaceHolder;
        mResources = context.getResources();

        // two background since we want them moving at different speeds
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inSampleSize = 2;
        mFarBackground  = BitmapFactory.decodeResource(mResources, R.drawable.background_far, bmpOptions);
        mNearBackground = BitmapFactory.decodeResource(mResources, R.drawable.background_near, bmpOptions);

    }

    @Override
    public void run() {

        while (mRunning) {
            long start = System.currentTimeMillis();

            input();
            update();
            draw();

            long waitingTimeMillis = mFrameDurationMillis - (System.currentTimeMillis() - start);
            if (waitingTimeMillis > 0) {
                try {
                    sleep(waitingTimeMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    mRunning = false;
                }
            } else {
                // We are running late !
                Log.d("miniGames", "Running late ! " + waitingTimeMillis);
            }

        }
    }

    public void setFrameRate(int framePerSecond) {
        mFrameRate = framePerSecond;
        mFrameDurationMillis = 1000 / mFrameRate;
    }

    /**
     * Used to signal the thread whether it should be running or not.
     *
     * @param running true to run, false to shut down
     */
    public void setRunning(boolean running) {
        mRunning = running;
    }

    /* Callback invoked when the surface dimensions change. */
    public void setSurfaceSize(int width, int height) {
        // synchronized to make sure these all change atomically
        synchronized (mSurfaceHolder) {
            mCanvasWidth    = width;
            mCanvasHeight   = height;

            // Resize the background image
            // Image is larger than the screen => adapt height
            float farBgCoef  = (float)mCanvasHeight / (float)mFarBackground.getHeight();
            float nearBgCoef = (float)mCanvasHeight / (float)mNearBackground.getHeight();
            float newFarBgWidth  = mFarBackground.getWidth() * farBgCoef;
            float newNearBgWidth = mNearBackground.getWidth() * nearBgCoef;

            mFarBackground  = Bitmap.createScaledBitmap(mFarBackground,  (int) newFarBgWidth,  mCanvasHeight, true);
            mNearBackground = Bitmap.createScaledBitmap(mNearBackground, (int) newNearBgWidth, mCanvasHeight, true);
        }
    }

    /** Check user inputs. */
    private void input() { }

    /** Update data. */
    private void update() {
        // decrement the far and near backgrounds
        mFarBg1Left -= 1;
        mNearBg1Left -= 4;

        int farBg1Right    = mFarBg1Left + mFarBackground.getWidth();
        int nearBg1Right   = mNearBg1Left + mNearBackground.getWidth();

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

    /** Draw the new frame. */
    private void draw() {
        Canvas canvas = null;
        try {
            canvas = mSurfaceHolder.lockCanvas(null);
            if (canvas != null) {
                doDraw(canvas);
            }
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

        canvas.drawBitmap(mFarBackground, mFarBg1Left, 0, null);
        if (mFarBg2Left <= mCanvasWidth) { // TODO isOnScreen(bg)
            // Draw second background only if necessary
            canvas.drawBitmap(mFarBackground, mFarBg2Left, 0, null);
        }

        canvas.drawBitmap(mNearBackground, mNearBg1Left, 0, null);
        if (mNearBg2Left <= mCanvasWidth) { // TODO isOnScreen(bg)
            // Draw second only if necessary
            canvas.drawBitmap(mNearBackground, mNearBg2Left, 0, null);
        }
    }
}