package org.es.minigames.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Parent class for drawing views
 * <p/>
 * Created by Cyril on 22/09/13.
 */
public abstract class DrawingView extends SurfaceView implements SurfaceHolder.Callback {

    protected DrawingThread thread = null;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = createDrawingThread(holder, context);

        setFocusable(true);
    }

    protected abstract DrawingThread createDrawingThread(SurfaceHolder holder, Context context);

    public void setFrameRate(int framePerSecond) {
        if (thread != null) {
            thread.setFrameRate(framePerSecond);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        // Stop the drawing thread for it not to touch the Surface/Canvas again.
        thread.setRunning(false);
        try {
            thread.join();
        } catch (InterruptedException e) { /* swallow */ }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            if (thread != null) {
                Log.d("ScrollingBackgroundView", "onWindowFocusChanged");
                //TODO
                //                thread.pause();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = event.getAction();

        switch (action & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                // TODO
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                // TODO
                break;

            case MotionEvent.ACTION_UP:
                // TODO
                break;

            case MotionEvent.ACTION_POINTER_UP:
                // TODO
                break;

            case MotionEvent.ACTION_MOVE:
                // TODO
                break;
        }

        return super.onTouchEvent(event);
    }
}
