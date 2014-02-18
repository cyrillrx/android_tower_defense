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
    /** The near background scrolls {@link #FAR_NEAR_BG_COEF} times faster than the far one. */
    private final static int FAR_NEAR_BG_COEF = 4;
    private final static int SCROLLING_DURATION = 20;
    // Background elements
    private Background mFarBackground;
    //    private Background mNearBackground;
    // Hero
    private Hero mHero;

    public PlatformThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        // two background since we want them moving at different speeds
        mFarBackground = new Background(mResources, R.drawable.background_far);
        //        mNearBackground = new Background(mResources, R.drawable.background_near);
        mHero = new Hero(mResources, mFarBackground);
    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        mFarBackground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        //        mNearBackground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        mHero.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    protected boolean update() {

        boolean updated = false;

        while (!mEventQueue.isEmpty()) {
            processEvent(mEventQueue.poll());
        }
        updated |= mHero.update();

        return updated;
    }

    @Override
    protected void processEvent(UserEvent event) {

        final int keyCode = event.getKeyCode();
        final int action = event.getAction();
        Log.d(TAG, "UserEvent : " + action);

        //        // TODO onKeyDown => start scrolling
        //        // TODO onKeyUP => stop scrolling
        if (keyCode == UserEvent.KEYCODE_LEFT && action == UserEvent.ACTION_DOWN) {
            //            scrollBackgrounds(1);
            mHero.walkLeft();

        } else if (keyCode == UserEvent.KEYCODE_RIGHT && action == UserEvent.ACTION_DOWN) {
            //            scrollBackgrounds(-1);
            mHero.walkRight();

        } else if (action == UserEvent.ACTION_UP) {
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
