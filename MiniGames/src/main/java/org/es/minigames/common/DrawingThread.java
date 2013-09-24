package org.es.minigames.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Parent class for drawing threads
 * <p/>
 * Created by Cyril on 22/09/13.
 */
public abstract class DrawingThread extends Thread {

    private static final String TAG = "org.es.minigames.common.DrawingThread";

    /** Current rect of the surface/canvas. */
    protected Rect mCanvasRect = new Rect(0, 0, 1, 1);
    protected SurfaceHolder mSurfaceHolder = null;
    protected Resources mResources = null;
    protected ConcurrentLinkedQueue<MotionEvent> mEventQueue = new ConcurrentLinkedQueue<MotionEvent>();

    /** Number of frame we wish to draw per second. */
    private int mFrameRate = 20;
    /** The time a frame is suppose to stay on screen in milliseconds. */
    private int mFrameDurationMillis = 1000 / mFrameRate;
    /** Indicate whether the thread is suppose to draw or not. */
    private boolean mRunning = true;

    public DrawingThread(SurfaceHolder surfaceHolder, Context context) {
        mSurfaceHolder = surfaceHolder;
        mResources = context.getResources();
    }

    @Override
    public void run() {

        while (mRunning) {
            long start = System.currentTimeMillis();

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
                Log.d(TAG, "Running late ! " + waitingTimeMillis);
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
            mCanvasRect.right   = width;
            mCanvasRect.bottom  = height;

            updateSurfaceSize();
        }
    }

    /**
     * Update the surface size atomically.<br />
     * Synchronized is performed by the caller ({@link #setSurfaceSize(int, int)}).
     */
    protected abstract void updateSurfaceSize();

    /** Check user inputs and update data. */
    protected abstract void update();

    /** Processes the event to update the view. */
    protected abstract void processEvent(MotionEvent event);

    /** Add a motionEvent that will be processed in {@link #update()}. */
    public boolean addMotionEvent(MotionEvent event) {
        return mEventQueue.add(event);
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
     * Draws current state of the game Canvas.<br />
     * Canvas null check is performed by the caller ({@link #draw()}).
     */
    protected abstract void doDraw(Canvas canvas);
}