package org.es.minigames.platform.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.Log;

import org.es.engine.graphics.animation.Animation;
import org.es.engine.graphics.animation.AnimationCallback;
import org.es.engine.graphics.animation.BitmapAnimation;
import org.es.engine.graphics.drawable.Background;
import org.es.engine.graphics.sprite.GenericSprite;
import org.es.engine.graphics.sprite.Sprite;
import org.es.minigames.BuildConfig;
import org.es.minigames.R;

import java.util.EnumMap;


/**
 * @author Cyril Leroux
 *         Created on 02/10/13.
 */
public class Hero implements Sprite<Hero.AnimId>, AnimationCallback {

    private static final String TAG = "Hero";
    private static final int STATE_STATIC = 0;
    private int mState = STATE_STATIC;
    private static final int STATE_WALKING = 1;
    private static final int STATE_JUMPING = 2;

    private static final float START_ACCELERATION_DELAY = 2000;
    /** Action duration to reach the maximum speed (in milliseconds). */
    private static final float REACH_MAX_SPEED_DELAY = 6000;
    /** The walking speed of the character in pixels per second. */
    private static final float WALKING_SPEED = 200;
    /** Max speed of the character in pixels per second. */
    private static final float MAX_SPEED = 800;
    private final Sprite<AnimId> mSprite;
    private final Background mBackground;
    //    private float mVelocityX = 0;
    //    private float mVelocityY = 0;
    /** Character current speed in pixels per second. */
    private float mCurrentSpeed = 0;
    public Hero(Resources resources, Background background) {
        mSprite = new GenericSprite(getAnimations(resources, this), AnimId.WALK_LEFT);
        mBackground = background;

        mState = STATE_WALKING;
    }

    private static EnumMap<AnimId, Animation> getAnimations(Resources resources, AnimationCallback callback) {

        EnumMap<AnimId, Animation> animations = new EnumMap<>(AnimId.class);

        animations.put(AnimId.WALK_LEFT, new BitmapAnimation(resources,
                new int[]{
                        R.drawable.hero_left_1,
                        R.drawable.hero_left_2,
                        R.drawable.hero_left_3,
                        R.drawable.hero_left_4,
                        R.drawable.hero_left_5,
                        R.drawable.hero_left_6,
                }, 150, true, callback));

        animations.put(AnimId.WALK_RIGHT, new BitmapAnimation(resources,
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
            if (AnimId.WALK_LEFT.equals(getAnimationId())) {
                mBackground.setScrollSpeed(mCurrentSpeed, 1000);

            } else if (AnimId.WALK_RIGHT.equals(getAnimationId())) {
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
        mSprite.setPosition(surfaceWidth/2f - getAnimation().getWidth()/2f,
                surfaceHeight/2f - getAnimation().getHeight()/2f);
    }

    @Override
    public void draw(Canvas canvas) { mSprite.draw(canvas); }

    @Override
    public void startAnimation() { mSprite.startAnimation(); }

    @Override
    public void stopAnimation() { mSprite.stopAnimation(); }

    @Override
    public AnimId getAnimationId() { return mSprite.getAnimationId(); }

    @Override
    public void setAnimationId(AnimId animationId) { mSprite.setAnimationId(animationId); }

    @Override
    public Animation getAnimation() { return mSprite.getAnimation(); }

    @Override
    public void updateAnimation() { mSprite.updateAnimation(); }

    @Override
    public Animation getAnimation(AnimId animationId) { return mSprite.getAnimation(animationId); }

    @Override
    public void setPosition(float x, float y) { mSprite.setPosition(x, y); }

    @Override
    public void moveX(int value) { mSprite.moveX(value); }

    @Override
    public void moveY(int value) { mSprite.moveY(value); }

    @Override
    public void onAnimationStopped() {
        mCurrentSpeed = 0;
        mState = STATE_STATIC;
    }

    /** Change the animation if necessary. */
    protected void switchState(AnimId state) {
        if (state.equals(mSprite.getAnimationId())) {
            return;
        }
        setAnimationId(state);
    }

    public void walkLeft() {
        mState = STATE_WALKING;
        switchState(AnimId.WALK_LEFT);
        if (!getAnimation().isRunning()) {
            mCurrentSpeed = WALKING_SPEED;
            startAnimation();
        }
    }

    public void walkRight() {
        mState = STATE_WALKING;
        switchState(AnimId.WALK_RIGHT);
        if (!getAnimation().isRunning()) {
            mCurrentSpeed = WALKING_SPEED;
            startAnimation();
        }
    }

    public void jump() {
        mState = STATE_JUMPING;
        // switchState();
        //        xt = vx * t;
        //        yt = vy * t - (g * t²)/2;
    }

    // Hero states
    public static enum AnimId {
        WALK_LEFT,
        WALK_RIGHT
    }
}
