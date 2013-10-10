package org.es.minigames.platform.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import org.es.minigames.BuildConfig;
import org.es.minigames.R;
import org.es.minigames.common.AnimationCallback;
import org.es.minigames.common.drawable.AnimatedElement;
import org.es.minigames.common.drawable.Animation;
import org.es.minigames.common.drawable.BackgroundElement;

/** Created by Cyril on 02/10/13. */
public class Hero extends AnimatedElement {

    private static final String TAG = "Hero";

    // Hero states
    private static final int STATE_STATIC   = 0;
    private static final int STATE_WALKING  = 1;
    private static final int STATE_JUMPING  = 2;

    private static final float START_ACCELERATION_DELAY = 2000;
    /** Action duration to reach the maximum speed (in milliseconds). */
    private static final float REACH_MAX_SPEED_DELAY = 6000;
    /** The walking speed of the character in pixels per second. */
    private static final float WALKING_SPEED = 200;
    /** Max speed of the character in pixels per second. */
    private static final float MAX_SPEED = 800;

    private int mState = STATE_STATIC;
    /** Character current speed in pixels per second. */
    private float mCurrentSpeed = 0;
//    private float mVelocityX = 0;
//    private float mVelocityY = 0;

    private BackgroundElement mBackground;
    private Animation mWalkLeft = null;
    private Animation mWalkRight = null;

    public Hero(Resources resources, BackgroundElement background) {
        super(null);

        mBackground = background;
        mWalkLeft = new Animation(resources,
                new int[] {
                        R.drawable.hero_left_1,
                        R.drawable.hero_left_2,
                        R.drawable.hero_left_3,
                        R.drawable.hero_left_4,
                        R.drawable.hero_left_5,
                        R.drawable.hero_left_6,
                }, 800, true, this);

        mWalkRight = new Animation(resources,
                new int[] {
                        R.drawable.hero_right_1,
                        R.drawable.hero_right_2,
                        R.drawable.hero_right_3,
                        R.drawable.hero_right_4,
                        R.drawable.hero_right_5,
                        R.drawable.hero_right_6,
                }, 800, true, this);

        mAnimation = mWalkRight;
    }

    public boolean update() {

        boolean updated = false;

        updateSpeed();
        updated |= mAnimation.updateBitmap();
        updated |= updatePosition();

        return updated;
    }

    private void updateSpeed() {
        if (mState == STATE_WALKING) {
            final float actionElapsedTime = Math.min(System.currentTimeMillis() - mAnimation.getStartTime(), REACH_MAX_SPEED_DELAY);
            if (actionElapsedTime > START_ACCELERATION_DELAY) {
                // speed that will be added to the standard walking speed
                final float extraSpeed = actionElapsedTime * (MAX_SPEED - WALKING_SPEED) / REACH_MAX_SPEED_DELAY;
                mCurrentSpeed = WALKING_SPEED + extraSpeed;
            }
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Current speed : " + mCurrentSpeed);
            }
        }
    }

    private boolean updatePosition() {
        if (mAnimation.isRunning()) {
            if (mWalkLeft.equals(mAnimation)) {
                mBackground.setScrollSpeed(mCurrentSpeed, 1000);

            } else if (mWalkRight.equals(mAnimation)) {
                mBackground.setScrollSpeed(-1*mCurrentSpeed, 1000);
            }
        } else {
            mBackground.setScrollSpeed(0, 1000);
            return false;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mAnimation != null) {
            mAnimation.draw(canvas, mPosition);
        }
    }

    @Override
    public void stopAnimation() {
        super.stopAnimation();
    }

    @Override
    public void onAnimationStopped() {
        mCurrentSpeed = 0;
        mState = STATE_STATIC;
    }

    /** Change the animation if necessary. */
    protected void switchAnimation(Animation animation) {
        if (animation == null || animation.equals(mAnimation)) {
            return;
        }
        mAnimation = animation;
    }

    public void walkLeft() {
        mState = STATE_WALKING;
        switchAnimation(mWalkLeft);
        if (!mAnimation.isRunning()) {
            mCurrentSpeed = WALKING_SPEED;
            startAnimation();
        }
    }

    public void walkRight() {
        mState = STATE_WALKING;
        switchAnimation(mWalkRight);
        if (!mAnimation.isRunning()) {
            mCurrentSpeed = WALKING_SPEED;
            startAnimation();
        }
    }

    public void jump() {
        mState = STATE_JUMPING;
        // switchAnimation();
//        xt = vx * t;
//        yt = vy * t - (g * t²)/2;
    }
}
