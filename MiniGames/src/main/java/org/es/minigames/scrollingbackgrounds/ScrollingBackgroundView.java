package org.es.minigames.scrollingbackgrounds;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
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
                //TODO
//                thread.pause();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.setSurfaceSize(width, height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}
