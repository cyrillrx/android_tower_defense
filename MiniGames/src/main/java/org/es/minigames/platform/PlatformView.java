package org.es.minigames.platform;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.es.minigames.common.DrawingThread;
import org.es.minigames.common.DrawingView;
import org.es.minigames.common.GameEvent;

/**
 * Created by Cyril on 22/09/13.
 */
public class PlatformView extends DrawingView {

    private static final String TAG = "PlatformView";

    public PlatformView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected DrawingThread createDrawingThread(SurfaceHolder holder, Context context) {
        return new PlatformThread(holder, context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final int action = event.getActionMasked();

        Log.d(TAG, "dispatchTouchEvent action : " + action    );
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                thread.addGameEvent(new GameEvent(GameEvent.KEYCODE_RIGHT, GameEvent.ACTION_DOWN));
                return true;

            case MotionEvent.ACTION_UP:
                thread.addGameEvent(new GameEvent(GameEvent.KEYCODE_RIGHT, GameEvent.ACTION_UP));
                return true;

            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_MOVE:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        final int action = event.getAction();
        final int keyCode = event.getKeyCode();

        Log.d(TAG, "dispatchTouchEvent action : " + action    );

        if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            thread.addGameEvent(new GameEvent(GameEvent.KEYCODE_LEFT, GameEvent.ACTION_DOWN));

        } else if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            thread.addGameEvent(new GameEvent(GameEvent.KEYCODE_RIGHT, GameEvent.ACTION_DOWN));

        } else if (action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            thread.addGameEvent(new GameEvent(GameEvent.KEYCODE_LEFT, GameEvent.ACTION_UP));

        } else if (action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            thread.addGameEvent(new GameEvent(GameEvent.KEYCODE_RIGHT, GameEvent.ACTION_UP));
        }

        return super.dispatchKeyEvent(event);
    }
}
