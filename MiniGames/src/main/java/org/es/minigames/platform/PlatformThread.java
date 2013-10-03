package org.es.minigames.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import org.es.minigames.R;
import org.es.minigames.common.drawable.BackgroundElement;
import org.es.minigames.common.DrawingThread;
import org.es.minigames.common.GameEvent;
import org.es.minigames.platform.drawable.Hero;

/**
 * Created by Cyril on 22/09/13.
 */
public class PlatformThread extends DrawingThread {

    private static final String TAG = "PlatformThread";

    /** The near background scrolls {@link #FAR_NEAR_BG_COEF} times faster than the far one. */
    private final static int FAR_NEAR_BG_COEF = 4;
    private final static int SCROLLING_DURATION = 20;

    // Background elements
    private BackgroundElement mFarBackground;
    //    private BackgroundElement mNearBackground;

    // Hero
    private Hero mHero;

    public PlatformThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        // two background since we want them moving at different speeds
        mFarBackground = new BackgroundElement(mResources, R.drawable.background_far);
        //        mNearBackground = new BackgroundElement(mResources, R.drawable.background_near);
        mHero = new Hero(mResources, mFarBackground);
    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        mFarBackground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        //        mNearBackground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        mHero.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    protected void update() {

        while(!mEventQueue.isEmpty()) {
            processEvent(mEventQueue.poll());
        }
        mHero.update();

    }

    @Override
    protected void processEvent(GameEvent event) {

        final int keyCode = event.getKeyCode();
        final int action = event.getAction();
        Log.d(TAG, "GameEvent : " + action);

        //        // TODO onKeyDown => start scrolling
        //        // TODO onKeyUP => stop scrolling
        if (keyCode == GameEvent.KEYCODE_LEFT && action == GameEvent.ACTION_DOWN) {
            //            scrollBackgrounds(1);
            mHero.walkLeft();

        } else if (keyCode == GameEvent.KEYCODE_RIGHT && action == GameEvent.ACTION_DOWN) {
            //            scrollBackgrounds(-1);
            mHero.walkRight();

        } else if (action == GameEvent.ACTION_UP) {
            //            scrollBackgrounds(0);
            mHero.stopAnimation();
        }
    }

    //    private void scrollBackgrounds(int offSetX) {
    //
    //        // decrement the far and near backgrounds
    //        mFarBackground.setScrollSpeed(offSetX, SCROLLING_DURATION);
    //        mNearBackground.setScrollSpeed(offSetX * FAR_NEAR_BG_COEF, SCROLLING_DURATION);
    //    }

    @Override
    protected void doDraw(Canvas canvas) {

        mFarBackground.draw(canvas);
        //        mNearBackground.draw(canvas);
        mHero.draw(canvas);
    }
}
