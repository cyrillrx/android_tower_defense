package org.es.minigames.platform.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import org.es.gameengine.AnimationCallback;
import org.es.gameengine.drawable.Animation;
import org.es.gameengine.drawable.Background;
import org.es.gameengine.drawable.BitmapAnimation;
import org.es.gameengine.drawable.GenericSprite;
import org.es.gameengine.drawable.Sprite;
import org.es.minigames.BuildConfig;
import org.es.minigames.R;

import java.util.HashMap;
import java.util.Map;

/** Created by Cyril on 02/10/13. */
public class Hero implements Sprite, AnimationCallback {

    private static final String TAG = "Hero";

    // Hero states

    private static final int ANIM_WALK_LEFT = 0;
    private static final int ANIM_WALK_RIGHT = 1;

    private static final int STATE_STATIC = 0;
    private static final int STATE_WALKING = 1;
    private static final int STATE_JUMPING = 2;

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

    private final GenericSprite mSprite;
    private final Background mBackground;

    public Hero(Resources resources, Background background) {
        mSprite = new GenericSprite(getAnimations(resources, this));
        mSprite.setAnimationId(ANIM_WALK_LEFT);
        mBackground = background;

        mState = STATE_WALKING;
    }

    private static Map<Integer, Animation> getAnimations(Resources resources, AnimationCallback callback) {

        Map<Integer, Animation> animations = new HashMap<>();

        animations.put(ANIM_WALK_LEFT, new BitmapAnimation(resources,
                new int[]{
                        R.drawable.hero_left_1,
                        R.drawable.hero_left_2,
                        R.drawable.hero_left_3,
                        R.drawable.hero_left_4,
                        R.drawable.hero_left_5,
                        R.drawable.hero_left_6,
                }, 150, true, callback));

        animations.put(ANIM_WALK_RIGHT, new BitmapAnimation(resources,
                new int[]{
                        R.drawable.hero_right_1,
                        R.drawable.hero_right_2,
                        R.drawable.hero_right_3,
                        R.drawable.hero_right_4,
                        R.drawable.hero_right_5,
                        R.drawable.hero_right_6,
                }, 150, true, callback));
        return animations;
    }

    public boolean update() {

        boolean updated = false;

        updateSpeed();
        updated |= getAnimation().updateFrame();
        updated |= updatePosition();

        return updated;
    }

    private void updateSpeed() {
        if (mState == STATE_WALKING) {
            final float actionElapsedTime = Math.min(System.currentTimeMillis() - getAnimation().getStartTime(), REACH_MAX_SPEED_DELAY);
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
        if (getAnimation().isRunning()) {
            if (ANIM_WALK_LEFT == getAnimationId()) {
                mBackground.setScrollSpeed(mCurrentSpeed, 1000);

            } else if (ANIM_WALK_RIGHT == getAnimationId()) {
                mBackground.setScrollSpeed(-1 * mCurrentSpeed, 1000);
            }
        } else {
            mBackground.setScrollSpeed(0, 1000);
            return false;
        }
        return true;
    }


    @Override
    public void onUpdateSurfaceSize(int surfaceWidth, int surfaceHeight) {
        mSprite.onUpdateSurfaceSize(surfaceWidth, surfaceHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        mSprite.draw(canvas);
    }

    @Override
    public void startAnimation() {
        mSprite.startAnimation();
    }

    @Override
    public void stopAnimation() {
        mSprite.stopAnimation();
    }

    @Override
    public int getAnimationId() {
        return mSprite.getAnimationId();
    }

    @Override
    public Animation getAnimation() {
        return mSprite.getAnimation();
    }

    @Override
    public void setAnimationId(final int id) {
        mSprite.setAnimationId(id);
    }

    @Override
    public void onAnimationStopped() {
        mCurrentSpeed = 0;
        mState = STATE_STATIC;
    }

    /** Change the animation if necessary. */
    protected void switchAnimation(int animationId) {
        if (animationId == mSprite.getAnimationId()) {
            return;
        }
        setAnimationId(animationId);
    }

    public void walkLeft() {
        mState = STATE_WALKING;
        switchAnimation(ANIM_WALK_LEFT);
        if (!getAnimation().isRunning()) {
            mCurrentSpeed = WALKING_SPEED;
            startAnimation();
        }
    }

    public void walkRight() {
        mState = STATE_WALKING;
        switchAnimation(ANIM_WALK_RIGHT);
        if (!getAnimation().isRunning()) {
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
