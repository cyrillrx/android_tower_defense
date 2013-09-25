package org.es.minigames.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import org.es.minigames.R;
import org.es.minigames.common.drawelement.AnimatedElement;
import org.es.minigames.common.drawelement.BackgroundElement;
import org.es.minigames.common.DrawingThread;
import org.es.minigames.common.GameEvent;

/**
 * Created by Cyril on 22/09/13.
 */
public class PlatformThread extends DrawingThread {

    private static final String TAG = "PlatformThread";

    /** The near background scrolls {@link #FAR_NEAR_BG_COEF} times faster than the far one. */
    private final static int FAR_NEAR_BG_COEF = 4;

    // Background elements
    private BackgroundElement mFarBackground;
    private BackgroundElement mNearBackground;

    // Hero
    private AnimatedElement mHero;

    public PlatformThread(SurfaceHolder surfaceHolder, Context context) {
        super(surfaceHolder, context);

        // two background since we want them moving at different speeds
        mFarBackground = new BackgroundElement(mResources, R.drawable.background_far);
        mNearBackground = new BackgroundElement(mResources, R.drawable.background_near);
        mHero = new AnimatedElement(mResources,
                new int[] {
                        R.drawable.hero_right_1,
                        R.drawable.hero_right_2,
                        R.drawable.hero_right_3,
                        R.drawable.hero_right_4,
                        R.drawable.hero_right_5,
                        R.drawable.hero_right_6,
                }, 800);
    }

    @Override
    protected void updateSurfaceSize(int surfaceWidth, int surfaceHeight) {

        mFarBackground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        mNearBackground.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
        mHero.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    protected void update() {

        while(!mEventQueue.isEmpty()) {
            processEvent(mEventQueue.poll());
        }
    }

    @Override
    protected void processEvent(GameEvent event) {

        final int keyCode = event.getKeyCode();
        final int action = event.getAction();
        Log.d(TAG, "GameEvent : " + action);

//        // TODO onKeyDown => start scrolling
//        // TODO onKeyUP => stop scrolling
        if (keyCode == GameEvent.KEYCODE_LEFT && action == GameEvent.ACTION_DOWN) {
            scrollBackgrounds(1);
            mHero.startAnimation();

        } else if (keyCode == GameEvent.KEYCODE_RIGHT && action == GameEvent.ACTION_DOWN) {
            scrollBackgrounds(-1);
            mHero.startAnimation();

        } else if (action == GameEvent.ACTION_UP) {
            mHero.stopAnimation();
        }
    }

    private void scrollBackgrounds(int offSetX) {

        // decrement the far and near backgrounds
        mFarBackground.scrollX(offSetX);
        mNearBackground.scrollX(offSetX * FAR_NEAR_BG_COEF);
    }

    @Override
    protected void doDraw(Canvas canvas) {

        mFarBackground.draw(canvas);
        mNearBackground.draw(canvas);
        mHero.draw(canvas);
    }
}
