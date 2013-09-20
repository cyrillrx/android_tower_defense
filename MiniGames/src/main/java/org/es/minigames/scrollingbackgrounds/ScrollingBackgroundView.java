package org.es.minigames.scrollingbackgrounds;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Cyril on 18/09/13.
 */
public class ScrollingBackgroundView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawingThread thread = null;

    public ScrollingBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new DrawingThread(holder, context, new Handler());

        setFocusable(true);
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

    public void setFrameRate(int framePerSecond) {
        if (thread != null) {
            thread.setFrameRate(framePerSecond);
        }
    }
}
