package org.es.minigames.platform;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.es.gameengine.DrawingThread;
import org.es.gameengine.DrawingView;
import org.es.gameengine.UserEvent;

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

        Log.d(TAG, "dispatchTouchEvent action : " + action);
        switch (action) {

            case MotionEvent.ACTION_DOWN:
                final int keyCodeDown = (event.getX() > getCenterX()) ?
                        UserEvent.KEYCODE_RIGHT : UserEvent.KEYCODE_LEFT;
                mThread.addUserEvent(new UserEvent(keyCodeDown, UserEvent.ACTION_DOWN));
                return true;

            case MotionEvent.ACTION_UP:
                final int keyCodeUp = (event.getX() > getCenterX()) ?
                        UserEvent.KEYCODE_RIGHT : UserEvent.KEYCODE_LEFT;
                mThread.addUserEvent(new UserEvent(keyCodeUp, UserEvent.ACTION_UP));
                return true;

            case MotionEvent.ACTION_POINTER_DOWN:
                mThread.addUserEvent(new UserEvent(UserEvent.KEYCODE_UP, UserEvent.ACTION_DOWN));
                return true;

            case MotionEvent.ACTION_POINTER_UP:
                mThread.addUserEvent(new UserEvent(UserEvent.KEYCODE_UP, UserEvent.ACTION_UP));
                return true;


            case MotionEvent.ACTION_MOVE:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        final int action = event.getAction();
        final int keyCode = event.getKeyCode();

        Log.d(TAG, "dispatchTouchEvent action : " + action);

        if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            mThread.addUserEvent(new UserEvent(UserEvent.KEYCODE_LEFT, UserEvent.ACTION_DOWN));

        } else if (action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            mThread.addUserEvent(new UserEvent(UserEvent.KEYCODE_LEFT, UserEvent.ACTION_UP));

        } else if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            mThread.addUserEvent(new UserEvent(UserEvent.KEYCODE_RIGHT, UserEvent.ACTION_DOWN));

        } else if (action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            mThread.addUserEvent(new UserEvent(UserEvent.KEYCODE_RIGHT, UserEvent.ACTION_UP));

        } else if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            mThread.addUserEvent(new UserEvent(UserEvent.KEYCODE_RIGHT, UserEvent.ACTION_DOWN));

        } else if (action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            mThread.addUserEvent(new UserEvent(UserEvent.KEYCODE_RIGHT, UserEvent.ACTION_UP));
        }

        return super.dispatchKeyEvent(event);
    }
}
