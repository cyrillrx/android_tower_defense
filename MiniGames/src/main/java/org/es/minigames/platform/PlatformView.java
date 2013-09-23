package org.es.minigames.platform;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.es.minigames.common.DrawingThread;
import org.es.minigames.common.DrawingView;

/**
 * Created by Cyril on 22/09/13.
 */
public class PlatformView extends DrawingView {

    public PlatformView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected DrawingThread createDrawingThread(SurfaceHolder holder, Context context) {
        return new PlatformThread(holder, context);
    }
}
