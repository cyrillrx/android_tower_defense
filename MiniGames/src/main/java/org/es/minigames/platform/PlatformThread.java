package org.es.minigames.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import org.es.engine.gamemechanic.DrawingThread;
import org.es.engine.gamemechanic.UserEvent;
import org.es.minigames.R;
import org.es.minigames.platform.drawable.Hero;
import org.es.minigames.scrollingbackgrounds.drawable.Background;

/**
 * @author Cyril Leroux
 *         Created on 22/09/13.
 */
public class PlatformThread extends DrawingThread {

    private static final String TAG = "PlatformThread";
    // Background elements
    private Background mFarBackground;
    // Hero
    private Hero mHero;

    public PlatformThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        mFarBackground = new Background(mResources, R.drawable.background_far);
        mHero = new Hero(mResources, mFarBackground);
    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        mFarBackground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        mHero.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    protected boolean update() {

        while (!mEventQueue.isEmpty()) {
            processEvent(mEventQueue.poll());
        }
        return mHero.update();
    }

    @Override
    protected void processEvent(UserEvent event) {

        final int keyCode = event.getKeyCode();
        final int action = event.getAction();
        Log.d(TAG, "UserEvent : " + action);

        if (keyCode == UserEvent.KEYCODE_LEFT && action == UserEvent.ACTION_DOWN) {
            mHero.walkLeft();

        } else if (keyCode == UserEvent.KEYCODE_RIGHT && action == UserEvent.ACTION_DOWN) {
            mHero.walkRight();

        } else if (action == UserEvent.ACTION_UP) {
            mHero.stopAnimation();
        }
    }

    @Override
    protected void doDraw(Canvas canvas) {

        mFarBackground.draw(canvas, null);
        mHero.draw(canvas, null);
    }
}
