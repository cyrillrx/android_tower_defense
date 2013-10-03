package org.es.minigames.platform.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;

import org.es.minigames.R;
import org.es.minigames.common.drawable.AnimatedElement;
import org.es.minigames.common.drawable.Animation;
import org.es.minigames.common.drawable.BackgroundElement;

/** Created by Cyril on 02/10/13. */
public class Hero extends AnimatedElement {

    // Hero states
    private static final int STATE_WALKING = 0;
    private static final int STATE_JUMPING = 1;

    /**
     * Action duration to reach the maximum speed.<br />
     * In milliseconds.
     */
    private static final float MAX_SPEED_TIME = 10000;
    /** The walking speed of the character in pixels per second. */
    private static final float WALKING_SPEED = 80; // 80 px/s
    /** Max speed of the character in pixels per second. */
    private static final float MAX_SPEED = 600; // 600 px/s

    /** Character current speed in pixels per second. */
    private float mCurrentSpeed = 0; // 5 px/s

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
                }, 800, true);

        mWalkRight = new Animation(resources,
                new int[] {
                        R.drawable.hero_right_1,
                        R.drawable.hero_right_2,
                        R.drawable.hero_right_3,
                        R.drawable.hero_right_4,
                        R.drawable.hero_right_5,
                        R.drawable.hero_right_6,
                }, 800, true);

        mAnimation = mWalkRight;
    }

    public void update() {
        updateSpeed();
    }

    private void updateSpeed() {
        if (mAnimation.equals(mWalkRight) || mAnimation.equals(mWalkLeft)) {
            final float actionElapsedTime = System.currentTimeMillis() - mAnimation.getStartTime();
            mCurrentSpeed = Math.min(actionElapsedTime * MAX_SPEED / MAX_SPEED_TIME, MAX_SPEED);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (mAnimation != null) {
            mAnimation.draw(canvas, mPosition);

            if (mAnimation.isRunning()) {
                if (mAnimation.equals(mWalkLeft)) {
                    mBackground.setScrollSpeed(mCurrentSpeed, 1000);

                } else if (mWalkRight.equals(mAnimation)) {
                    mBackground.setScrollSpeed(-1*mCurrentSpeed, 1000);
                }
            } else {
                mBackground.setScrollSpeed(0, 1000);
            }
        }
    }

    protected void switchAnimation(Animation animation) {
        if (animation == null || animation.equals(mAnimation)) {
            return;
        }
        mAnimation = animation;
    }

    public void walkLeft() {
        switchAnimation(mWalkLeft);
        if (!mAnimation.isRunning()) {
            mCurrentSpeed = WALKING_SPEED;
            startAnimation();
        }
    }

    public void walkRight() {
        switchAnimation(mWalkRight);
        if (!mAnimation.isRunning()) {
            mCurrentSpeed = WALKING_SPEED;
            startAnimation();
        }
    }
}
